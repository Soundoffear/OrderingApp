package com.ejoapps.m2d2_sub.orderingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ejoapps.m2d2_sub.orderingapp.R;
import com.ejoapps.m2d2_sub.orderingapp.containers.CateringNameAndTypeData;

import java.util.List;

public class CateringOrderSummaryAdapter extends RecyclerView.Adapter<CateringOrderSummaryAdapter.CateringOrderSummaryViewHolder> {

    private Context tContext;
    private List<CateringNameAndTypeData> nameAndTypeDataList;

    public CateringOrderSummaryAdapter(Context tContext, List<CateringNameAndTypeData> nameAndTypeDataList) {
        this.tContext = tContext;
        this.nameAndTypeDataList = nameAndTypeDataList;
    }

    @Override
    public CateringOrderSummaryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutR = R.layout.catering_order_summary_item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(layoutR, parent, false);

        return new CateringOrderSummaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CateringOrderSummaryViewHolder holder, int position) {

        String name = nameAndTypeDataList.get(position).getCateringName();
        String price = nameAndTypeDataList.get(position).getsCateringPrice();

        holder.tv_name.setText(name);
        holder.tv_priceValue.setText(price);
        holder.tv_priceCurrency.setText(R.string.currency);

    }

    @Override
    public int getItemCount() {
        return nameAndTypeDataList.size();
    }

    class CateringOrderSummaryViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name;
        TextView tv_priceValue;
        TextView tv_priceCurrency;

        CateringOrderSummaryViewHolder(View view) {
            super(view);

            tv_name = view.findViewById(R.id.cat_order_sum_label);
            tv_priceValue = view.findViewById(R.id.cat_order_sum_price_value);
            tv_priceCurrency = view.findViewById(R.id.cat_order_sum_price_currency);
        }

    }

}
