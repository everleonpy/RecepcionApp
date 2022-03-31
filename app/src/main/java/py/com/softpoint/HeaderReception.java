package py.com.softpoint;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import py.com.softpoint.apiclient.InvWarehouseApi;
import py.com.softpoint.pojos.InvWarehouse;
import py.com.softpoint.pojos.PayVendor;
import py.com.softpoint.pojos.User;
import py.com.softpoint.utils.Cliente;
import retrofit2.Call;
import retrofit2.Response;

public class HeaderReception extends AppCompatActivity implements View.OnClickListener{

    private PayVendor proveedorSelected;
    private Button btnProcesar;
    private Spinner spDepositos;
    private RecyclerView rvListaOCs;
    private TextView tvProveedorName, tvRuc;
    private EditText etFechaDesde, etFechaHasta;
    private Calendar calendar;
    private List<InvWarehouse> lstDepositos;
    private User userLoged;
    private String baseURL;
    private int dia, mes, anho;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header_reception);
        tvProveedorName = findViewById(R.id.tvProveedor);
        tvRuc = findViewById(R.id.tvRuc);

        // Lista de Depositos
        spDepositos = findViewById(R.id.spListaDepositos);
        // Lista de Ord. de Compras
        rvListaOCs = findViewById(R.id.rvListaOCs);
        // Campos Fecha con Calendario atrivo
        etFechaDesde = findViewById(R.id.etFechaDesde);
        etFechaDesde.setOnClickListener(this);
        etFechaHasta = findViewById(R.id.etFechaHasta);
        etFechaHasta.setOnClickListener(this);

        //Recuperamos el Proveedor seleccionado
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        this.proveedorSelected = (PayVendor) bundle.get("PAY_VENDOR");
        this.userLoged = (User) bundle.get("USER_LOGED");
        this.baseURL = (String) bundle.get("URL_BASE");

        //Asignamos el nombre a Visuializar
        tvProveedorName.setText(proveedorSelected.getName());
        tvRuc.setText(proveedorSelected.getTaxNumber());

        btnProcesar = findViewById(R.id.btnProcesar);
        btnProcesar.setOnClickListener(this);

        // Cargar lista de Depositos desde
        lstDepositos = cargarDepositos(proveedorSelected.getUnitId()); // Pedimos la lista de depositos del Sitio del usuario
        Log.i("DEPOSITOS : "," "+lstDepositos.size());
        if( lstDepositos.size() > 0 )
        {
            ArrayAdapter<InvWarehouse> depositoAdapter =  new ArrayAdapter<InvWarehouse>(this,
                    R.layout.support_simple_spinner_dropdown_item,lstDepositos);
            spDepositos.setAdapter(depositoAdapter);
        }
       // Cargamos el Data Pickert
        datePickerSet();
    }

    private List<InvWarehouse> cargarDepositos(Long l) {
        InvWarehouseApi api = Cliente.getClient(this.baseURL).create(InvWarehouseApi.class);
        Call<List<InvWarehouse>> call = api.getAll(userLoged.getSiteId());

        try {
            Thread hiloDepositos = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Response<List<InvWarehouse>> httpGet = call.execute();
                        if (httpGet.isSuccessful() && httpGet.code() == 200) {
                            Log.i("LST_DEPOSITO",httpGet.body().get(1).toString());
                            lstDepositos = httpGet.body();
                        }else {
                            Log.i("LST_DEPOSITO","*** SIN DATOS ***");
                            }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });
            hiloDepositos.start();
            hiloDepositos.join();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return lstDepositos;
    }


    private void datePickerSet() {
        calendar = Calendar.getInstance();
        dia = calendar.get(Calendar.DAY_OF_MONTH);
        mes = calendar.get(Calendar.MONTH);
        anho = calendar.get(Calendar.YEAR);
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.etFechaDesde: {

                DatePickerDialog dp = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        String desde = (dayOfMonth <= 9) ? "0"+dayOfMonth : ""+dayOfMonth ;
                        desde+= ((monthOfYear+1) <= 9) ? "/0"+(monthOfYear+1) : "/"+monthOfYear+1;   //"/"+(monthOfYear+1)+"/"+year;
                        desde+= "/"+year;
                        etFechaDesde.setText(desde);
                    }
                },anho,mes,dia);

                dp.show(getFragmentManager(),"CALENDARIO");
                break;
            }
            case R.id.etFechaHasta: {

                DatePickerDialog dp = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        String hasta = (dayOfMonth <= 9) ? "0"+dayOfMonth :""+dayOfMonth;
                        hasta+=((monthOfYear+1) <= 9) ? "/0"+(monthOfYear+1) : "/"+monthOfYear+1;        //"/"+(monthOfYear+1)+"/"+year;
                        hasta+= "/"+year;
                        etFechaHasta.setText(hasta);
                    }
                },anho,mes,dia);

                dp.show(getFragmentManager(),"CALENDARIO");
                break;
            }
            case R.id.btnProcesar: {
                Intent intent = new Intent(this, GridHeader.class);
                startActivity(intent);
                break;
            }
            default:
                break;
        }

    }
}