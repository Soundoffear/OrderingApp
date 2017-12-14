package com.ejoapps.m2d2_sub.orderingapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ejoapps.m2d2_sub.orderingapp.adapters.PaidAddOnsAdapter;
import com.ejoapps.m2d2_sub.orderingapp.adapters.SandwichNamesAndDescAdapter;
import com.ejoapps.m2d2_sub.orderingapp.containers.BreadTypesData;
import com.ejoapps.m2d2_sub.orderingapp.containers.PaidAddOnsData;
import com.ejoapps.m2d2_sub.orderingapp.containers.SandwichNameData;
import com.ejoapps.m2d2_sub.orderingapp.containers.SauceData;
import com.ejoapps.m2d2_sub.orderingapp.containers.VegetablesData;
import com.ejoapps.m2d2_sub.orderingapp.database_preload.ContractSandwichBuilder;
import com.ejoapps.m2d2_sub.orderingapp.database_preload.SandwichBuilderDatabase;
import com.ejoapps.m2d2_sub.orderingapp.interfaces.OnGetDataListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SandwichBuilderActivity extends AppCompatActivity {

    private List<BreadTypesData> breadTypesList;
    private List<SandwichNameData> sandwichNameList;
    private List<PaidAddOnsData> paidAddOnList;
    private List<VegetablesData> vegeList;
    private List<SauceData> sauceList;

    RecyclerView sandwichNDP;
    RecyclerView paidAddsRecView;

    LinearLayout ll_vegetablesHolder;
    LinearLayout ll_saucesHolder;

    RadioGroup rg_bread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sandwich_builder);

        rg_bread = findViewById(R.id.sub_build_radio_button);

        SandwichBuilderDatabase sandwichBuilderDatabase = new SandwichBuilderDatabase(this);
        sandwichNameList = sandwichBuilderDatabase.getSandwichNameAllData(ContractSandwichBuilder.SandwichNames.TABLE_NAME_SANDWICH_NAMES);

        sandwichNDP = findViewById(R.id.sub_build_sub_names_recView);
        sandwichNDP.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SandwichBuilderActivity.this);
        sandwichNDP.setLayoutManager(linearLayoutManager);
        SandwichNamesAndDescAdapter sandwichNamesAndDescAdapter = new SandwichNamesAndDescAdapter(SandwichBuilderActivity.this, sandwichNameList);
        sandwichNDP.setAdapter(sandwichNamesAndDescAdapter);

        breadTypesList = sandwichBuilderDatabase.getBreadTypeAllData();
        // Create RadioButton and populate RadioGroup with it
        RadioButton[] radioButton = new RadioButton[breadTypesList.size()];
        for(int i = 0; i < breadTypesList.size();i++) {
            radioButton[i] = new RadioButton(this);
            radioButton[i].setText(breadTypesList.get(i).getBreadType());
            radioButton[i].setTextSize(16f);
            radioButton[i].setTextColor(Color.BLACK);
            rg_bread.addView(radioButton[i]);
        }

        // Create Paid Add Ons and populate recyclerView
        paidAddOnList = sandwichBuilderDatabase.getPaidAddOnsAllData();
        paidAddsRecView = findViewById(R.id.sub_build_paid_add_ons_recView);
        LinearLayoutManager llManager = new LinearLayoutManager(this);
        paidAddsRecView.setHasFixedSize(true);
        paidAddsRecView.setLayoutManager(llManager);
        PaidAddOnsAdapter paidAddOnsAdapter = new PaidAddOnsAdapter(this, paidAddOnList);
        paidAddsRecView.setAdapter(paidAddOnsAdapter);


        // Create Vegetable and populate LinearLayout with CheckBoxes
        vegeList = sandwichBuilderDatabase.getVegeDataAll();
        ll_vegetablesHolder = findViewById(R.id.sub_build_vegetables_checkBox_holder);
        CheckBox[] vegeCBs = new CheckBox[vegeList.size()];
        for(int i = 0; i < vegeList.size(); i++) {
            vegeCBs[i] = new CheckBox(this);
            vegeCBs[i].setText(vegeList.get(i).getVegetables());
            vegeCBs[i].setTextSize(16f);
            vegeCBs[i].setTextColor(Color.BLACK);
            ll_vegetablesHolder.addView(vegeCBs[i]);
        }

        // Create Sauces and populate LinearLayout with CheckBoxes
        sauceList = sandwichBuilderDatabase.getSauceDataAll();
        ll_saucesHolder = findViewById(R.id.sub_build_sauces_checkBox_holder);
        CheckBox[] saucesCB = new CheckBox[sauceList.size()];
        for(int i = 0;i < sauceList.size(); i++) {
            saucesCB[i] = new CheckBox(this);
            saucesCB[i].setText(sauceList.get(i).getSauces());
            saucesCB[i].setTextSize(16f);
            saucesCB[i].setTextColor(Color.BLACK);
            ll_saucesHolder.addView(saucesCB[i]);
        }

    }

}
