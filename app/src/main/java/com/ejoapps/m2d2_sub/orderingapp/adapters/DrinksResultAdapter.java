package com.ejoapps.m2d2_sub.orderingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ejoapps.m2d2_sub.orderingapp.R;
import com.ejoapps.m2d2_sub.orderingapp.containers.OrderedDrinksData;

import java.util.List;

public class DrinksResultAdapter extends RecyclerView.Adapter<DrinksResultAdapter.DrinksResultViewHolder> {

    private List<OrderedDrinksData> orderedDrinksDataList;

    public DrinksResultAdapter(List<OrderedDrinksData> orderedDrinksDataList) {
        this.orderedDrinksDataList = orderedDrinksDataList;
    }

    @Override
    public DrinksResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutR = R.layout.drinks_list_item_results;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(layoutR, parent, false);
        return new DrinksResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DrinksResultViewHolder holder, int position) {

        holder.tv_drinksName.setText(orderedDrinksDataList.get(position).getDrinkName());
        holder.tv_drinksQuantity.setText(orderedDrinksDataList.get(position).getDrinkQuantity());
        holder.tv_drinksPriceSum.setText(orderedDrinksDataList.get(position).getDrinkPrice());

    }

    @Override
    public int getItemCount() {
        return orderedDrinksDataList.size();
    }

    class DrinksResultViewHolder extends RecyclerView.ViewHolder {

        TextView tv_drinksName;
        TextView tv_drinksQuantity;
        TextView tv_drinksPriceSum;

        DrinksResultViewHolder(View view) {
            super(view);

            tv_drinksName = view.findViewById(R.id.drinks_result_name);
            tv_drinksQuantity = view.findViewById(R.id.drinks_result_quantity);
            tv_drinksPriceSum = view.findViewById(R.id.drinks_result_price_value);
        }

    }
}
