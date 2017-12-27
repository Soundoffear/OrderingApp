package com.ejoapps.m2d2_sub.orderingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ejoapps.m2d2_sub.orderingapp.R;
import com.ejoapps.m2d2_sub.orderingapp.interfaces.TypeAndNumberClickListener;

import java.util.List;

public class SandwichListDetailAdapter extends RecyclerView.Adapter<SandwichListDetailAdapter.TypeAndNumberViewHolder> {

    private Context mContext;
    private List<String> listOfItems;

    private static TypeAndNumberClickListener numberClickListener;

    public SandwichListDetailAdapter(Context context, List<String> stringList, TypeAndNumberClickListener typeAndNumberClickListener) {
        this.mContext = context;
        this.listOfItems = stringList;
        numberClickListener = typeAndNumberClickListener;
    }

    @Override
    public TypeAndNumberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int listItemLayout = R.layout.sandwich_list_item_detail;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View detailListView = layoutInflater.inflate(listItemLayout, parent, false);

        return new TypeAndNumberViewHolder(detailListView);
    }

    @Override
    public void onBindViewHolder(TypeAndNumberViewHolder holder, int position) {

        String[] splittedString = listOfItems.get(position).split("_");
        String[] splittedPrice = splittedString[2].split(" ");

        holder.tvSubNameTemp.setText(splittedString[0]);
        holder.tvSubDescTemp.setText(splittedString[1]);
        holder.tvPriceTemp.setText(splittedPrice[0]);

    }

    @Override
    public int getItemCount() {
        return listOfItems.size();
    }

    public static class TypeAndNumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvSubNameTemp;
        TextView tvSubDescTemp;
        TextView tvPriceTemp;

        TypeAndNumberViewHolder(View itemView) {
            super(itemView);

            tvSubNameTemp = itemView.findViewById(R.id.sub_details_sub_name_label);
            tvSubDescTemp = itemView.findViewById(R.id.sub_details_sub_description_label);
            tvPriceTemp = itemView.findViewById(R.id.sub_details_sub_price_value);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            numberClickListener.onRecyclerViewClick(v, this.getLayoutPosition());
        }
    }

}
