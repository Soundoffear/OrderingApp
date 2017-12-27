package com.ejoapps.m2d2_sub.orderingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ejoapps.m2d2_sub.orderingapp.adapters.DrinksResultAdapter;
import com.ejoapps.m2d2_sub.orderingapp.adapters.SandwichListDetailAdapter;
import com.ejoapps.m2d2_sub.orderingapp.containers.FinalSandwichData;
import com.ejoapps.m2d2_sub.orderingapp.containers.OrderedDrinksData;
import com.ejoapps.m2d2_sub.orderingapp.database_preload.OrderedDrinksDB;
import com.ejoapps.m2d2_sub.orderingapp.database_preload.SandwichFinalDatabase;
import com.ejoapps.m2d2_sub.orderingapp.interfaces.TypeAndNumberClickListener;
import com.ejoapps.m2d2_sub.orderingapp.storage.SandwichListStorage;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SandwichListActivity extends AppCompatActivity implements TypeAndNumberClickListener, View.OnClickListener {

    ArrayList<String> tempKeys;
    ArrayList<Integer> tempQuantity;
    public static final String FINAL_PRICE_CALCULATED = "final-price-all";
    private TextView totalSandwichPrice;

    public static boolean isDrinkSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sandwich_list);

        Button buttonCheckOut = findViewById(R.id.sl_btn_checkout);
        buttonCheckOut.setOnClickListener(this);

        totalSandwichPrice = findViewById(R.id.sl_total_price_value);
        RecyclerView sandwichesRecView = findViewById(R.id.sl_recView_allCarriers);
        FloatingActionButton fab_drinks = findViewById(R.id.sl_fab_drinks);
        fab_drinks.setOnClickListener(this);

        tempKeys = SandwichQuantityAndTypeActivity.CARRIES_CHECK;
        tempQuantity = SandwichQuantityAndTypeActivity.CARRIES_QUANTITY;

        if (SandwichListStorage.isSandwichBuilt) {
            SandwichListStorage.allCarriersTogether.clear();
            populateAllCarriersList();
        }

        sandwichesRecView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        sandwichesRecView.setLayoutManager(linearLayoutManager);
        SandwichListDetailAdapter sandwichListDetailAdapter = new SandwichListDetailAdapter(this, SandwichListStorage.allCarriersTogether, this);
        sandwichesRecView.setAdapter(sandwichListDetailAdapter);

        if (isDrinkSelected) {
            includeDrinks();
        }

        sumAllSandwiches();
    }

    double tempPrice = 0;

    void includeDrinks() {

        OrderedDrinksDB orderedDrinksDB = new OrderedDrinksDB(this);
        List<OrderedDrinksData> orderedDrinksDataList = orderedDrinksDB.getAllOrderedDataDrinks();
        for (int i = 0; i < orderedDrinksDataList.size(); i++) {
            String drinkPriceSum = orderedDrinksDataList.get(i).getDrinkPrice();
            double drinkPrice = Double.parseDouble(drinkPriceSum);
            tempPrice += drinkPrice;
        }
        RecyclerView drinksRecView = findViewById(R.id.sl_recView_drinks);
        drinksRecView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        drinksRecView.setLayoutManager(linearLayoutManager);
        DrinksResultAdapter drinksResultAdapter = new DrinksResultAdapter(orderedDrinksDataList);
        drinksRecView.setAdapter(drinksResultAdapter);

    }

    private void sumAllSandwiches() {
        SandwichFinalDatabase sandwichFinalDatabase = new SandwichFinalDatabase(this);
        List<FinalSandwichData> finalSandwichDataList = sandwichFinalDatabase.getAllFinalSubData();

        for (int i = 0; i < finalSandwichDataList.size(); i++) {
            String[] sandwichPrice = finalSandwichDataList.get(i).getSubPrice().split(" ");
            double dPrice = Double.parseDouble(sandwichPrice[0]);
            tempPrice += dPrice;
        }

        totalSandwichPrice.setText(new DecimalFormat("0.00").format(tempPrice));

    }

    StringBuilder newString;

    private void populateAllCarriersList() {
        int totalViewsItems = 0;
        for (int i = 0; i < tempKeys.size(); i++) {
            for (int q = 0; q < tempQuantity.get(i); q++) {
                totalViewsItems++;
                newString = new StringBuilder();
                newString.append(tempKeys.get(i)).append("_")
                        .append(totalViewsItems)
                        .append("_")
                        .append(getString(R.string.tempPrice));
                SandwichListStorage.allCarriersTogether.add(newString.toString());
            }
        }
        SandwichListStorage.isSandwichBuilt = false;
    }

    private String[] breakDownString(String s) {
        return s.split("_");
    }

    public static final String CARRIER_TYPE_TO_BUILDER = "carrier-type-to-builder";

    @Override
    public void onRecyclerViewClick(View v, int position) {
        Log.d("Position", String.valueOf(position));
        SandwichListStorage.positionFromToReplace = position;
        Bundle dataToBuilder = new Bundle();
        String carrierType = breakDownString(SandwichListStorage.allCarriersTogether.get(position))[0];
        dataToBuilder.putString(CARRIER_TYPE_TO_BUILDER, carrierType);
        Intent startBuilder = new Intent(SandwichListActivity.this, SandwichBuilderActivity.class);
        startBuilder.putExtras(dataToBuilder);
        startActivity(startBuilder);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.sl_btn_checkout:
                SandwichFinalDatabase sandwichFinalDatabase = new SandwichFinalDatabase(getApplicationContext());
                List<FinalSandwichData> newFinalData = sandwichFinalDatabase.getAllFinalSubData();
                double finalPriceValue = 0;
                for (int i = 0; i < newFinalData.size(); i++) {
                    String fPrice = newFinalData.get(i).getSubPrice();
                    String[] singlePrice = fPrice.split(" ");
                    double singlePriceDouble = Double.parseDouble(singlePrice[0]);
                    finalPriceValue += singlePriceDouble;
                }

                Intent intent = new Intent(SandwichListActivity.this, OrderSummaryActivity.class);
                intent.putExtra(FINAL_PRICE_CALCULATED, finalPriceValue);

                startActivity(intent);
                break;
            case R.id.sl_fab_drinks:

                Intent intentToPickDrinks = new Intent(SandwichListActivity.this, DrinksPickerActivity.class);
                startActivity(intentToPickDrinks);

                break;
            case R.id.sl_btn_cancelOrder:
                break;
        }

    }
}
