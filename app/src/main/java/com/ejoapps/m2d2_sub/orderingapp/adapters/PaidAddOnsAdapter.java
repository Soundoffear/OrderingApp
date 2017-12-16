package com.ejoapps.m2d2_sub.orderingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ejoapps.m2d2_sub.orderingapp.R;
import com.ejoapps.m2d2_sub.orderingapp.SandwichBuilderActivity;
import com.ejoapps.m2d2_sub.orderingapp.containers.PaidAddOnsData;
import com.ejoapps.m2d2_sub.orderingapp.storage.TotalSandwichValue;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by soundoffear on 14/12/2017.
 */

public class PaidAddOnsAdapter extends RecyclerView.Adapter<PaidAddOnsAdapter.PaidAddsViewHolder> {

    private Context mContext;
    private List<PaidAddOnsData> paidAddOnsDataList;

    public PaidAddOnsAdapter(Context mContext, List<PaidAddOnsData> paidAddOnsDataList) {
        this.mContext = mContext;
        this.paidAddOnsDataList = paidAddOnsDataList;
    }

    @Override
    public PaidAddsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutID = R.layout.sandwich_builder_paid_add_ons_list_items;

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(layoutID, parent, false);

        return new PaidAddsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PaidAddsViewHolder holder, final int position) {
        final String nameString = paidAddOnsDataList.get(position).getPaidAddOnName();
        final String price = paidAddOnsDataList.get(position).getPaidAddOnPrice();
        final double priceDouble = Double.parseDouble(price);
        holder.tvPaidName.setText(nameString);
        holder.tvPriceSingle.setText(price + " PLN ");

        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentQuantity = holder.tvIncrement.getText().toString();
                double currentQDouble = Double.parseDouble(currentQuantity);
                currentQDouble++;
                holder.tvIncrement.setText(String.valueOf((int) currentQDouble));

                Log.d("CHEESE TEST", (currentQDouble > 1) + " " + nameString.equals("Cheese"));

                if (nameString.equals("Cheese")) {
                    if (currentQDouble > 1) {
                        TotalSandwichValue.paidAddOns = TotalSandwichValue.paidAddOns + priceDouble;
                        SandwichBuilderActivity.setFinalPrice(TotalSandwichValue.finalValue());
                        String finalPrice = setAlwaysTwoDec(updateQuanityAndFinalPrice((currentQDouble - 1), price)) + " PLN";
                        holder.tvPriceTotal.setText(finalPrice);
                    }
                } else {
                    String finalPrice = setAlwaysTwoDec(updateQuanityAndFinalPrice(currentQDouble, price)) + " PLN";
                    holder.tvPriceTotal.setText(finalPrice);
                    TotalSandwichValue.paidAddOns = TotalSandwichValue.paidAddOns + priceDouble;
                    SandwichBuilderActivity.setFinalPrice(TotalSandwichValue.finalValue());
                }


            }
        });

        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nameString = paidAddOnsDataList.get(position).getPaidAddOnName();
                final String price = paidAddOnsDataList.get(position).getPaidAddOnPrice();
                final double priceDouble = Double.parseDouble(price);
                String currentQuantity = holder.tvIncrement.getText().toString();
                double currentQDouble = Double.parseDouble(currentQuantity);
                if (currentQDouble > 0) {
                    if(nameString.equals("Cheese")) {
                        if(currentQDouble > 1) {
                            TotalSandwichValue.paidAddOns = TotalSandwichValue.paidAddOns - priceDouble;
                            SandwichBuilderActivity.setFinalPrice(TotalSandwichValue.finalValue());
                        }
                    } else {
                        TotalSandwichValue.paidAddOns = TotalSandwichValue.paidAddOns - priceDouble;
                        SandwichBuilderActivity.setFinalPrice(TotalSandwichValue.finalValue());
                    }
                    currentQDouble--;
                } else {
                    currentQDouble = 0;
                }
                holder.tvIncrement.setText(String.valueOf((int) currentQDouble));
                String finalPrice = setAlwaysTwoDec(updateQuanityAndFinalPrice(currentQDouble, price)) + " PLN";
                holder.tvPriceTotal.setText(finalPrice);

            }
        });

    }

    private String setAlwaysTwoDec(double value) {

        double twoDecDouble = value * 100;
        twoDecDouble = twoDecDouble / 100;

        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        return decimalFormat.format(twoDecDouble);
    }

    private double updateQuanityAndFinalPrice(double currentQuanity, String valuePerOne) {
        double finalValueDouble = currentQuanity * Double.parseDouble(valuePerOne);
        return finalValueDouble;
    }

    @Override
    public int getItemCount() {
        return paidAddOnsDataList.size();
    }

    class PaidAddsViewHolder extends RecyclerView.ViewHolder {

        TextView tvPaidName;
        TextView tvIncrement;
        TextView tvPriceSingle;
        TextView tvPriceTotal;

        ImageButton btnRemove;
        ImageButton btnAdd;

        public PaidAddsViewHolder(View itemView) {
            super(itemView);

            tvPaidName = itemView.findViewById(R.id.sub_build_paid_adds_name_label);
            tvIncrement = itemView.findViewById(R.id.sub_build_paid_adds_quantity);
            tvPriceSingle = itemView.findViewById(R.id.sub_build_paid_adds_price_per_one);
            tvPriceTotal = itemView.findViewById(R.id.sub_build_paid_adds_price_final);

            btnRemove = itemView.findViewById(R.id.sub_build_paid_adds_remove_button);
            btnAdd = itemView.findViewById(R.id.sub_build_paid_adds_add_button);
        }
    }

}
