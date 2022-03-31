package py.com.softpoint.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import py.com.softpoint.R;
import py.com.softpoint.pojos.PoPurchaseOrdersVw;

public class PoPurchaseOrdersVwAdapter extends RecyclerView.Adapter<PoPurchaseOrdersVwAdapter.ViewHolder> {

    private List<PoPurchaseOrdersVw> listaOrdened;


    public PoPurchaseOrdersVwAdapter(List<PoPurchaseOrdersVw> listaOrdened) {
        this.listaOrdened = listaOrdened;
    }

    @NonNull
    @Override
    public PoPurchaseOrdersVwAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orden_compras,null, true);
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
        TextView tvNroOC, tvFechaOC, tvTipoOC;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNroOC = itemView.findViewById(R.id.itOCNro);
            tvFechaOC = itemView.findViewById(R.id.itOCFecha);
            tvTipoOC = itemView.findViewById(R.id.itOCTipo);

        }

        public void cargarDatos(PoPurchaseOrdersVw poPurchaseOrdersVw) {
            tvNroOC.setText(poPurchaseOrdersVw.getPoNumber().trim());
            tvFechaOC.setText(poPurchaseOrdersVw.getPoDate().toString());
            tvTipoOC.setText(poPurchaseOrdersVw.getOrderType());
        }
    }
}
