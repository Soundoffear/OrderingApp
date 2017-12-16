package com.ejoapps.m2d2_sub.orderingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.ejoapps.m2d2_sub.orderingapp.adapters.TypeAndNumberAdapter;
import com.ejoapps.m2d2_sub.orderingapp.interfaces.TypeAndNumberClickListener;
import com.ejoapps.m2d2_sub.orderingapp.storage.SandwichListStorage;

import java.util.ArrayList;

public class SandwichListActivity extends AppCompatActivity implements TypeAndNumberClickListener {

    private RecyclerView sub15RecView;
    ArrayList<String> tempKeys;
    ArrayList<Integer> tempQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sandwich_list);

        sub15RecView = findViewById(R.id.sl_recView_allCarriers);

        sub15RecView.setHasFixedSize(true);


        tempKeys = QuantityAndTypeActivity.CARRIES_CHECK;
        tempQuantity = QuantityAndTypeActivity.CARRIES_QUANTITY;

        if (SandwichListStorage.isSandwichBuilt) {
            Log.d("TEST BOOL", "Test bolka");
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
    }

    private void populateAllCarriersList() {
        int totalViewsItems = 0;
        for (int i = 0; i < tempKeys.size(); i++) {
            for (int q = 0; q < tempQuantity.get(i); q++) {
                totalViewsItems++;
                StringBuilder newString = new StringBuilder();
                newString.append(tempKeys.get(i)).append("_")
                        .append(totalViewsItems)
                        .append("_")
                        .append(R.string.tempPrice);
                SandwichListStorage.allCarriersTogether.add(newString.toString());
            }
        }
        SandwichListStorage.isSandwichBuilt = false;
    }

    private String[] breakDownString(String s) {
        String[] brokenDownString = s.split("_");
        return brokenDownString;
    }

    @Override
    public void onRecyclerViewClick(View v, int position) {
        Log.d("Position", String.valueOf(position));
        //String sandwitchToBeBuilded = SandwichListStorage.allCarriersTogether.get(position);
        SandwichListStorage.positionFromToReplace = position;
        Intent startBuilder = new Intent(SandwichListActivity.this, SandwichBuilderActivity.class);
        startActivity(startBuilder);
    }
}
