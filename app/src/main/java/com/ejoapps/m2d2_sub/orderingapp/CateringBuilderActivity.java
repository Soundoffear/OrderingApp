package com.ejoapps.m2d2_sub.orderingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ejoapps.m2d2_sub.orderingapp.adapters.CateringBuilderAdapter;
import com.ejoapps.m2d2_sub.orderingapp.containers.CateringNameAndTypeData;
import com.ejoapps.m2d2_sub.orderingapp.containers.SandwichNameData;
import com.ejoapps.m2d2_sub.orderingapp.database_preload.ContractSandwichBuilder;
import com.ejoapps.m2d2_sub.orderingapp.database_preload.SandwichBuilderDatabase;

import java.util.ArrayList;
import java.util.List;

public class CateringBuilderActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView cateringBuilderRecView;
    Button confirmButton;
    Button cancelButton;
    List<CateringNameAndTypeData> cateringNameAndTypeDataList;
    int positionInList;
    double priceCatering;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catering_builder2);

        confirmButton = findViewById(R.id.catering_builder_btn_confirm);
        cancelButton = findViewById(R.id.catering_builder_btn_cancel);
        confirmButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

        Intent intentFromBuildList = getIntent();
        Bundle receivedData = intentFromBuildList.getExtras();
        assert receivedData != null;
        cateringNameAndTypeDataList = receivedData.getParcelableArrayList(CateringListToBuildActivity.LIST_OF_CATERING_ITEMS);
        positionInList = receivedData.getInt(CateringListToBuildActivity.POSITION_OF_ITEM_CATERING);
        priceCatering = cateringNameAndTypeDataList.get(positionInList).getCateringPrice();

        cateringBuilderRecView = findViewById(R.id.catering_builder_recView);

        cateringBuilderRecView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        cateringBuilderRecView.setLayoutManager(linearLayoutManager);

        SandwichBuilderDatabase sandwichBuilderDatabase = new SandwichBuilderDatabase(this);
        List<SandwichNameData> sandwichNameDataList = sandwichBuilderDatabase.getSandwichNameAllData(ContractSandwichBuilder.SandwichNames.TABLE_NAME_SANDWICH_NAMES);
        CateringBuilderAdapter cateringBuilderAdapter = new CateringBuilderAdapter(this, sandwichNameDataList);
        cateringBuilderRecView.setAdapter(cateringBuilderAdapter);

    }

    public static final String DATA_BACK_TO_LIST = "data-sent-back-to-list";

    @Override
    public void onClick(View v) {
        int buttonId = v.getId();

        switch (buttonId) {
            case R.id.catering_builder_btn_confirm:
                StringBuilder stringBuilder = new StringBuilder();
                for(int i = 0; i < cateringBuilderRecView.getChildCount(); i++) {
                    CheckBox singleCheckBox = cateringBuilderRecView.getChildAt(i).findViewById(R.id.cb_check_box_sandwich);
                    if(singleCheckBox.isChecked()) {
                        TextView selectedSandwichName = cateringBuilderRecView.getChildAt(i).findViewById(R.id.cb_sandwich_item_name);
                        String sSandwichName = selectedSandwichName.getText().toString();
                        stringBuilder.append(sSandwichName).append(", ");
                    }
                }

                cateringNameAndTypeDataList.remove(positionInList);
                CateringNameAndTypeData cateringNameAndTypeData = new CateringNameAndTypeData(stringBuilder.toString(), 0, priceCatering);
                cateringNameAndTypeDataList.add(positionInList, cateringNameAndTypeData);

                Intent intentBackToList = new Intent(CateringBuilderActivity.this, CateringListToBuildActivity.class);
                Bundle dataBackToList = new Bundle();
                dataBackToList.putParcelableArrayList(DATA_BACK_TO_LIST, (ArrayList) cateringNameAndTypeDataList);
                intentBackToList.putExtras(dataBackToList);
                startActivity(intentBackToList);

                break;
            case R.id.catering_builder_btn_cancel:

                break;
        }
    }
}
