package com.ejoapps.m2d2_sub.orderingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ejoapps.m2d2_sub.orderingapp.storage.Types;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FirstPageActivity extends AppCompatActivity {

    Button orderButton;
    Button cateringButton;
    EditText editText1;

    public static List<String> dataStrings;
    private static List<String> keysString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        orderButton = findViewById(R.id.order_sandwich);
        cateringButton = findViewById(R.id.order_catering);

        editText1 = findViewById(R.id.input_test_1);

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<String> carrierTypes = (ArrayList<String>) dataStrings;

                Intent intent = new Intent(FirstPageActivity.this, QuantityAndTypeActivity.class);
                intent.putStringArrayListExtra("DATA_LIST", carrierTypes);
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = database.getReference().child("sandwiches").child("types");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                getData((Map<String, Object>) dataSnapshot.getValue());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getData(Map<String, Object> dataSnap) {
        dataStrings = new ArrayList<>();
        keysString = new ArrayList<>();

        for (Map.Entry<String, Object> entry : dataSnap.entrySet()) {
            String singleKey = entry.getKey();
            String singleUser = (String) entry.getValue();
            dataStrings.add(singleUser);
            keysString.add(singleKey);
        }

        for (int i = 0; i < dataStrings.size(); i++) {
            Log.d("Array values ", dataStrings.get(i));
            Log.d("Array keys ", keysString.get(i));
        }
    }
}
