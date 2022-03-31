package py.com.softpoint;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.IOException;

import py.com.softpoint.apiclient.LoginApi;
import py.com.softpoint.dbutils.DbHelper;
import py.com.softpoint.pojos.User;
import py.com.softpoint.utils.Cliente;
import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String BASE_URL = "http://192.168.10.21:8080/";
    private static User userLoged = null;
    private EditText txtUsername;
    private EditText txtPassword;
    private Button btnAceptar;
    private Button btnSalir;
    private DbHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Base de Dartos
        dbHelper = new DbHelper(MainActivity.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        //Setings
        txtUsername = findViewById(R.id.etUserName);
        txtPassword = findViewById(R.id.etPassword);
        btnAceptar = findViewById(R.id.btnAceptar);
        btnSalir = findViewById(R.id.btnSalir);

        //Listener de Botones
        btnAceptar.setOnClickListener(this);
        btnSalir.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnAceptar:
                if( validUser(txtUsername.getText().toString())  != null ){
                    if(userLoged.getPassword().trim().equals(txtPassword.getText().toString().trim())) {
                        Intent intent = new Intent(this, ListraProveedores.class);
                        intent.putExtra("UserLoged", userLoged);
                        intent.putExtra("BASE_URL",BASE_URL);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(),"PASSWORD INVALIDO...",Toast.LENGTH_SHORT).show();
                        Log.i("USER","Password Error : "+userLoged.getFullName()+" Ps "+userLoged.getPassword());

                    }
                }
                break;
            case R.id.btnSalir:
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
                break;
            default:
                break;
        }
    }

    /***
    * Validar usuario Conectado
    * @param usrName
    * @return
    */
    private User validUser(String usrName) {
        LoginApi loginApi = Cliente.getClient(BASE_URL).create(LoginApi.class);
        Call<User> call = loginApi.getUser(usrName.trim());

            try {
                Thread hilo = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            Response<User> httpResp = call.execute();
                            if( httpResp.isSuccessful() && httpResp.code() == 200 )
                            {
                                userLoged = httpResp.body();
                                Log.i("RESP", "User : " + userLoged.getFullName());
                            }
                            else
                            {
                                if( httpResp.code() == 204 )
                                {

                                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getApplicationContext(), "Usuario NO REGISTRADO ",
                                            Toast.LENGTH_LONG).show();
                                        }
                                    });

                                }/*else{

                                }*/

                                userLoged = null;
                            }

                        } catch (IOException e) {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "NO HAY CONEXION : "+e.toString(),
                                            Toast.LENGTH_LONG).show();
                                }
                            });

                        }

                    }
                });

                hilo.start();
                hilo.join();

            }catch (Exception e){
                e.printStackTrace();
            }

         return userLoged;
    }
}