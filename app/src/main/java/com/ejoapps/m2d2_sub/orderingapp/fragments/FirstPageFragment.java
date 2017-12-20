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

import com.ejoapps.m2d2_sub.orderingapp.QuantityAndTypeActivity;
import com.ejoapps.m2d2_sub.orderingapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FirstPageFragment extends Fragment implements View.OnClickListener{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.activity_first_page_fragment, container, false);

        Button subOrderButton = mainView.findViewById(R.id.main_screen_btn_order_subs);
        Button cateringOrderButton = mainView.findViewById(R.id.main_screen_btn_order_catering);

        subOrderButton.setOnClickListener(this);
        cateringOrderButton.setOnClickListener(this);

        return mainView;
    }

    private List<String> sandwichTypesList;

    @Override
    public void onStart() {
        super.onStart();

        sandwichTypesList = new ArrayList<>();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("sandwiches").child("types");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long dataBaseChilderCount = dataSnapshot.getChildrenCount();
                Log.d("Test new way", String.valueOf(dataBaseChilderCount));

                Map<String, Object> mappedData = (Map<String, Object>) dataSnapshot.getValue();

                for (Map.Entry<String, Object> entry: mappedData.entrySet()) {
                    sandwichTypesList.add((String) entry.getValue());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public static final String LIST_OF_TYPES = "carriers_types";

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.main_screen_btn_order_subs:
                Intent intent = new Intent(getContext(), QuantityAndTypeActivity.class);
                Bundle transferData = new Bundle();
                transferData.putStringArrayList(LIST_OF_TYPES, (ArrayList) sandwichTypesList);
                intent.putExtras(transferData);
                startActivity(intent);
        }
    }
}
