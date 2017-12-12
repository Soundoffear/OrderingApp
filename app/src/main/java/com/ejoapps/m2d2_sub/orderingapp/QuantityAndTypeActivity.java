package com.ejoapps.m2d2_sub.orderingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ejoapps.m2d2_sub.orderingapp.adapters.QuantityAndTypeAdapter;

import java.util.ArrayList;
import java.util.List;

public class QuantityAndTypeActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerView;
    Button btn_next;
    Button btn_cancel;

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

        QuantityAndTypeAdapter quantityAndTypeAdapter = new QuantityAndTypeAdapter(this, carriersData, true);
        recyclerView.setAdapter(quantityAndTypeAdapter);

        btn_next = findViewById(R.id.quantityButtonNext);
        btn_next.setOnClickListener(this);

        btn_cancel = findViewById(R.id.quantityButtonCancelOrder);
        btn_cancel.setOnClickListener(this);

    }

    private ArrayList<String> tempKeyList = new ArrayList<>();
    public static final String TEMP_KEYS = "array-of-temp-keys";

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.quantityButtonNext:

                Intent intent = new Intent(QuantityAndTypeActivity.this, SandwichListActivity.class);

                for(int i = 0; i < recyclerView.getChildCount();i++) {
                    TextView recViewNameTV = recyclerView.getChildAt(i).findViewById(R.id.recyclerViewTypeName);
                    Log.d("Recycler Text View Name", recViewNameTV.getText().toString());
                    tempKeyList.add(recViewNameTV.getText().toString());
                    TextView recyclerViewQuantity = recyclerView.getChildAt(i).findViewById(R.id.recyclerViewQuantity);
                    Log.d("Recycler Text View Qua", recyclerViewQuantity.getText().toString());
                    int sandwichValue = Integer.parseInt(recyclerViewQuantity.getText().toString());

                    intent.putExtra(recViewNameTV.getText().toString(), sandwichValue);
                }

                intent.putStringArrayListExtra(TEMP_KEYS, tempKeyList);

                startActivity(intent);

                break;
            case R.id.quantityButtonCancelOrder:

                break;
            default:
                return;
        }

    }
}
