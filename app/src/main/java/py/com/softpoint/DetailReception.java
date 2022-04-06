package py.com.softpoint;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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
            itemAdater = new PoPurchOrdersProdsVwAdapter(itemsProductos);
            rvItemProductos.setAdapter(itemAdater);

        }






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