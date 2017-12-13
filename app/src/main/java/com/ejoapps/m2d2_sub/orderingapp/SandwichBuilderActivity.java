package com.ejoapps.m2d2_sub.orderingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ejoapps.m2d2_sub.orderingapp.adapters.SandwichNamesAndDescAdapter;
import com.ejoapps.m2d2_sub.orderingapp.containers.SandwichNameData;
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

    private List<String> breadTypesList;
    private List<SandwichNameData> sandwichNameList;
    private List<SandwichNameData> paidAddOnList;
    private List<String> vegeList;
    private List<String> sauceList;

    RecyclerView sandwichNDP;

    RadioGroup rg_bread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sandwich_builder);

        rg_bread = findViewById(R.id.sub_build_radio_button);

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = database.getReference().child("sandwiches");

        readDataFromFriebase(dbRef, new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                // Sandwich view recycler view activities
                sandwichNameList = getDataBig((Map<String, Object>) dataSnapshot.child("s_name_desc").getValue());

                for (int s = 0; s < sandwichNameList.size(); s++) {
                    Log.d("Test data Fetch", sandwichNameList.get(s).getsName()
                            + " " + sandwichNameList.get(s).getsDescription()
                            + " " + sandwichNameList.get(s).getsPrice());
                }

                sandwichNDP = findViewById(R.id.sub_build_sub_names_recView);
                sandwichNDP.setHasFixedSize(true);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SandwichBuilderActivity.this);
                sandwichNDP.setLayoutManager(linearLayoutManager);
                SandwichNamesAndDescAdapter sandwichNamesAndDescAdapter = new SandwichNamesAndDescAdapter(SandwichBuilderActivity.this, sandwichNameList);
                sandwichNDP.setAdapter(sandwichNamesAndDescAdapter);

                // RadioGroup functions and activities
                breadTypesList = getData((Map<String, Object>) dataSnapshot.child("breadTypes").getValue());

                RadioButton[] rb_breadTypes = new RadioButton[breadTypesList.size()];

                for (int i = 0; i < breadTypesList.size(); i++) {
                    rb_breadTypes[i] = new RadioButton(getApplicationContext());
                    rb_breadTypes[i].setText(breadTypesList.get(i));
                    rb_breadTypes[i].setId(i + 1000);
                    rg_bread.addView(rb_breadTypes[i]);
                }

                paidAddOnList = getDataBig((Map<String, Object>) dataSnapshot.child("paid").getValue());

            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure() {

            }
        });

    }

    private void readDataFromFriebase(DatabaseReference data, final OnGetDataListener onGetDataListener) {
        onGetDataListener.onStart();

        data.addListenerForSingleValueEvent(new ValueEventListener() {
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

    private List<String> getData(Map<String, Object> dataSnap) {
        List<String> detailItemList = new ArrayList<>();

        for (Map.Entry<String, Object> entry : dataSnap.entrySet()) {
            String singleUser = (String) entry.getValue();
            detailItemList.add(singleUser);
        }

        return detailItemList;
    }

    private List<SandwichNameData> getDataBig(Map<String, Object> dataSnap) {
        List<SandwichNameData> detailItemList = new ArrayList<>();

        for (Map.Entry<String, Object> entry : dataSnap.entrySet()) {
            String nameString = entry.getKey();
            String descAndPrice = (String) entry.getValue();
            String[] descAndPriceSep = descAndPrice.split("_");
            SandwichNameData sandwichNameData = new SandwichNameData(nameString, descAndPriceSep[0], descAndPriceSep[1]);
            detailItemList.add(sandwichNameData);
        }

        return detailItemList;
    }

}
