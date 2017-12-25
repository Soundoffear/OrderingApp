package com.ejoapps.m2d2_sub.orderingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ejoapps.m2d2_sub.orderingapp.R;
import com.ejoapps.m2d2_sub.orderingapp.containers.CateringNameAndTypeData;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by soundoffear on 24/12/2017.
 */

public class CateringOrderSummaryAdapter extends RecyclerView.Adapter<CateringOrderSummaryAdapter.CateringOrderSummaryViewHolder> {

    Context tContext;
    List<CateringNameAndTypeData> nameAndTypeDataList;

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
        double price = nameAndTypeDataList.get(position).getCateringPrice();

        String priceFormated = new DecimalFormat("0.00").format(price) + " PLN";

        holder.tv_name.setText(name);
        holder.tv_price.setText(priceFormated);

    }

    @Override
    public int getItemCount() {
        return nameAndTypeDataList.size();
    }

    class CateringOrderSummaryViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name;
        TextView tv_price;

        public CateringOrderSummaryViewHolder(View view) {
            super(view);

            tv_name = view.findViewById(R.id.cat_order_sum_label);
            tv_price = view.findViewById(R.id.cat_order_sum_price);
        }

    }

}
