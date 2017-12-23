package com.ejoapps.m2d2_sub.orderingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ejoapps.m2d2_sub.orderingapp.adapters.CateringListToBuildAdapter;
import com.ejoapps.m2d2_sub.orderingapp.containers.CateringNameAndTypeData;
import com.ejoapps.m2d2_sub.orderingapp.interfaces.CateringItemOnClickListener;
import com.ejoapps.m2d2_sub.orderingapp.storage.SandwichListStorage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soundoffear on 22/12/2017.
 */

public class CateringListToBuildActivity extends AppCompatActivity implements CateringItemOnClickListener {

    RecyclerView cateringBuilderRecView;
    List<CateringNameAndTypeData> cateringNames;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catering_builder);

        cateringNames = new ArrayList<>();

        if(SandwichListStorage.isSandwichBuilt) {
            Intent intent = getIntent();
            Bundle receivedData = intent.getExtras();
            List<CateringNameAndTypeData> cateringNameAndTypeDataList = receivedData.getParcelableArrayList(CateringOrderActivity.CATERING_DATA_TRANSFER);
            for(int i = 0; i < cateringNameAndTypeDataList.size();i++) {
                for(int j =0; j < cateringNameAndTypeDataList.get(i).getCateringValue(); j++) {
                    String cateringItem = cateringNameAndTypeDataList.get(i).getCateringName();
                    double cateringPrice = cateringNameAndTypeDataList.get(i).getCateringPrice();
                    CateringNameAndTypeData cateringNameAndTypeData = new CateringNameAndTypeData(cateringItem, 0, cateringPrice);
                    cateringNames.add(cateringNameAndTypeData);
                }
            }
            Log.d("TEST", "PRICE TEST +++++++++ " + cateringNames.get(0).getCateringPrice());
            SandwichListStorage.isSandwichBuilt = false;
        }

        cateringBuilderRecView = findViewById(R.id.cb_recView);

        cateringBuilderRecView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        cateringBuilderRecView.setLayoutManager(linearLayoutManager);
        CateringListToBuildAdapter cateringListToBuildAdapter = new CateringListToBuildAdapter(this, cateringNames, this);
        cateringBuilderRecView.setAdapter(cateringListToBuildAdapter);

    }

    @Override
    public void onCateringItemClick(View v, int position) {
        TextView tv_name = v.findViewById(R.id.catering_list_item_label);
        String sName = tv_name.getText().toString();

        Toast.makeText(this, sName, Toast.LENGTH_SHORT).show();
    }
}
