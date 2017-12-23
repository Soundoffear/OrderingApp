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

import com.ejoapps.m2d2_sub.orderingapp.adapters.CateringOrdersRecViewAdapter;
import com.ejoapps.m2d2_sub.orderingapp.containers.CateringNameAndTypeData;
import com.ejoapps.m2d2_sub.orderingapp.fragments.FirstPageFragment;

import java.util.ArrayList;
import java.util.List;

public class CateringOrderActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView cateringOrdersRecyclerView;

    List<String> cateringItems;

    Button nextButton;
    Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catering_order);

        cateringItems = new ArrayList<>();
        nextButton = findViewById(R.id.co_btn_next_catering);
        nextButton.setOnClickListener(this);
        cancelButton = findViewById(R.id.co_btn_cancel_catering);
        cancelButton.setOnClickListener(this);

        Intent cateringReceivedIntent = getIntent();
        if(cateringReceivedIntent != null) {
            cateringItems = cateringReceivedIntent.getExtras().getStringArrayList(FirstPageFragment.CATERING_TYPES);
        }

        cateringOrdersRecyclerView = findViewById(R.id.co_recyclerView);
        cateringOrdersRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        cateringOrdersRecyclerView.setLayoutManager(linearLayoutManager);
        CateringOrdersRecViewAdapter cateringOrdersRecViewAdapter = new CateringOrdersRecViewAdapter(this, cateringItems);
        cateringOrdersRecyclerView.setAdapter(cateringOrdersRecViewAdapter);

    }

    public static final String CATERING_DATA_TRANSFER = "catering-data-transfer";


    @Override
    public void onClick(View v) {

        List<CateringNameAndTypeData> allChosenCatering = new ArrayList<>();
        if (v.getId() == R.id.co_btn_next_catering) {
            int allItemsInRecView = cateringOrdersRecyclerView.getChildCount();
            for (int i = 0; i < allItemsInRecView; i++) {
                TextView tv_cateringType = cateringOrdersRecyclerView.getChildAt(i).findViewById(R.id.catering_list_order_item_label);
                String sCateringType = tv_cateringType.getText().toString();
                TextView tv_numberOfItems = cateringOrdersRecyclerView.getChildAt(i).findViewById(R.id.catering_list_number_of_items);
                String sNumberOfItems = tv_numberOfItems.getText().toString();
                TextView tv_cateringPrice = cateringOrdersRecyclerView.getChildAt(i).findViewById(R.id.catering_list_order_item_price);
                int iNumberOfTypes = Integer.parseInt(sNumberOfItems);
                Log.d("TEST TEST", "TEST ------------------ TEST");
                if(iNumberOfTypes > 0) {
                    // throws error because string to be parsed contains 'PLN'
                    String[] valueSplitted = tv_cateringPrice.getText().toString().split(" ");
                    double priceTotal = Double.parseDouble(valueSplitted[0]);
                    double noOfItems = (double) Integer.parseInt(tv_numberOfItems.getText().toString());
                    double pricePerOneItem = priceTotal / noOfItems;
                    Log.d("PRICE PER ITEM", String.valueOf(pricePerOneItem));
                    CateringNameAndTypeData cateringNameAndTypeData = new CateringNameAndTypeData(sCateringType, iNumberOfTypes, pricePerOneItem);
                    allChosenCatering.add(cateringNameAndTypeData);
                }
                Intent toCateringList = new Intent(CateringOrderActivity.this, CateringListToBuildActivity.class);
                Bundle cateringBundle = new Bundle();
                cateringBundle.putParcelableArrayList(CATERING_DATA_TRANSFER, (ArrayList) allChosenCatering);
                toCateringList.putExtras(cateringBundle);
                startActivity(toCateringList);
            }
        } else if (v.getId() == R.id.co_btn_cancel_catering) {
            Intent cancelIntent = new Intent(CateringOrderActivity.this, MainScreenNavDrawer.class);
            startActivity(cancelIntent);
        }
    }

}
