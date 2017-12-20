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
import com.ejoapps.m2d2_sub.orderingapp.database_preload.SandwichFinalDatabase;
import com.ejoapps.m2d2_sub.orderingapp.storage.SandwichListStorage;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        Bundle receivedData = intent.getExtras();
        List<String> carriersData = receivedData.getStringArrayList(FirstPageFragment.LIST_OF_TYPES);

        QuantityAndTypeAdapter quantityAndTypeAdapter = new QuantityAndTypeAdapter(this, carriersData, true);
        recyclerView.setAdapter(quantityAndTypeAdapter);

        btn_next = findViewById(R.id.quantityButtonNext);
        btn_next.setOnClickListener(this);

        btn_cancel = findViewById(R.id.quantityButtonCancelOrder);
        btn_cancel.setOnClickListener(this);

    }

    public static final String TEMP_KEYS = "array-of-temp-keys";
    public static final String ARRAY_KEY_BUNDLE = "array-bundle";
    public static final ArrayList<String> CARRIES_CHECK = new ArrayList<>();

    public static final ArrayList<Integer> CARRIES_QUANTITY = new ArrayList<>();
    private Bundle arrayKeysBundle = new Bundle();

    @Override
    public void onClick(View v) {

        SandwichFinalDatabase db = new SandwichFinalDatabase(this);
        db.deleteAllRecords();

        SandwichListStorage.isSandwichBuilt = true;
        CARRIES_CHECK.clear();
        CARRIES_QUANTITY.clear();

        int id = v.getId();
        switch (id) {
            case R.id.quantityButtonNext:

                Intent intent = new Intent(QuantityAndTypeActivity.this, SandwichListActivity.class);

                for(int i = 0; i < recyclerView.getChildCount();i++) {
                    TextView recViewNameTV = recyclerView.getChildAt(i).findViewById(R.id.recyclerViewTypeName);
                    Log.d("Recycler Text View Name", recViewNameTV.getText().toString());
                    CARRIES_CHECK.add(recViewNameTV.getText().toString());
                    TextView recyclerViewQuantity = recyclerView.getChildAt(i).findViewById(R.id.recyclerViewQuantity);
                    Log.d("Recycler Text View Qua", recyclerViewQuantity.getText().toString());
                    int sandwichValue = Integer.parseInt(recyclerViewQuantity.getText().toString());
                    CARRIES_QUANTITY.add(sandwichValue);
                    Bundle dataToQuantityAndTypeActivity = new Bundle();
                    dataToQuantityAndTypeActivity.putInt(CARRIES_CHECK.get(i), sandwichValue);
                    intent.putExtras(dataToQuantityAndTypeActivity);
                }

                arrayKeysBundle.putStringArrayList(ARRAY_KEY_BUNDLE, CARRIES_CHECK);
                intent.putStringArrayListExtra(TEMP_KEYS, CARRIES_CHECK);
                intent.putExtras(arrayKeysBundle);
                startActivity(intent);

                break;
            case R.id.quantityButtonCancelOrder:

                break;
            default:
        }

    }
}
