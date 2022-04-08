package py.com.softpoint;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;
import java.io.IOException;
import java.util.List;
import py.com.softpoint.adapters.PayVendorAdapter;
import py.com.softpoint.apiclient.PayVendorApi;
import py.com.softpoint.pojos.PayVendor;
import py.com.softpoint.pojos.User;
import py.com.softpoint.utils.Cliente;
import retrofit2.Call;
import retrofit2.Response;

public class ListraProveedores extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private List<PayVendor> listaProveedores;
    private RecyclerView payRecyclerView;
    private SearchView txtABuscar;
    private PayVendorAdapter adapter;
    private User userLoged;
    private String baseUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listra_proveedores);

        // Referencias al Recycler...
        payRecyclerView = findViewById(R.id.recyclerProveedores);
        payRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        // Cargamos el Usuario Logeado
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        this.baseUrl  = (String) bundle.get("BASE_URL");
        this.userLoged = (User) bundle.get("UserLoged");
        Log.i("LOGIN","UserName : "+userLoged.getFullName());

        listaProveedores = cargarProveedores( userLoged.getSiteId() );
        Log.i("LOGIN", "Cant. Registros Proveedores : "+listaProveedores.size());

        if( listaProveedores.size() > 0 )
        {
            adapter = new PayVendorAdapter(listaProveedores, userLoged, baseUrl);
            payRecyclerView.setAdapter(adapter);
            // Solo si hay una lista de proveedores asignamos estos datos
            txtABuscar = findViewById(R.id.buscarProveedor);
            txtABuscar.setOnQueryTextListener(this);

        }
    }

    /**
     * Metodo que controla la salida de la app
     */
    @Override
    public void onBackPressed() {

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        alertBuilder.setMessage("Desea salir de la Aplicacion")
                    .setCancelable(false)
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            moveTaskToBack(true);
                            android.os.Process.killProcess(android.os.Process.myPid());
                            System.exit(1);
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                            }
                        });

            AlertDialog mesg = alertBuilder.create();
            mesg.show();
    }

    /**
     * Carga la lista de Proveedores
     * @param siteId
     * @return
     */
    private List<PayVendor> cargarProveedores(long siteId) {

        PayVendorApi payVendorApi = Cliente.getClient(this.baseUrl).create(PayVendorApi.class);
        Call<List<PayVendor>> call = payVendorApi.getProveedores(siteId);

        try {
            Thread hiloGet = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Response<List<PayVendor>> httpGet = call.execute();
                        if (httpGet.isSuccessful() && httpGet.code() == 200) {
                            listaProveedores = httpGet.body();
                            Log.i("ITEM","Data :"+httpGet.body().get(1).toString() );

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });
            hiloGet.start();
            hiloGet.join();

        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return listaProveedores;
    }


    /*
        Metodos del SearchView Listener
     */
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String txt) {
        adapter.filtraLista(txt);
        return false;
    }

}