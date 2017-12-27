package com.ejoapps.m2d2_sub.orderingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ejoapps.m2d2_sub.orderingapp.adapters.CateringOrderSummaryAdapter;
import com.ejoapps.m2d2_sub.orderingapp.adapters.DrinksResultAdapter;
import com.ejoapps.m2d2_sub.orderingapp.adapters.OrderSummaryAdapter;
import com.ejoapps.m2d2_sub.orderingapp.containers.CateringNameAndTypeData;
import com.ejoapps.m2d2_sub.orderingapp.containers.FinalSandwichData;
import com.ejoapps.m2d2_sub.orderingapp.containers.OrderedDrinksData;
import com.ejoapps.m2d2_sub.orderingapp.database_preload.CateringBuilderDatabase;
import com.ejoapps.m2d2_sub.orderingapp.database_preload.OrderedDrinksDB;
import com.ejoapps.m2d2_sub.orderingapp.database_preload.SandwichFinalDatabase;
import com.ejoapps.m2d2_sub.orderingapp.storage.SandwichListStorage;

import java.text.DecimalFormat;
import java.util.List;

public class OrderSummaryActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView orderSummaryRecyclerView;

    TextView totalSumMoney;

    Button cancelOrder;
    Button placeAnOrder;

    List<FinalSandwichData> finalSandwichDataList;
    private double finalPriceAllSandwiches;

    public static final String TOTAL_ORDER_FINAL = "total-order-final";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        orderSummaryRecyclerView = findViewById(R.id.finalOrderRecyclerView);

        cancelOrder = findViewById(R.id.order_btn_cancel_order);
        placeAnOrder = findViewById(R.id.order_btn_placeAnOrder);

        placeAnOrder.setOnClickListener(this);
        cancelOrder.setOnClickListener(this);

        totalSumMoney = findViewById(R.id.orderTotalSumTotalMoney);

        if(!SandwichListStorage.isComingFromCatering) {
            sandwichV();
        } else {
            cateringV();
        }

    }

    void cateringV() {
        double totalPrice = 0;
        CateringBuilderDatabase cateringBuilderDatabase = new CateringBuilderDatabase(this);
        List<CateringNameAndTypeData> cateringNameAndTypeDataList = cateringBuilderDatabase.getAllCateringData();
        for(int i = 0; i < cateringNameAndTypeDataList.size(); i++) {
            String priceStringWithCurrency = cateringNameAndTypeDataList.get(i).getsCateringPrice();
            String[] splitPriceAndCurrency = priceStringWithCurrency.split(" ");
            String priceOnly = splitPriceAndCurrency[0];
            double tempPrice = Double.parseDouble(priceOnly);
            totalPrice += tempPrice;
        }
        CateringOrderSummaryAdapter cateringOrderSummaryAdapter = new CateringOrderSummaryAdapter(this, cateringNameAndTypeDataList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        orderSummaryRecyclerView.setHasFixedSize(true);
        orderSummaryRecyclerView.setLayoutManager(linearLayoutManager);
        orderSummaryRecyclerView.setAdapter(cateringOrderSummaryAdapter);

        if(CateringListToBuildActivity.isDrinkPicked) {
            OrderedDrinksDB orderedDrinksDB = new OrderedDrinksDB(this);
            List<OrderedDrinksData> orderedDrinksDataList = orderedDrinksDB.getAllOrderedDataDrinks();
            setDrinkRecView(orderedDrinksDataList);
            if (orderedDrinksDataList.size() > 0) {
                for (int i = 0; i < orderedDrinksDataList.size(); i++) {
                    String drinkPrice = orderedDrinksDataList.get(i).getDrinkPrice();
                    double dPrice = Double.parseDouble(drinkPrice);
                    totalPrice += dPrice;
                }
            }
        }

        finalPriceAllSandwiches = totalPrice;

        String sPriceFinal = new DecimalFormat("0.00").format(finalPriceAllSandwiches) + " " + getResources().getString(R.string.currency);
        totalSumMoney.setText(sPriceFinal);
    }

    void sandwichV() {
        double totalPrice = 0;
        SandwichFinalDatabase sandwichFinalDatabase = new SandwichFinalDatabase(this);
        finalSandwichDataList = sandwichFinalDatabase.getAllFinalSubData();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        orderSummaryRecyclerView.setHasFixedSize(true);
        orderSummaryRecyclerView.setLayoutManager(linearLayoutManager);
        OrderSummaryAdapter orderSummaryAdapter = new OrderSummaryAdapter(this, finalSandwichDataList);
        orderSummaryRecyclerView.setAdapter(orderSummaryAdapter);

        Intent intentFromSandwichList = getIntent();
        finalPriceAllSandwiches = intentFromSandwichList.getDoubleExtra(SandwichListActivity.FINAL_PRICE_CALCULATED, 0);

        if(SandwichListActivity.isDrinkSelected) {
            OrderedDrinksDB orderedDrinksDB = new OrderedDrinksDB(this);
            List<OrderedDrinksData> orderedDrinksDataList = orderedDrinksDB.getAllOrderedDataDrinks();
            setDrinkRecView(orderedDrinksDataList);
            if(orderedDrinksDataList.size() > 0) {
                for(int i = 0; i< orderedDrinksDataList.size(); i++) {
                    String drinkPrice = orderedDrinksDataList.get(i).getDrinkPrice();
                    double dPrice = Double.parseDouble(drinkPrice);
                    totalPrice += dPrice;
                }
            }
        }


        DecimalFormat format = new DecimalFormat("0.00");
        finalPriceAllSandwiches += totalPrice;
        String finalPriceWithCurrency = format.format(finalPriceAllSandwiches) + " " + getResources().getString(R.string.currency);
        totalSumMoney.setText(finalPriceWithCurrency);
    }

    private void setDrinkRecView(List<OrderedDrinksData> orderedDrinksDataList) {

        RecyclerView drinksRecyclerView = findViewById(R.id.finalOrderDrinksRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        drinksRecyclerView.setHasFixedSize(true);
        drinksRecyclerView.setLayoutManager(linearLayoutManager);
        DrinksResultAdapter drinksRecyclerViewAdapter = new DrinksResultAdapter(orderedDrinksDataList);
        drinksRecyclerView.setAdapter(drinksRecyclerViewAdapter);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.order_btn_placeAnOrder:
                Intent intentToFinalOrder = new Intent(OrderSummaryActivity.this, OrderAddressAndFinalConfirmationActivity.class);
                Bundle dataToCarryOver = new Bundle();
                dataToCarryOver.putDouble(TOTAL_ORDER_FINAL, finalPriceAllSandwiches);
                intentToFinalOrder.putExtras(dataToCarryOver);
                startActivity(intentToFinalOrder);
            case R.id.order_btn_cancel_order:

        }
    }
}
