package com.ejoapps.m2d2_sub.orderingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ejoapps.m2d2_sub.orderingapp.adapters.DrinksRecyclerViewAdapter;
import com.ejoapps.m2d2_sub.orderingapp.containers.DrinksData;
import com.ejoapps.m2d2_sub.orderingapp.containers.OrderedDrinksData;
import com.ejoapps.m2d2_sub.orderingapp.database_preload.OrderedDrinksDB;
import com.ejoapps.m2d2_sub.orderingapp.interfaces.OnGetDataListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DrinksPickerActivity extends AppCompatActivity implements View.OnClickListener {

    DatabaseReference databaseReference;

    private List<DrinksData> hotDrinksDataList;
    private List<DrinksData> softDrinkDataList;
    private List<DrinksData> waterDrinksDataList;

    RecyclerView hotDrinkRecView;
    RecyclerView softDrinkRecView;
    RecyclerView waterRecView;

    private static TextView totalPriceDrinks;
    Button btn_cancel;
    Button btn_add_drinks;

    OrderedDrinksDB orderedDrinksDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drinks_picker);

        hotDrinkRecView = findViewById(R.id.drinks_hot_drinks_recView);
        softDrinkRecView = findViewById(R.id.drinks_soft_drinks_recView);
        waterRecView = findViewById(R.id.drinks_water_recView);
        totalPriceDrinks = findViewById(R.id.drinks_price_value);
        btn_cancel = findViewById(R.id.drinks_cancel);
        btn_add_drinks = findViewById(R.id.drinks_add_to_order);
        btn_add_drinks.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

        orderedDrinksDB = new OrderedDrinksDB(this);
        orderedDrinksDB.deleteAllDrinksData();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("drinks");
        loadDrinksDatabase(databaseReference, new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {

                hotDrinksDataList = new ArrayList<>();
                Map<String, Object> hotDrinksMap = (Map<String, Object>) dataSnapshot.child("coffee").getValue();

                for (Map.Entry<String, Object> drinksMap : hotDrinksMap.entrySet()) {
                    String drinksValue = String.valueOf(drinksMap.getValue());
                    DrinksData drinksData = new DrinksData(drinksMap.getKey(), drinksValue);
                    hotDrinksDataList.add(drinksData);
                }

                hotDrinkRecView.setHasFixedSize(true);
                LinearLayoutManager hotLinearManager = new LinearLayoutManager(getApplicationContext());
                hotDrinkRecView.setLayoutManager(hotLinearManager);
                DrinksRecyclerViewAdapter drinksRecyclerViewAdapter = new DrinksRecyclerViewAdapter(hotDrinksDataList);
                hotDrinkRecView.setAdapter(drinksRecyclerViewAdapter);


                softDrinkDataList = new ArrayList<>();
                Map<String, Object> bottledDrinks = (Map<String, Object>) dataSnapshot.child("softdrinks").child("bottle").getValue();
                for (Map.Entry<String, Object> drinksMap : bottledDrinks.entrySet()) {
                    String typeAndName = "Bottle: " + drinksMap.getKey();
                    DrinksData drinksData = new DrinksData(typeAndName, String.valueOf(drinksMap.getValue()));
                    softDrinkDataList.add(drinksData);
                }

                Map<String, Object> cannedDrinks = (Map<String, Object>) dataSnapshot.child("softdrinks").child("cans").getValue();
                for (Map.Entry<String, Object> cannedDrinksMap : cannedDrinks.entrySet()) {
                    String typeAndName = "Can: " + cannedDrinksMap.getKey();
                    DrinksData drinksData = new DrinksData(typeAndName, String.valueOf(cannedDrinksMap.getValue()));
                    softDrinkDataList.add(drinksData);
                }

                softDrinkRecView.setHasFixedSize(true);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                softDrinkRecView.setLayoutManager(linearLayoutManager);
                DrinksRecyclerViewAdapter softDrinksAdapter = new DrinksRecyclerViewAdapter(softDrinkDataList);
                softDrinkRecView.setAdapter(softDrinksAdapter);

                waterDrinksDataList = new ArrayList<>();
                Map<String, Object> waterDrinks = (Map<String, Object>) dataSnapshot.child("water").getValue();
                for (Map.Entry<String, Object> water : waterDrinks.entrySet()) {
                    DrinksData drinksData = new DrinksData(water.getKey(), String.valueOf(water.getValue()));
                    waterDrinksDataList.add(drinksData);
                }

                waterRecView.setHasFixedSize(true);
                LinearLayoutManager waterLL = new LinearLayoutManager(getApplicationContext());
                waterRecView.setLayoutManager(waterLL);
                DrinksRecyclerViewAdapter waterAdapter = new DrinksRecyclerViewAdapter(waterDrinksDataList);
                waterRecView.setAdapter(waterAdapter);

            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure() {

            }
        });

    }


    private static double tempPrice = 0;

    public static void setTotalPriceFinal(double currentPrice) {

        tempPrice = tempPrice + currentPrice;

        totalPriceDrinks.setText(new DecimalFormat("0.00").format(tempPrice));

    }

    private void loadDrinksDatabase(DatabaseReference databaseReference, final OnGetDataListener onGetDataListener) {
        onGetDataListener.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                onGetDataListener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                onGetDataListener.onFailure();
            }
        });

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.drinks_add_to_order:

                checkSelectedDrinks(hotDrinkRecView);
                checkSelectedDrinks(softDrinkRecView);
                checkSelectedDrinks(waterRecView);

                if(CateringListToBuildActivity.isCatering) {
                    CateringListToBuildActivity.isDrinkPicked = true;

                    Intent intentToGoToCateringList = new Intent(DrinksPickerActivity.this, CateringListToBuildActivity.class);
                    startActivity(intentToGoToCateringList);
                } else {
                    SandwichListActivity.isDrinkSelected = true;

                    Intent intentToGoToCateringList = new Intent(DrinksPickerActivity.this, SandwichListActivity.class);
                    startActivity(intentToGoToCateringList);
                }

                break;
            case R.id.drinks_cancel:

                break;
        }

    }

    private void checkSelectedDrinks(RecyclerView recyclerView) {
        for (int hot = 0; hot < recyclerView.getChildCount(); hot++) {
            TextView tv_counter = recyclerView.getChildAt(hot).findViewById(R.id.drinks_item_drink_counter);
            String sCounter = tv_counter.getText().toString();
            int drinkCounter = Integer.parseInt(sCounter);
            if (drinkCounter > 0) {
                TextView tv_dName = recyclerView.getChildAt(hot).findViewById(R.id.drinks_item_name_check_box);
                TextView tv_priceSum = recyclerView.getChildAt(hot).findViewById(R.id.drinks_item_price_value);
                String sDName = tv_dName.getText().toString();
                String sPriceSum = tv_priceSum.getText().toString();

                OrderedDrinksData orderedDrinksData = new OrderedDrinksData(sDName, sCounter, sPriceSum);
                orderedDrinksDB.insertDataToOrderedDrinksDB(orderedDrinksData);
            }
        }
    }
}
