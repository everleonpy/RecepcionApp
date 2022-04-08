package py.com.softpoint.adapters;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import py.com.softpoint.HeaderReception;
import py.com.softpoint.R;
import py.com.softpoint.pojos.PayVendor;
import py.com.softpoint.pojos.User;

public class PayVendorAdapter extends RecyclerView.Adapter<PayVendorAdapter.ViewHolder> {

    private List<PayVendor> listaOriginal;
    private List<PayVendor> listaCompleta;
    private  User userLoged;
    private String URL_BASE;


    public PayVendorAdapter(List<PayVendor> listaOriginal, User userLoged, String baseUrl) {
        this.listaOriginal = listaOriginal;
        this.listaCompleta  = new ArrayList<>();
        this.userLoged = userLoged;
        this.URL_BASE = baseUrl;
        listaCompleta.addAll(listaOriginal);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_proveedor,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.asignarDatos(listaOriginal.get(position));
    }

    /**
     * Metodo que filtra la lista
     * @param txt
     */
    public void filtraLista(String txt)
    {
        int  size = txt.length();
        if( size == 0 )
            {
                listaOriginal.clear();
                listaOriginal.addAll(listaCompleta);
            }else
            {

                List<PayVendor> provs = listaOriginal.stream()
                        .filter(r -> r.getName().trim().toLowerCase().contains(txt.toLowerCase()))
                        .collect(Collectors.toList());
                listaOriginal.clear();
                listaOriginal.addAll(provs);
            }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listaOriginal.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        //implements View.OnClickListener
        TextView txtNombreProveedor, txtRuc, txtId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombreProveedor = itemView.findViewById(R.id.txtNombreProveedor);
            txtRuc = itemView.findViewById(R.id.txtRuc);
            txtId = itemView.findViewById(R.id.txtId);

            //Agregamos el evento Click a cada registro
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, HeaderReception.class);
                    intent.putExtra("PAY_VENDOR", listaOriginal.get(getAdapterPosition()));
                    intent.putExtra("USER_LOGED",userLoged);
                    intent.putExtra("URL_BASE",URL_BASE);
                    context.startActivity(intent);
                }
            });
        }

        public void asignarDatos(PayVendor payVendor) {
            txtNombreProveedor.setText(payVendor.getName().trim());
            txtRuc.setText("RUC : "+payVendor.getTaxNumber());
            txtId.setText("Id : "+payVendor.getIdentifier());
        }

    }
 }
