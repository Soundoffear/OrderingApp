package com.ejoapps.m2d2_sub.orderingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ejoapps.m2d2_sub.orderingapp.R;

import java.text.DecimalFormat;
import java.util.List;


public class CateringOrdersRecViewAdapter extends RecyclerView.Adapter<CateringOrdersRecViewAdapter.CateringOrdersViewHolder> {

    private Context rContext;
    private List<String> listOfCatering;

    private int itemCounter = 0;
    public CateringOrdersRecViewAdapter(Context rContext, List<String> listOfCatering) {
        this.rContext = rContext;
        this.listOfCatering = listOfCatering;
    }

    @Override
    public CateringOrdersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int recyclerLayout = R.layout.catering_item_list;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(recyclerLayout, parent, false);

        return new CateringOrdersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CateringOrdersViewHolder holder, int position) {

        //Break String
        String[] nameAndPrice = listOfCatering.get(position).split("_");
        holder.tv_CateringOption.setText(nameAndPrice[0]);
        final double itemPrice = Double.parseDouble(nameAndPrice[1]);
        String priceWithCurrency = new DecimalFormat("0.00").format(itemPrice) + " PLN";
        holder.tv_price.setText(priceWithCurrency);
        holder.tv_itemCounter.setText("0");

        holder.ib_addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemCounter = Integer.parseInt(holder.tv_itemCounter.getText().toString());
                itemCounter++;
                holder.tv_itemCounter.setText(String.valueOf(itemCounter));
                double itemsPrice = itemPrice * itemCounter;
                String itemsPriceFormatted = new DecimalFormat("0.00").format(itemsPrice) + " PLN";
                holder.tv_price.setText(itemsPriceFormatted);
            }
        });

        holder.ib_removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemCounter = Integer.parseInt(holder.tv_itemCounter.getText().toString());
                itemCounter--;
                if(itemCounter <= 0) {
                    itemCounter = 0;

                }
                holder.tv_itemCounter.setText(String.valueOf(itemCounter));
                double itemsPrice = itemPrice * itemCounter;
                if(itemCounter <= 0) {
                    itemsPrice = itemPrice;
                }
                String itemsPriceFormatted = new DecimalFormat("0.00").format(itemsPrice) + " PLN";
                holder.tv_price.setText(itemsPriceFormatted);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listOfCatering.size();
    }

    class CateringOrdersViewHolder extends RecyclerView.ViewHolder {

        TextView tv_CateringOption;
        TextView tv_price;
        TextView tv_itemCounter;
        ImageButton ib_addItem;
        ImageButton ib_removeItem;

        CateringOrdersViewHolder(View itemView) {
            super(itemView);

            tv_CateringOption = itemView.findViewById(R.id.catering_list_order_item_label);
            tv_price = itemView.findViewById(R.id.catering_list_order_item_price);
            tv_itemCounter = itemView.findViewById(R.id.catering_list_number_of_items);
            ib_addItem = itemView.findViewById(R.id.catering_list_add_btn);
            ib_removeItem = itemView.findViewById(R.id.catering_list_remove_btn);
        }

    }

}
