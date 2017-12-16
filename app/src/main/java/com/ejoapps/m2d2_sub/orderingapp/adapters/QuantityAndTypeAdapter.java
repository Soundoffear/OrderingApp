package com.ejoapps.m2d2_sub.orderingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ejoapps.m2d2_sub.orderingapp.R;
import com.ejoapps.m2d2_sub.orderingapp.storage.QuantityStorageClass;

import java.util.List;

/**
 * Created by soundoffear on 12/12/2017.
 */

public class QuantityAndTypeAdapter extends RecyclerView.Adapter<QuantityAndTypeAdapter.QuantityViewHolder> {

    private Context mContext;
    private List<String> mTypeNames;
    private boolean prevIsOrder;

    public QuantityAndTypeAdapter(Context context, List<String> _typeNames, boolean _prevIsOrder) {
        mContext = context;
        mTypeNames = _typeNames;
        prevIsOrder = _prevIsOrder;
    }

    @Override
    public QuantityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        int layoutQuantity = R.layout.quantity_recycler_list_items;
        View quantityView = layoutInflater.inflate(layoutQuantity, parent, false);

        return new QuantityViewHolder(quantityView);
    }

    private int quantityStart;

    @Override
    public void onBindViewHolder(QuantityViewHolder holder, int position) {

        final TextView quantityView = holder.tv_Quantity;
        // not yet finished, on back should load from QuantityStorageClass
        if(prevIsOrder) {
            quantityStart = 0;
        } else {
            quantityStart = QuantityStorageClass.storageQuantity;
        }

        quantityView.setText(String.valueOf(quantityStart));

        holder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sCurrentValue = quantityView.getText().toString();

                quantityStart  = Integer.parseInt(sCurrentValue);

                quantityStart++;
                quantityView.setText(String.valueOf(quantityStart));
            }
        });

        holder.btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sCurrentValue = quantityView.getText().toString();

                quantityStart  = Integer.parseInt(sCurrentValue);
                quantityStart--;
                if(quantityStart < 0) {
                    quantityStart = 0;
                }
                quantityView.setText(String.valueOf(quantityStart));
            }
        });

        holder.tv_itemName.setText(mTypeNames.get(position));

    }

    @Override
    public int getItemCount() {
        return mTypeNames.size();
    }

    class QuantityViewHolder extends RecyclerView.ViewHolder {

        TextView tv_itemName;
        TextView tv_Quantity;
        ImageView btn_add;
        ImageView btn_remove;

        public QuantityViewHolder(View itemView) {
            super(itemView);

            tv_itemName = itemView.findViewById(R.id.recyclerViewTypeName);
            tv_Quantity = itemView.findViewById(R.id.recyclerViewQuantity);

            btn_add = itemView.findViewById(R.id.recyclerViewMoreButton);
            btn_remove = itemView.findViewById(R.id.recyclerViewLessButton);

        }
    }

}
