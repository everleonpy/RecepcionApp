package py.com.softpoint.adapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import py.com.softpoint.R;
import py.com.softpoint.apiclient.InvPoReceivingItemApi;
import py.com.softpoint.apiclient.InvUnitOfMeasureApi;
import py.com.softpoint.pojos.InvPoReceivingItem;
import py.com.softpoint.pojos.InvUnitOfMeasure;
import py.com.softpoint.utils.Cliente;
import retrofit2.Call;
import retrofit2.Response;


/**
* https://www.develou.com/android-recyclerview-cardview/
*
*/
public class InvPoReceivingItemAdapter  extends RecyclerView.Adapter<InvPoReceivingItemAdapter.ViewHolder> {


    private List<InvPoReceivingItem> itemsCompleto;
    private List<InvPoReceivingItem> itemsFiltrado;
    private List<InvUnitOfMeasure> listUm;
    private boolean isdeleted;
    String urlBase;

    /**
    * <p> Constructor para cargar la lista de items procesados ya en la recepcion </p>
    * @param itemsCompleto
    */
    public InvPoReceivingItemAdapter( List<InvPoReceivingItem> itemsCompleto, String urlBase )
    {
        this.itemsCompleto = itemsCompleto;
        this.itemsFiltrado = new ArrayList<>();
        this.itemsFiltrado.addAll(itemsCompleto);
        this.urlBase = urlBase;
        isdeleted = false;
        listUm = cargarLista();

    }

    @NonNull
    @Override
    public InvPoReceivingItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_receiving, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InvPoReceivingItemAdapter.ViewHolder holder, int position)
    {
        holder.asignarDatos(itemsCompleto.get(position));
    }

    @Override
    public int getItemCount() {
        return itemsFiltrado.size() ;
    }


    /**
     * <p> Holder </p>
     */
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tvRcvItemDescripcion, tvRcvItemCodeBar, tvRcvUM, tvRcvCantidad;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRcvItemDescripcion = itemView.findViewById(R.id.tvRcvItemDescripcion);
            tvRcvItemCodeBar = itemView.findViewById(R.id.tvRcvItemCodeBar);
            tvRcvUM = itemView.findViewById(R.id.tvRcvUM);
            tvRcvCantidad = itemView.findViewById(R.id.tvRcvCantidad);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO Preguntar si se quiere eliminar item seleccionado
                    Log.i("ITEM_REC", "Data : "+itemsFiltrado.get(getAdapterPosition()).getDescription());
                    InvPoReceivingItem xdata = itemsFiltrado.get(getAdapterPosition());
                    AlertDialog.Builder xmenu = new AlertDialog.Builder(itemView.getContext());
                    xmenu.setTitle("Desea eliminar este Item ?");
                    xmenu.setMessage(" Codigo : "+xdata.getInvMtlBarCode()+"\n"+
                                     " Descripcion : "+xdata.getDescription()+"\n" +
                                     " Cantidad : "+formatoNro(xdata.getQuantity()));
                    xmenu.setCancelable(false);
                    xmenu.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    xmenu.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if( isDeleted(xdata.getIdentifier()) ) {
                                //Toast.makeText(itemView.getContext(), "ITEM ELIMINADO : " + xdata.getDescription(), Toast.LENGTH_LONG).show();
                                itemsFiltrado.remove(getAbsoluteAdapterPosition());
                                notifyItemRemoved(getAbsoluteAdapterPosition());
                                //notifyDataSetChanged();
                            }else {
                                Toast.makeText(itemView.getContext(), "ERROR : No se pudo eliminar : " + xdata.getDescription(), Toast.LENGTH_LONG).show();
                            }
                            dialog.dismiss();
                        }
                    });
                    xmenu.show();
                }
            });
        }

        public void asignarDatos(InvPoReceivingItem invr)
        {
            tvRcvItemDescripcion.setText(invr.getDescription());
            tvRcvItemCodeBar.setText(invr.getInvMtlBarCode());
            tvRcvUM.setText(getUmDescription(invr.getItemUomId()));
            tvRcvCantidad.setText(formatoNro(invr.getQuantity() ));
        }
    }

    private String formatoNro(Double nro)
    {
        //Locale locale = new Locale(Locale.US);
        String patter = "#.###.###,##";
        DecimalFormat decimalFormat = (DecimalFormat)
                NumberFormat.getNumberInstance(Locale.US);
        return decimalFormat.format(nro);
    }


    private String getUmDescription(Long itemUomId)
    {
        for (InvUnitOfMeasure um : listUm) {
            if ( um.getIdentifier() == itemUomId ) {
                return um.getDescription();
            }
        }
        return "";
    }


    /**
     * Metodo encargado de cargar la lista de unidades de medidas exitentes
     * @return
     */
    private List<InvUnitOfMeasure> cargarLista() {
        InvUnitOfMeasureApi umApi = Cliente.getClient(urlBase).create(InvUnitOfMeasureApi.class);
        Call<List<InvUnitOfMeasure>> call = umApi.getAll();

        try {
            Thread hilo = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Response<List<InvUnitOfMeasure>> httpResp = call.execute();
                        if( httpResp.isSuccessful() && httpResp.code() == 200 )
                        {
                            listUm = httpResp.body();
                        }else{
                            if( httpResp.code() == 204 ){
                                Log.i("PROD : ","NO HAY LISTA DE UNIDADES DE MEDIDA");
                                listUm = null;
                            }
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
        return listUm;
    }

    /**
    * <p> Elimina un item coletado en Recepcion </p>
    * @param id
    * @return
    */
    public boolean isDeleted(Long id)
    {
        InvPoReceivingItemApi api = Cliente.getClient(urlBase).create(InvPoReceivingItemApi.class);
        Call<String> call = api.deleteItem(id);

        try {
                Thread hilo = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Response<String> httpResp = call.execute();
                        Log.i("DELETE","Code : "+httpResp.code());
                        if( httpResp.isSuccessful() && httpResp.code() == 200 )
                        {
                            String msg = httpResp.body();
                            Log.i("DELETE","MSG : "+msg);
                            isdeleted = true;
                        }else{
                            isdeleted = false;
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

        return isdeleted;
    }

}
