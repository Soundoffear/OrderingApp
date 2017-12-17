package com.ejoapps.m2d2_sub.orderingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import com.ejoapps.m2d2_sub.orderingapp.adapters.OrderSummaryAdapter;
import com.ejoapps.m2d2_sub.orderingapp.containers.FinalSandwichData;
import com.ejoapps.m2d2_sub.orderingapp.database_preload.SandwichFinalDatabase;

import java.util.List;

public class OrderSummaryActivity extends AppCompatActivity {

    RecyclerView orderSummaryRecyclerView;

    TextView totalSumMoney;

    Button cancelOrder;
    Button placeAnOrder;

    List<FinalSandwichData> finalSandwichDataList;

    // TODO Refine looks and feel of OrderSummaryClass
    // need to add total price functionality, get sandwich description
    // TODO need to create connection to Firebase to place order

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        orderSummaryRecyclerView = findViewById(R.id.finalOrderRecyclerView);

        cancelOrder = findViewById(R.id.order_btn_cancel_order);
        placeAnOrder = findViewById(R.id.order_btn_placeAnOrder);

        totalSumMoney = findViewById(R.id.orderTotalSumTotalMoney);

        SandwichFinalDatabase sandwichFinalDatabase = new SandwichFinalDatabase(this);
        finalSandwichDataList = sandwichFinalDatabase.getAllFinalSubData();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        orderSummaryRecyclerView.setHasFixedSize(true);
        orderSummaryRecyclerView.setLayoutManager(linearLayoutManager);
        OrderSummaryAdapter orderSummaryAdapter = new OrderSummaryAdapter(this, finalSandwichDataList);
        orderSummaryRecyclerView.setAdapter(orderSummaryAdapter);

    }
}
