package py.com.softpoint.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import py.com.softpoint.R;
import py.com.softpoint.pojos.PoPurchaseOrdersVw;
import py.com.softpoint.utils.NumberTools;

public class PoPurchaseOrdersVwAdapter extends RecyclerView.Adapter<PoPurchaseOrdersVwAdapter.ViewHolder> {

    private List<PoPurchaseOrdersVw> listaOrdened;
    final PoPurchaseOrdersVwAdapter.OnItemClickListener  listener;

    public interface OnItemClickListener {
        void onItemClick(PoPurchaseOrdersVw item);
    }



    public PoPurchaseOrdersVwAdapter(List<PoPurchaseOrdersVw> listaOrdened, PoPurchaseOrdersVwAdapter.OnItemClickListener listener) {
        this.listaOrdened = listaOrdened;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PoPurchaseOrdersVwAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orden_compras,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PoPurchaseOrdersVwAdapter.ViewHolder holder, int position) {
            holder.cargarDatos(listaOrdened.get(position));
    }

    @Override
    public int getItemCount() {
        return listaOrdened.size();
    }


    /**
    *  View Holder
    */
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNroOC, tvFechaOC, tvTipoOC, tvMontoOC;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNroOC = itemView.findViewById(R.id.itOCNro);
            tvFechaOC = itemView.findViewById(R.id.itOCFecha);
            tvTipoOC = itemView.findViewById(R.id.itOCTipo);
            tvMontoOC = itemView.findViewById(R.id.itMontoOC);

        }

        public void cargarDatos(final PoPurchaseOrdersVw poPurchaseOrdersVw) {
            tvNroOC.setText(poPurchaseOrdersVw.getPoNumber().trim());
            tvFechaOC.setText(poPurchaseOrdersVw.getPoDate().trim());
            tvTipoOC.setText(poPurchaseOrdersVw.getOrderType());
            tvMontoOC.setText(NumberTools.nroFormat(poPurchaseOrdersVw.getAmount()));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(poPurchaseOrdersVw);
                }
            });
        }
        /*private String nroFormat(Double amount) {
            DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.getDefault());
            formatter.applyPattern("#,###,###,###");
            String resp = formatter.format(amount).trim() ;
            return resp;
        }*/
    }
}
