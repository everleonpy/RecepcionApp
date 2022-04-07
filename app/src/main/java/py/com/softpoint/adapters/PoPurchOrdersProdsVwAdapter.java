package py.com.softpoint.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import py.com.softpoint.R;
import py.com.softpoint.pojos.PoPurchOrdersProdsVw;

public class PoPurchOrdersProdsVwAdapter extends RecyclerView.Adapter<PoPurchOrdersProdsVwAdapter.ViewHolder> {

    private List<PoPurchOrdersProdsVw> listItemOC;

    final PoPurchOrdersProdsVwAdapter.OnItemClickListener  listener;

    public interface OnItemClickListener {
        void onItemClick(PoPurchOrdersProdsVw item);
    }

    public PoPurchOrdersProdsVwAdapter(List<PoPurchOrdersProdsVw> listItemOC, PoPurchOrdersProdsVwAdapter.OnItemClickListener  listener) {
        this.listItemOC = listItemOC;
        this.listener = listener;

    }

    @NonNull
    @Override
    public PoPurchOrdersProdsVwAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_oc_productos,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PoPurchOrdersProdsVwAdapter.ViewHolder holder, int position) {
            holder.cargarItems(listItemOC.get(position));
    }

    @Override
    public int getItemCount() {
        return listItemOC.size();
    }


     /**
     * ViewHolder
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemOcCodeBar, tvItemOcDescripcion, tvItemOcUM,tvItemOcCantOC, tvItemOcCantRCP;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvItemOcCodeBar = itemView.findViewById(R.id.rcpItemCodeBar);
            tvItemOcDescripcion = itemView.findViewById(R.id.rcpItemDescription);
            tvItemOcUM = itemView.findViewById(R.id.rcpItemUM);
            tvItemOcCantOC = itemView.findViewById(R.id.rcpItemCantOC);
            tvItemOcCantRCP = itemView.findViewById(R.id.rcpItemCantRCP);
        }

        public void cargarItems(PoPurchOrdersProdsVw poPurchOrdersProdsVw) {
            tvItemOcCodeBar.setText(poPurchOrdersProdsVw.getBarCode());
            tvItemOcDescripcion.setText(poPurchOrdersProdsVw.getProductName());
            tvItemOcUM.setText(poPurchOrdersProdsVw.getUomName());
            tvItemOcCantOC.setText(""+poPurchOrdersProdsVw.getQuantity());
            tvItemOcCantRCP.setText(""+poPurchOrdersProdsVw.getReceivedQty());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(poPurchOrdersProdsVw);
                }
            });
        }
    }
}
