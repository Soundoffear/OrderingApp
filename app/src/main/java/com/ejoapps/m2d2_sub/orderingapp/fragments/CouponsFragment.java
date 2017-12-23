package com.ejoapps.m2d2_sub.orderingapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ejoapps.m2d2_sub.orderingapp.R;
import com.ejoapps.m2d2_sub.orderingapp.adapters.CouponsRecViewAdapter;

public class CouponsFragment extends Fragment {

    RecyclerView couponsRecyclerView;

    String[] tempCoupons = {"Coupon 1", "Coupon 2","Coupon 3","Coupon 4","Coupon 5","Coupon 6","Coupon 7","Coupon 8"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.coupons_fragment, container, false);

        couponsRecyclerView = rootView.findViewById(R.id.coupons_recyclerView);
        couponsRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        couponsRecyclerView.setLayoutManager(linearLayoutManager);
        CouponsRecViewAdapter couponsRecViewAdapter = new CouponsRecViewAdapter(getContext(), tempCoupons);
        couponsRecyclerView.setAdapter(couponsRecViewAdapter);


        return rootView;

    }
}
