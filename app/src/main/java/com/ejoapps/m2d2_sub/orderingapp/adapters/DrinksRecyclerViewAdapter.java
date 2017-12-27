package com.ejoapps.m2d2_sub.orderingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ejoapps.m2d2_sub.orderingapp.DrinksPickerActivity;
import com.ejoapps.m2d2_sub.orderingapp.R;
import com.ejoapps.m2d2_sub.orderingapp.containers.DrinksData;

import java.text.DecimalFormat;
import java.util.List;

public class DrinksRecyclerViewAdapter extends RecyclerView.Adapter<DrinksRecyclerViewAdapter.DrinksViewHolder> {

    private List<DrinksData> drinksData;

    public DrinksRecyclerViewAdapter(List<DrinksData> drinksData) {
        this.drinksData = drinksData;
    }

    @Override
    public DrinksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutR = R.layout.drinks_list_item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(layoutR, parent, false);
        return new DrinksViewHolder(view);
    }

    private int drinksCount = 0;
    @Override
    public void onBindViewHolder(final DrinksViewHolder holder, int position) {

        String drinkName = drinksData.get(position).getDrinkName();
        final String drinkPrice = drinksData.get(position).getDrinkPrice();
        holder.tv_dName.setText(drinkName);
        holder.tv_priceFinal.setText(new DecimalFormat("0.00").format(Double.parseDouble(drinkPrice)));
        holder.tv_currency.setText(R.string.currency);

        holder.im_add_drink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drinksCount = Integer.parseInt(holder.tv_counter.getText().toString());
                drinksCount++;
                holder.tv_counter.setText(String.valueOf(drinksCount));
                DrinksPickerActivity.setTotalPriceFinal(Double.parseDouble(drinkPrice));
                double tempPrice = drinksCount * Double.parseDouble(drinkPrice);
                holder.tv_priceFinal.setText(new DecimalFormat("0.00").format(tempPrice));
            }
        });

        holder.im_remove_drink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drinksCount = Integer.parseInt(holder.tv_counter.getText().toString());
                if(drinksCount >= 1) {
                    drinksCount--;
                    holder.tv_counter.setText(String.valueOf(drinksCount));
                    if(drinksCount == 0) {
                        holder.tv_priceFinal.setText(new DecimalFormat("0.00").format(Double.parseDouble(drinkPrice)));
                        DrinksPickerActivity.setTotalPriceFinal(-Double.parseDouble(drinkPrice));
                        holder.tv_counter.setText(R.string.zero);
                    } else {
                        double tempPrice = drinksCount * Double.parseDouble(drinkPrice);
                        holder.tv_priceFinal.setText(new DecimalFormat("0.00").format(tempPrice));
                        DrinksPickerActivity.setTotalPriceFinal(-Double.parseDouble(drinkPrice));
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return drinksData.size();
    }


    class DrinksViewHolder extends RecyclerView.ViewHolder {

        TextView tv_dName;
        TextView tv_priceFinal;
        TextView tv_counter;
        TextView tv_currency;
        ImageButton im_add_drink;
        ImageButton im_remove_drink;

        DrinksViewHolder(View view) {
            super(view);
            tv_dName = view.findViewById(R.id.drinks_item_name_check_box);
            tv_priceFinal = view.findViewById(R.id.drinks_item_price_value);
            tv_counter = view.findViewById(R.id.drinks_item_drink_counter);
            tv_currency = view.findViewById(R.id.drinks_item_price_currency);
            im_add_drink = view.findViewById(R.id.drinks_item_ad_item_item);
            im_remove_drink = view.findViewById(R.id.drinks_item_remove_item);
        }

    }

}
