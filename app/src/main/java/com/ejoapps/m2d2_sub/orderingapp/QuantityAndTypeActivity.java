package com.ejoapps.m2d2_sub.orderingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.ejoapps.m2d2_sub.orderingapp.adapters.QuantityAndTypeAdapter;

import java.util.List;

public class QuantityAndTypeActivity extends AppCompatActivity {


    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quantity_and_type);

        recyclerView = findViewById(R.id.quantityRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        Intent intent = getIntent();
        List<String> carriersData = intent.getStringArrayListExtra("DATA_LIST");

        Log.d("DataStrings", String.valueOf(carriersData.size()));

        QuantityAndTypeAdapter quantityAndTypeAdapter = new QuantityAndTypeAdapter(this, carriersData, true);
        recyclerView.setAdapter(quantityAndTypeAdapter);

    }


}
