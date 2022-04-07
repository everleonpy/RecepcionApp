package py.com.softpoint;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import py.com.softpoint.adapters.PoPurchOrdersProdsVwAdapter;
import py.com.softpoint.adapters.PoPurchaseOrdersVwAdapter;
import py.com.softpoint.apiclient.PoPurchOrdersProdsVwApi;
import py.com.softpoint.pojos.PayVendor;
import py.com.softpoint.pojos.PoPurchOrdersProdsVw;
import py.com.softpoint.pojos.PoPurchaseOrdersVw;
import py.com.softpoint.utils.Cliente;
import retrofit2.Call;
import retrofit2.Response;

public class DetailReception extends AppCompatActivity {

    private TextView nroOC, proveedorName;
    private RecyclerView rvItemProductos;
    private PayVendor proveedorSelected;
    private PoPurchaseOrdersVw ocSelected;
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

        // Component
        nroOC = findViewById(R.id.dataNroOC);
        nroOC.setText(ocSelected.getPoNumber());
        proveedorName = findViewById(R.id.dataProveedorName);
        proveedorName.setText(proveedorSelected.getName());
        // RecyclerView Item
        rvItemProductos = findViewById(R.id.rvRcpItemsProductos);
        rvItemProductos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));


        // Cargamos los items de la OC
        itemsProductos = getItemsOC(Long.parseLong(ocSelected.getPoNumber()));
        if( itemsProductos != null )
        {
            Toast.makeText(getApplicationContext(), "Size : "+itemsProductos.size(),Toast.LENGTH_LONG).show();
            itemAdater = new PoPurchOrdersProdsVwAdapter(itemsProductos, new PoPurchOrdersProdsVwAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(PoPurchOrdersProdsVw item) {
                    //TODO Tomamos el item de producto a recepcionar
                    // Toast.makeText(getApplicationContext(),"Producto : "+item.getProductName(),Toast.LENGTH_LONG).show();
                    confirmarItem(item);
                }
            });
            rvItemProductos.setAdapter(itemAdater);

        }

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

        // Component Botones Item confirm
        Button btnDescartar, btnRecibir;
        btnDescartar = xview.findViewById(R.id.btnDescartar);
        btnRecibir = xview.findViewById(R.id.btnRecibir);

        alert.setView(xview);

        final AlertDialog itemRecibirDialog =  alert.create();
        itemRecibirDialog.setCanceledOnTouchOutside(false);


        //TODO Botones Listener
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

}