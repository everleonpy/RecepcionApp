package py.com.softpoint;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import java.io.IOException;
import android.widget.SearchView;
import java.util.List;
import py.com.softpoint.adapters.InvPoReceivingItemAdapter;
import py.com.softpoint.apiclient.InvPoReceivingItemApi;
import py.com.softpoint.pojos.InvPoReceivingItem;
import py.com.softpoint.utils.Cliente;
import retrofit2.Call;
import retrofit2.Response;

public class ItemsRecepcionados extends AppCompatActivity
{
    private List<InvPoReceivingItem> itemsRecepcionados;
    private SearchView txtBuscar;
    private RecyclerView rvItemsRecepcionados;
    private InvPoReceivingItemAdapter adapter;
    private Long idOrdenCompras;
    private Integer idRecepcion;
    private String baseUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_recepcionados);
        // Datos Extras
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        idOrdenCompras = (Long) bd.get("ORDER_ID");
        idRecepcion = (Integer) bd.get("RECEIV_ID");
        baseUrl = (String) bd.get("BASE_URL");

        initComponent();
    }

    private void initComponent() {
        txtBuscar = findViewById(R.id.txtSearchItems);
        rvItemsRecepcionados = findViewById(R.id.rvItemsRecepcion);
        rvItemsRecepcionados.setLayoutManager( new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false) );

        itemsRecepcionados = cargarItems(idOrdenCompras, idRecepcion.longValue());
        if( itemsRecepcionados.size() > 0 ){
            adapter = new InvPoReceivingItemAdapter(itemsRecepcionados, baseUrl);
            rvItemsRecepcionados.setAdapter(adapter);

        }else{
            Toast.makeText(getApplicationContext(),"SIN ITEMS",Toast.LENGTH_LONG).show();
            super.onBackPressed();
        }

    }

    /**
    *
    * @param idOrdenCompras
    * @param idRecepcion
    * @return
    */
    private List<InvPoReceivingItem> cargarItems(Long idOrdenCompras, Long idRecepcion) {
        InvPoReceivingItemApi api = Cliente.getClient(baseUrl).create(InvPoReceivingItemApi.class);
        Call<List<InvPoReceivingItem>> call = api.getItems(idRecepcion, idOrdenCompras);

        try {
            Thread hiloGet = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Response<List<InvPoReceivingItem>> httpGet = call.execute();
                        if ( httpGet.isSuccessful() && httpGet.code() == 200 )
                        {
                            itemsRecepcionados = httpGet.body();
                            Log.i("ITEMS_RECUPERADOS","Size : "+itemsRecepcionados.size());
                        }else{
                            Toast.makeText(getApplicationContext(),"Code Error "+httpGet.message(),Toast.LENGTH_LONG).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            hiloGet.start();
            hiloGet.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return itemsRecepcionados;

    }
}