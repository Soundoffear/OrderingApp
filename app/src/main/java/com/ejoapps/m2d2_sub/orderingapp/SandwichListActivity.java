package com.ejoapps.m2d2_sub.orderingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ejoapps.m2d2_sub.orderingapp.adapters.OrderSummaryAdapter;
import com.ejoapps.m2d2_sub.orderingapp.adapters.TypeAndNumberAdapter;
import com.ejoapps.m2d2_sub.orderingapp.containers.FinalSandwichData;
import com.ejoapps.m2d2_sub.orderingapp.database_preload.SandwichFinalDatabase;
import com.ejoapps.m2d2_sub.orderingapp.interfaces.TypeAndNumberClickListener;
import com.ejoapps.m2d2_sub.orderingapp.storage.SandwichListStorage;

import java.util.ArrayList;
import java.util.List;

public class SandwichListActivity extends AppCompatActivity implements TypeAndNumberClickListener {

    private RecyclerView sub15RecView;
    ArrayList<String> tempKeys;
    ArrayList<Integer> tempQuantity;
    public static final String FINAL_PRICE_CALCULATED = "final-price-all";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sandwich_list);

        sub15RecView = findViewById(R.id.sl_recView_allCarriers);

        sub15RecView.setHasFixedSize(true);

        Button buttonCheckOut = findViewById(R.id.sl_btn_checkout);

        tempKeys = QuantityAndTypeActivity.CARRIES_CHECK;
        tempQuantity = QuantityAndTypeActivity.CARRIES_QUANTITY;

        if (SandwichListStorage.isSandwichBuilt) {
            Log.d("TEST BOOL", "Test bolka");
            SandwichListStorage.allCarriersTogether.clear();
            populateAllCarriersList();
        }

        sub15RecView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        sub15RecView.setLayoutManager(linearLayoutManager);
        TypeAndNumberAdapter typeAndNumberAdapter = new TypeAndNumberAdapter(this, SandwichListStorage.allCarriersTogether, this);
        sub15RecView.setAdapter(typeAndNumberAdapter);

        for (int i = 0; i < SandwichListStorage.allCarriersTogether.size(); i++) {
            Log.d("Test temp Keys", SandwichListStorage.allCarriersTogether.get(i));
        }

        buttonCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SandwichFinalDatabase sandwichFinalDatabase = new SandwichFinalDatabase(getApplicationContext());
                List<FinalSandwichData> newFinalData = sandwichFinalDatabase.getAllFinalSubData();
                Log.d("TESTING BUTTON", "TESTING BUTTON");
                double finalPriceValue = 0;
                for(int i = 0; i < newFinalData.size(); i++) {
                    Log.d("Testing Final DB:", newFinalData.get(i).getSubPrice() + " " + newFinalData.get(i).getSubName());
                    String fPrice = newFinalData.get(i).getSubPrice();
                    String[] singlePrice = fPrice.split(" ");
                    double singlePriceDouble = Double.parseDouble(singlePrice[0]);
                    finalPriceValue += singlePriceDouble;
                }

                Intent intent = new Intent(SandwichListActivity.this, OrderSummaryActivity.class);
                intent.putExtra(FINAL_PRICE_CALCULATED, finalPriceValue);

                startActivity(intent);
            }
        });
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
        String[] brokenDownString = s.split("_");
        return brokenDownString;
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
}
