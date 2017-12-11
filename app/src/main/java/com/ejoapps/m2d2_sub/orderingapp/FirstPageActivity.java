package com.ejoapps.m2d2_sub.orderingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirstPageActivity extends AppCompatActivity {

    Button orderButton;
    Button cateringButton;
    EditText editText1;

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



            }
        });


    }
}
