package py.com.softpoint;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.util.List;
import py.com.softpoint.adapters.PoPurchOrdersProdsVwAdapter;
import py.com.softpoint.apiclient.PoPurchOrdersProdsVwApi;
import py.com.softpoint.pojos.InvWarehouse;
import py.com.softpoint.pojos.PayVendor;
import py.com.softpoint.pojos.PoPurchOrdersProdsVw;
import py.com.softpoint.pojos.PoPurchaseOrdersVw;
import py.com.softpoint.utils.Cliente;
import retrofit2.Call;
import retrofit2.Response;

public class DetailReception extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private TextView nroOC, proveedorName,depositoName;
    private SearchView txtSearch;
    private RecyclerView rvItemProductos;
    private PayVendor proveedorSelected;
    private PoPurchaseOrdersVw ocSelected;
    private InvWarehouse depositoSelected;
    private List<PoPurchOrdersProdsVw> itemsProductos;
    private PoPurchOrdersProdsVwAdapter itemAdater;
    private String urlBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_reception);

        // Datos de Entorno
        Bundle extras = new Bundle();
        extras = getIntent().getExtras();
        proveedorSelected = (PayVendor) extras.get("PROVEEDOR");
        ocSelected = (PoPurchaseOrdersVw) extras.get("OC_SELECTED");
        depositoSelected = (InvWarehouse) extras.get("DEPOSITO");

        // Component
        nroOC = findViewById(R.id.dataNroOC);
        nroOC.setText(ocSelected.getPoNumber());
        proveedorName = findViewById(R.id.dataProveedorName);
        proveedorName.setText(proveedorSelected.getName());
        depositoName = findViewById(R.id.dataDeposito);
        depositoName.setText(depositoSelected.getName());
        // RecyclerView Item
        rvItemProductos = findViewById(R.id.rvRcpItemsProductos);
        rvItemProductos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

        // Cargamos los items de la OC
        itemsProductos = getItemsOC(Long.parseLong(ocSelected.getPoNumber()));
        if( itemsProductos != null )
        {
            Toast.makeText(getApplicationContext(), "Total Items : "+itemsProductos.size(),Toast.LENGTH_LONG).show();
            itemAdater = new PoPurchOrdersProdsVwAdapter(itemsProductos, new PoPurchOrdersProdsVwAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(PoPurchOrdersProdsVw item) {
                    //TODO Tomamos el item de producto a recepcionar
                    confirmarItem(item);
                }
            });

            txtSearch = findViewById(R.id.svTxtSearch);
            txtSearch.setOnQueryTextListener(this);

            rvItemProductos.setAdapter(itemAdater);

        }

    }

    /**
     * Pulsar BackProcess
     */
    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setMessage(" Cerrar ventana Registro ?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        DetailReception.super.onBackPressed();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }) ;

        AlertDialog msg = alertBuilder.create();
        msg.show();

    }


    /**
     * Metodo encargado de llamar al Dialogo input_reception_item
     * @param item
     */
    private void confirmarItem(PoPurchOrdersProdsVw item) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(DetailReception.this);
        View xview = getLayoutInflater().inflate(R.layout.input_reception_item, null);

        // Componet TextView
        TextView tvItemNroOC,  tvItemProveedorName, tvItemCodigo, tvItemDescripcion, tvItemUM;
        tvItemNroOC = xview.findViewById(R.id.tvDetailNroOC);
        tvItemNroOC.setText(ocSelected.getPoNumber());
        tvItemProveedorName = xview.findViewById(R.id.tvDetailProveedorName);
        tvItemProveedorName.setText(proveedorSelected.getName());
        tvItemCodigo = xview.findViewById(R.id.tvDetailCodigo);
        tvItemCodigo.setText(item.getBarCode());
        tvItemDescripcion = xview.findViewById(R.id.tvDetailDescription);
        tvItemDescripcion.setText(item.getProductName());
        tvItemUM = xview.findViewById(R.id.tvDetailUm);
        tvItemUM.setText(item.getUomName());

        // EditText
        EditText tvItemRcpQty = xview.findViewById(R.id.etDetailQty);
        tvItemRcpQty.setText(item.getReceivedQty()+"");
        tvItemRcpQty.setSelectAllOnFocus(true);

        // Component Botones Item confirm
        Button btnDescartar, btnRecibir;
        btnDescartar = xview.findViewById(R.id.btnDescartar);
        btnRecibir = xview.findViewById(R.id.btnRecibir);

        alert.setView(xview);

        final AlertDialog itemRecibirDialog =  alert.create();
        itemRecibirDialog.setCanceledOnTouchOutside(false);

        //TODO Botones Listener
        btnRecibir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    itemRecibirDialog.dismiss();
                    int xposition  = itemsProductos.indexOf(item);

                    if( tvItemRcpQty.getText().length() > 0 )
                    {
                        item.setReceivedQty(Double.parseDouble(tvItemRcpQty.getText().toString()));
                        //Toast.makeText(getApplicationContext(), "Cantidad : " + item.getQtyReceivedTolPct(), Toast.LENGTH_SHORT).show();
                        itemAdater.getListItemOC().set(xposition, item);
                        itemAdater.notifyItemChanged(xposition);

                    }
            }
        });

        btnDescartar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    itemRecibirDialog.dismiss();
            }
        });
        itemRecibirDialog.show();
    }


    /**
     * Metodo encargado de cargar los items OC
     * @param poNumber
     * @return
     */
    private List<PoPurchOrdersProdsVw> getItemsOC(Long poNumber) {
        PoPurchOrdersProdsVwApi api = Cliente.getClient(urlBase).create(PoPurchOrdersProdsVwApi.class);
        Call<List<PoPurchOrdersProdsVw>> call = api.getItemsOC(poNumber);

        try {
            Thread hilo = new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        Response<List<PoPurchOrdersProdsVw>> httpGet = call.execute();
                        if (httpGet.isSuccessful() && httpGet.code() == 200) {
                            itemsProductos = httpGet.body();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });
            hilo.start();
            hilo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return itemsProductos;
    }

    /*
    * Metodos asosiados al SearchView de items de productos
    */
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String txt) {
        itemAdater.filtarItem(txt);
        return false;
    }
}