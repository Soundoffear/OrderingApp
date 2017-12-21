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
import com.ejoapps.m2d2_sub.orderingapp.storage.SandwichListStorage;
import com.ejoapps.m2d2_sub.orderingapp.storage.TotalSandwichValue;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by soundoffear on 14/12/2017.
 */

public class PaidAddOnsAdapter extends RecyclerView.Adapter<PaidAddOnsAdapter.PaidAddsViewHolder> {

    private Context mContext;
    private List<PaidAddOnsData> paidAddOnsDataList;
    private String carrier;

    public PaidAddOnsAdapter(Context mContext, List<PaidAddOnsData> paidAddOnsDataList, String carrierType) {
        this.mContext = mContext;
        this.paidAddOnsDataList = paidAddOnsDataList;
        this.carrier = carrierType;
    }

    @Override
    public PaidAddsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutID = R.layout.sandwich_builder_paid_add_ons_list_items;

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(layoutID, parent, false);

        return new PaidAddsViewHolder(view);
    }

    private double priceDouble;

    @Override
    public void onBindViewHolder(final PaidAddsViewHolder holder, final int position) {
        final String nameString = paidAddOnsDataList.get(position).getPaidAddOnName();
        final String price = paidAddOnsDataList.get(position).getPaidAddOnPrice();
        if(carrier.equals(SandwichListStorage.CARRIER_SUB15)) {
            priceDouble = Double.parseDouble(price);
        } else if (carrier.equals(SandwichListStorage.CARRIER_SUB30)) {
            priceDouble = Double.parseDouble(price) * 2;
        } else if (carrier.equals(SandwichListStorage.CARRIER_WRAP)) {
            priceDouble = Double.parseDouble(price);
        } else if (carrier.equals(SandwichListStorage.CARRIER_SALAD)) {
            priceDouble = Double.parseDouble(price);
        }
        holder.tvPaidName.setText(nameString);

        TotalSandwichValue.paidAddOns = 0;

        DecimalFormat decimalPrice = new DecimalFormat("0.00");
        String decimalFormattedPrice = decimalPrice.format(priceDouble) + " PLN";

        holder.tvPriceSingle.setText(decimalFormattedPrice);

        Log.d("CHEESE TEST", String.valueOf(priceDouble));
        priceDouble = Double.parseDouble(paidAddOnsDataList.get(position).getPaidAddOnPrice());

        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSplit = holder.tvPriceTotal.getText().toString();
                String[] splittedString = toSplit.split(" ");
                double privatePriceDouble = Double.parseDouble(splittedString[0]);
                String currentQuantity = holder.tvIncrement.getText().toString();
                double currentQDouble = Double.parseDouble(currentQuantity);
                currentQDouble++;
                holder.tvIncrement.setText(String.valueOf((int) currentQDouble));

                Log.d("CHEESE TEST", (currentQDouble > 1) + " " + String.valueOf(priceDouble));

                if(carrier.equals(SandwichListStorage.CARRIER_SUB15)) {
                    priceDouble = Double.parseDouble(price);
                } else if (carrier.equals(SandwichListStorage.CARRIER_SUB30)) {
                    priceDouble = Double.parseDouble(price) * 2;
                } else if (carrier.equals(SandwichListStorage.CARRIER_WRAP)) {
                    priceDouble = Double.parseDouble(price);
                } else if (carrier.equals(SandwichListStorage.CARRIER_SALAD)) {
                    priceDouble = Double.parseDouble(price);
                }

                if (nameString.equals("Cheese")) {
                    if (currentQDouble > 1) {
                        TotalSandwichValue.paidAddOns = TotalSandwichValue.paidAddOns + priceDouble;
                        privatePriceDouble = privatePriceDouble + priceDouble;
                        Log.d("Test 0", String.valueOf(TotalSandwichValue.paidAddOns) + " " + String.valueOf(priceDouble) + " " + TotalSandwichValue.paidAddOns);
                        SandwichBuilderActivity.setFinalPrice(TotalSandwichValue.finalValue());
                        String finalPrice = setAlwaysTwoDec(privatePriceDouble) + " PLN";
                        Log.d("Test:", String.valueOf(finalPrice));
                        holder.tvPriceTotal.setText(finalPrice);
                    }
                } else {
                    TotalSandwichValue.paidAddOns = TotalSandwichValue.paidAddOns + priceDouble;
                    privatePriceDouble = privatePriceDouble + priceDouble;
                    String finalPrice = setAlwaysTwoDec(privatePriceDouble) + " PLN";
                    holder.tvPriceTotal.setText(finalPrice);
                    SandwichBuilderActivity.setFinalPrice(TotalSandwichValue.finalValue());
                }

            }
        });

        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSplit = holder.tvPriceTotal.getText().toString();
                String[] splittedString = toSplit.split(" ");
                double privatePriceDouble = Double.parseDouble(splittedString[0]);
                final String nameString = paidAddOnsDataList.get(position).getPaidAddOnName();
                final String price = paidAddOnsDataList.get(position).getPaidAddOnPrice();
                String currentQuantity = holder.tvIncrement.getText().toString();
                double currentQDouble = Double.parseDouble(currentQuantity);

                if(carrier.equals(SandwichListStorage.CARRIER_SUB15)) {
                    priceDouble = Double.parseDouble(price);
                } else if (carrier.equals(SandwichListStorage.CARRIER_SUB30)) {
                    priceDouble = Double.parseDouble(price) * 2;
                } else if (carrier.equals(SandwichListStorage.CARRIER_WRAP)) {
                    priceDouble = Double.parseDouble(price);
                } else if (carrier.equals(SandwichListStorage.CARRIER_SALAD)) {
                    priceDouble = Double.parseDouble(price);
                }

                if (currentQDouble >= 1) {

                    currentQDouble--;
                    if(nameString.equals("Cheese")) {
                        if(currentQDouble > 0) {
                            Log.d("Test 3", String.valueOf(TotalSandwichValue.paidAddOns) + " " + priceDouble);
                            TotalSandwichValue.paidAddOns = TotalSandwichValue.paidAddOns - priceDouble;
                            privatePriceDouble = privatePriceDouble - priceDouble;
                            Log.d("Test 4", String.valueOf(TotalSandwichValue.paidAddOns) + " " + priceDouble);
                            SandwichBuilderActivity.setFinalPrice(TotalSandwichValue.finalValue());
                            String finalPrice = setAlwaysTwoDec(privatePriceDouble) + " PLN";
                            holder.tvPriceTotal.setText(finalPrice);
                        }
                    } else {
                        TotalSandwichValue.paidAddOns = TotalSandwichValue.paidAddOns - priceDouble;
                        privatePriceDouble = privatePriceDouble - priceDouble;
                        SandwichBuilderActivity.setFinalPrice(TotalSandwichValue.finalValue());
                        String finalPrice = setAlwaysTwoDec(privatePriceDouble) + " PLN";
                        holder.tvPriceTotal.setText(finalPrice);
                    }
                } else {
                    currentQDouble = 0;
                }
                holder.tvIncrement.setText(String.valueOf((int) currentQDouble));

            }
        });

    }

    private String setAlwaysTwoDec(double value) {

        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        return decimalFormat.format(value);
    }

    private double updateQuanityAndFinalPrice(double currentQuanity, double valuePerOne) {
        double finalValueDouble = 0;
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
