package com.ejoapps.m2d2_sub.orderingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ejoapps.m2d2_sub.orderingapp.R;
import com.ejoapps.m2d2_sub.orderingapp.containers.FinalSandwichData;
import com.ejoapps.m2d2_sub.orderingapp.storage.SandwichListStorage;

import java.util.List;

/**
 * Created by soundoffear on 17/12/2017.
 */

public class OrderSummaryAdapter extends RecyclerView.Adapter<OrderSummaryAdapter.OrderSummaryViewHolder> {

    Context mContext;
    List<FinalSandwichData> finalSandwichDataList;

    public OrderSummaryAdapter(Context mContext, List<FinalSandwichData> finalSandwichDataList) {
        this.mContext = mContext;
        this.finalSandwichDataList = finalSandwichDataList;
    }

    @Override
    public OrderSummaryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutOrder = R.layout.order_summary_sandwich_final;

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View orderView = layoutInflater.inflate(layoutOrder, parent, false);

        return new OrderSummaryViewHolder(orderView);
    }

    @Override
    public void onBindViewHolder(OrderSummaryViewHolder holder, int position) {
        String sandwichName = finalSandwichDataList.get(position).getSubName();
        String breadType = finalSandwichDataList.get(position).getSubBread();
        String paidAddOns = finalSandwichDataList.get(position).getSubPaid();
        String vegetables = finalSandwichDataList.get(position).getSubVege();
        String sauces = finalSandwichDataList.get(position).getSubSauce();
        String finalPrice = finalSandwichDataList.get(position).getSubPrice();

        Log.d("SANDWICH NAME", sandwichName);
        Log.d("BREAD TYPE", breadType);
        Log.d("PAID ADD ONS", buildNewString(breakDownString(paidAddOns)));
        Log.d("VEGES", buildNewString(breakDownString(vegetables)));
        Log.d("SAUCES", buildNewString(breakDownString(sauces)));
        Log.d("FINAL PRICE", finalPrice);

        holder.tv_sandwichName.setText(sandwichName);
        holder.tv_breadType.setText(breadType);
        holder.tv_sandwichPaidAdds.setText(buildNewString(breakDownString(paidAddOns)));
        holder.tv_sandwichVeges.setText(buildNewString(breakDownString(vegetables)));
        holder.tv_sandwichSauce.setText(buildNewString(breakDownString(sauces)));
        holder.tv_sandwichPrice.setText(finalPrice);
    }

    @Override
    public int getItemCount() {
        return finalSandwichDataList.size();
    }


    class OrderSummaryViewHolder extends RecyclerView.ViewHolder {

        TextView tv_sandwichName;
        TextView tv_sandwichPrice;
        TextView tv_breadType;
        TextView tv_sandwichDescription;
        TextView tv_sandwichPaidAdds;
        TextView tv_sandwichVeges;
        TextView tv_sandwichSauce;

        public OrderSummaryViewHolder(View itemView) {
            super(itemView);

            tv_sandwichName = itemView.findViewById(R.id.summary_tv_sandwich_label);
            tv_sandwichPrice = itemView.findViewById(R.id.summary_price);
            tv_breadType = itemView.findViewById(R.id.summary_bread_tv_sandwich);
            tv_sandwichDescription = itemView.findViewById(R.id.summary_description_tv_sandwich);
            tv_sandwichPaidAdds = itemView.findViewById(R.id.summary_paid_addOns);
            tv_sandwichVeges = itemView.findViewById(R.id.summary_veges);
            tv_sandwichSauce = itemView.findViewById(R.id.summary_sauces);

        }


    }

    private String[] breakDownString(String fullString) {

        return fullString.split("_");
    }

    private String buildNewString(String[] brokenString) {
        StringBuilder builtString = new StringBuilder();
        for(int i = 0; i<brokenString.length;i++) {
            builtString.append(brokenString[i]).append(", ");
        }

        return builtString.toString();
    }

    private double getPriceDouble(String stringCurrency) {

        String[] splittedFromCurrency = stringCurrency.split(" ");
        return Double.parseDouble(splittedFromCurrency[0]);
    }
}
