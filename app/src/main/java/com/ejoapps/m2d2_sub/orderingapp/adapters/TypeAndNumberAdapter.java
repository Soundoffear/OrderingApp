package com.ejoapps.m2d2_sub.orderingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ejoapps.m2d2_sub.orderingapp.R;

/**
 * Created by soundoffear on 12/12/2017.
 */

public class TypeAndNumberAdapter extends RecyclerView.Adapter<TypeAndNumberAdapter.TypeAndNumberViewHolder> {

    private Context mContext;
    private int aNoOfItems;
    private String aNameOfItem;

    public TypeAndNumberAdapter(Context context, int noOfItems, String nameOfItem) {
        this.mContext = context;
        this.aNoOfItems = noOfItems;
        this.aNameOfItem = nameOfItem;
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

        holder.tvSubNameTemp.setText(aNameOfItem);
        holder.tvSubDescTemp.setText(aNameOfItem);
        holder.tvPriceTemp.setText(R.string.tempPrice);

    }

    @Override
    public int getItemCount() {
        return aNoOfItems;
    }

    class TypeAndNumberViewHolder extends RecyclerView.ViewHolder {

        TextView tvSubNameTemp;
        TextView tvSubDescTemp;
        TextView tvPriceTemp;

        public TypeAndNumberViewHolder(View itemView) {
            super(itemView);

            tvSubNameTemp = itemView.findViewById(R.id.sub_details_sub_name_label);
            tvSubDescTemp = itemView.findViewById(R.id.sub_details_sub_description_label);
            tvPriceTemp = itemView.findViewById(R.id.sub_details_total_price_for_sub);
        }
    }

}
