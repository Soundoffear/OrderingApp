package com.ejoapps.m2d2_sub.orderingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ejoapps.m2d2_sub.orderingapp.R;


public class CouponsRecViewAdapter extends RecyclerView.Adapter<CouponsRecViewAdapter.CouponsViewHolder> {

    private Context recContext;
    private String[] coupons;

    public CouponsRecViewAdapter(Context recContext, String[] coupons) {
        this.recContext = recContext;
        this.coupons = coupons;
    }

    @Override
    public CouponsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int couponLayout = R.layout.coupons_list;
        LayoutInflater couponsViewInflater = LayoutInflater.from(context);
        View couponView = couponsViewInflater.inflate(couponLayout, parent, false);

        return new CouponsViewHolder(couponView);
    }

    @Override
    public void onBindViewHolder(CouponsViewHolder holder, int position) {

        holder.tv_couponDescription.setText(coupons[position]);

    }

    @Override
    public int getItemCount() {
        return coupons.length;
    }

    class CouponsViewHolder extends RecyclerView.ViewHolder {

        TextView tv_couponDescription;

        CouponsViewHolder(View itemView) {
            super(itemView);

            tv_couponDescription = itemView.findViewById(R.id.tv_coupon);
        }
    }

}
