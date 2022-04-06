package py.com.softpoint;

import android.content.Intent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import py.com.softpoint.adapters.PoPurchaseOrdersVwAdapter;
import py.com.softpoint.apiclient.InvWarehouseApi;
import py.com.softpoint.apiclient.PoPurchaseOrdersWSApi;
import py.com.softpoint.pojos.InvWarehouse;
import py.com.softpoint.pojos.ListaOCParam;
import py.com.softpoint.pojos.PayVendor;
import py.com.softpoint.pojos.PoPurchaseOrdersVw;
import py.com.softpoint.pojos.User;
import py.com.softpoint.utils.Cliente;
import py.com.softpoint.utils.NumberTools;
import retrofit2.Call;
import retrofit2.Response;

public class HeaderReception extends AppCompatActivity implements View.OnClickListener{

    private PayVendor proveedorSelected;
    private Button btnProcesar;
    private Spinner spDepositos;
    private RecyclerView rvListaOCs;
    private List<PoPurchaseOrdersVw> listaOCs;
    private PoPurchaseOrdersVwAdapter poAdapter;
    private TextView tvProveedorName, tvRuc;
    private Calendar calendar;
    private EditText etFechaDesde, etFechaHasta;
    private List<InvWarehouse> lstDepositos;  //Depositos
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

        btnProcesar = findViewById(R.id.btnConsultar);
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


    /**
     * Cargar la lista de Depositos Habilitados para la resepcion
     * @param l
     * @return
     */
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
                        desde+= ((monthOfYear+1) <= 9) ? "-0"+(monthOfYear+1) : "-"+monthOfYear+1;   //"/"+(monthOfYear+1)+"/"+year;
                        desde+= "-"+year;
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
                        hasta+=((monthOfYear+1) <= 9) ? "-0"+(monthOfYear+1) : "-"+monthOfYear+1;        //"/"+(monthOfYear+1)+"/"+year;
                        hasta+= "-"+year;
                        etFechaHasta.setText(hasta);
                    }
                },anho,mes,dia);

                dp.show(getFragmentManager(),"CALENDARIO");
                break;
            }
            case R.id.btnConsultar: {

                if( etFechaDesde.getText().length() > 0 )
                {
                    if( etFechaHasta.getText().length() > 0 ) {
                        // Intent intent = new Intent(this, GridHeader.class);
                        // startActivity(intent);
                        //TODO Cargar el Grid de OC del Proveedor
                        listaOCs = obtenerOCs(proveedorSelected.getIdentifier(), proveedorSelected.getUnitId(),
                                etFechaDesde.getText().toString(), etFechaHasta.getText().toString());

                        rvListaOCs.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                        if (listaOCs != null)
                        {
                            if (listaOCs.size() > 0) {
                                //TODO Cargar el RecyclerView
                                poAdapter = new PoPurchaseOrdersVwAdapter(listaOCs, new PoPurchaseOrdersVwAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(PoPurchaseOrdersVw item) {
                                            procesarItemsOC(item);
                                    }
                                });
                                rvListaOCs.setAdapter(poAdapter);
                            }else{
                               Log.i("ITEMS","SIN ITEMS");
                            }
                        }else{
                            //TODO Cargar el RecyclerView con valor null
                            /*poAdapter = new PoPurchaseOrdersVwAdapter(listaOCs, new PoPurchaseOrdersVwAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(PoPurchaseOrdersVw item) {
                                    procesarItemsOC(item);
                                }
                            });
                            rvListaOCs.setAdapter(poAdapter); */
                            Toast.makeText(this.getApplicationContext(),"No hay ordenes pendientes de recepcion",Toast.LENGTH_LONG).show();
                        }

                        break;
                    }else { etFechaHasta.setError("Ingrese una fecha valida.."); }
                } else { etFechaDesde.setError("Ingrese una fecha valida.."); }

            }
            default:
                break;
        }

    }


    /**
     * Metodo para invocado al seleccoinar la OC a procesar
     * @param item
     */
    private void procesarItemsOC(PoPurchaseOrdersVw item) {
        //Toast.makeText(getApplicationContext(),"Item Seleccionado :  Nro OC : "+item.getPoNumber(), Toast.LENGTH_LONG).show();

        final AlertDialog.Builder alter = new AlertDialog.Builder(HeaderReception.this);
        View xView = getLayoutInflater().inflate(R.layout.confirm_oc_process, null);

        TextView ocTvNroOC = xView.findViewById(R.id.lblNroOC);
        ocTvNroOC.setText(item.getPoNumber());
        TextView ocMonto = xView.findViewById(R.id.lblMonto);
        ocMonto.setText(NumberTools.nroFormat(item.getAmount()));

        Button btnCancelar = xView.findViewById(R.id.btnCancelOC);
        Button btnRecepcionarOC = xView.findViewById(R.id.btnRecepcionarOC);
        alter.setView(xView);

        final AlertDialog confOC = alter.create();
        confOC.setCanceledOnTouchOutside(false);

            btnRecepcionarOC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Llamamos al Intenen encargado de procesar la recepcion
                    Intent intent = new Intent(v.getContext(), DetailReception.class);
                    intent.putExtra("USER_LOGED", userLoged);
                    intent.putExtra("PROVEEDOR",proveedorSelected);
                    intent.putExtra("OC_SELECTED",item);
                    v.getContext().startActivity(intent);
                }
            });

            btnCancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        confOC.dismiss();
                }
            });


        confOC.show();



    }

    /**
     * Cargar OC pendientes de recepcion
     * @param vendorId
     * @param unitId
     * @param fechaDesde
     * @param fechaHasta
     * @return
     */
    private List<PoPurchaseOrdersVw> obtenerOCs(Long vendorId, Long unitId, String fechaDesde, String fechaHasta) {


        Log.i("OC_PARAM","URL : "+this.baseURL);
        Log.i("OC_PARAM","/"+vendorId+"/"+unitId+"/"+fechaDesde+"/"+fechaHasta);

        PoPurchaseOrdersWSApi api = Cliente.getClient(this.baseURL).create(PoPurchaseOrdersWSApi.class);
        Call<List<PoPurchaseOrdersVw>> call = api.postOCPendites(new ListaOCParam(vendorId,unitId,fechaDesde,fechaHasta));

        try{

            Thread hiloOC = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Response<List<PoPurchaseOrdersVw>> httpGet = call.execute();
                        Log.i("OC_PARAM","Susses : "+httpGet.isSuccessful()+"  Code : "+httpGet.code());
                        if (httpGet.isSuccessful() && httpGet.code() == 200) {
                            listaOCs = httpGet.body();

                        }
                    }catch (IOException io){ io.printStackTrace(); }
                }
            });
            hiloOC.start();
            hiloOC.join();

        }catch (Exception e){
            e.printStackTrace();;
        }
        //Log.i("OC_PARAM","Cat : "+listaOCs.size());
        return listaOCs;
    }
}