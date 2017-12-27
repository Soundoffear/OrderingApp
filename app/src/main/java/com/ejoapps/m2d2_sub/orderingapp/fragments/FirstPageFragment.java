package com.ejoapps.m2d2_sub.orderingapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ejoapps.m2d2_sub.orderingapp.CateringOrderActivity;
import com.ejoapps.m2d2_sub.orderingapp.SandwichQuantityAndTypeActivity;
import com.ejoapps.m2d2_sub.orderingapp.R;
import com.ejoapps.m2d2_sub.orderingapp.interfaces.OnGetDataListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FirstPageFragment extends Fragment implements View.OnClickListener{

    Button subOrderButton;
    Button cateringOrderButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.activity_first_page_fragment, container, false);

        subOrderButton = mainView.findViewById(R.id.main_screen_btn_order_subs);
        cateringOrderButton = mainView.findViewById(R.id.main_screen_btn_order_catering);

        subOrderButton.setEnabled(false);
        cateringOrderButton.setEnabled(false);

        subOrderButton.setOnClickListener(this);
        cateringOrderButton.setOnClickListener(this);

        return mainView;
    }

    public static List<String> sandwichTypesList;
    public static List<String> cateringTypeList;

    @Override
    public void onStart() {
        super.onStart();

        sandwichTypesList = new ArrayList<>();
        cateringTypeList = new ArrayList<>();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        loadAllData(databaseReference, new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                long dataBaseChilderCount = dataSnapshot.getChildrenCount();
                Log.d("Test new way", String.valueOf(dataBaseChilderCount));

                Map<String, Object> mappedData = (Map<String, Object>) dataSnapshot.child("sandwiches").child("types").getValue();

                assert mappedData != null;
                for (Map.Entry<String, Object> entry: mappedData.entrySet()) {
                    sandwichTypesList.add((String) entry.getValue());
                }

                Map<String, Object> mappedCateringData = (Map<String, Object>) dataSnapshot.child("catering").getValue();

                assert mappedCateringData != null;
                for(Map.Entry<String, Object> cateringEntry: mappedCateringData.entrySet()) {
                    cateringTypeList.add(cateringEntry.getKey()+"_"+cateringEntry.getValue());
                }


                subOrderButton.setEnabled(true);
                cateringOrderButton.setEnabled(true);
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure() {

            }
        });

    }

    public void loadAllData(DatabaseReference databaseReference, final OnGetDataListener onGetDataListener) {
        onGetDataListener.onStart();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                onGetDataListener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static final String LIST_OF_TYPES = "carriers_types";
    public static final String CATERING_TYPES = "catering_types";

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.main_screen_btn_order_subs:
                Intent intent = new Intent(getContext(), SandwichQuantityAndTypeActivity.class);
                Bundle transferData = new Bundle();
                transferData.putStringArrayList(LIST_OF_TYPES, (ArrayList) sandwichTypesList);
                intent.putExtras(transferData);
                startActivity(intent);
                break;
            case R.id.main_screen_btn_order_catering:
                Intent cateringIntent = new Intent(getContext(), CateringOrderActivity.class);
                Bundle cateringData = new Bundle();
                cateringData.putStringArrayList(CATERING_TYPES, (ArrayList) cateringTypeList);
                cateringIntent.putExtras(cateringData);
                startActivity(cateringIntent);
        }
    }
}
