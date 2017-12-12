package com.ejoapps.m2d2_sub.orderingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.ejoapps.m2d2_sub.orderingapp.adapters.TypeAndNumberAdapter;
import com.ejoapps.m2d2_sub.orderingapp.storage.TypeAndNumberOfCarriersStorage;

import java.util.ArrayList;
import java.util.List;

public class SandwichListActivity extends AppCompatActivity {

    private RecyclerView sub15RecView;
    private RecyclerView sub30RecView;
    private RecyclerView wrapRecView;
    private RecyclerView saladRecView;

    private int quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sandwich_list);

        sub15RecView = findViewById(R.id.sl_recView_sub15);
        sub30RecView = findViewById(R.id.sl_recView_sub30);
        wrapRecView = findViewById(R.id.sl_recView_wrap);
        saladRecView = findViewById(R.id.sl_recView_salad);

        TypeAndNumberAdapter typeAndNumberAdapter;

        Intent intent = getIntent();

        ArrayList<String> tempKeys = intent.getStringArrayListExtra(QuantityAndTypeActivity.TEMP_KEYS);

        for(int i = 0; i < tempKeys.size(); i++) {

            String key = tempKeys.get(i);
            quantity = intent.getIntExtra(key, 0);
            Log.d("Check Intent Values", key + " " + String.valueOf(quantity));

        }

        sub15RecView.setHasFixedSize(true);
        sub30RecView.setHasFixedSize(true);
        wrapRecView.setHasFixedSize(true);
        saladRecView.setHasFixedSize(true);

        LinearLayoutManager sub15linearLayoutManager = new LinearLayoutManager(this);
        LinearLayoutManager sub30linearLayoutManager = new LinearLayoutManager(this);
        LinearLayoutManager wraplinearLayoutManager = new LinearLayoutManager(this);
        LinearLayoutManager saladlinearLayoutManager = new LinearLayoutManager(this);


        sub15RecView.setLayoutManager(sub15linearLayoutManager);
        sub30RecView.setLayoutManager(sub30linearLayoutManager);
        wrapRecView.setLayoutManager(wraplinearLayoutManager);
        saladRecView.setLayoutManager(saladlinearLayoutManager);

        for(int i = 0; i < tempKeys.size(); i++) {
            if(tempKeys.get(i).equals("sub15")) {
                typeAndNumberAdapter = new TypeAndNumberAdapter(this, intent.getIntExtra(tempKeys.get(i), 0), tempKeys.get(i));
                sub15RecView.setAdapter(typeAndNumberAdapter);
            } else if (tempKeys.get(i).equals("sub30")) {
                typeAndNumberAdapter = new TypeAndNumberAdapter(this, intent.getIntExtra(tempKeys.get(i), 0), tempKeys.get(i));
                sub30RecView.setAdapter(typeAndNumberAdapter);
            } else if (tempKeys.get(i).equals("wrap")) {
                typeAndNumberAdapter = new TypeAndNumberAdapter(this, intent.getIntExtra(tempKeys.get(i), 0), tempKeys.get(i));
                wrapRecView.setAdapter(typeAndNumberAdapter);
            } else if (tempKeys.get(i).equals("salad")) {
                typeAndNumberAdapter = new TypeAndNumberAdapter(this, intent.getIntExtra(tempKeys.get(i), 0), tempKeys.get(i));
                saladRecView.setAdapter(typeAndNumberAdapter);
            }
        }

    }
}
