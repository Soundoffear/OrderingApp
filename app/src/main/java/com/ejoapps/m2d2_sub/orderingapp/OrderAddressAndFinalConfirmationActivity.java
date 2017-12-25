package com.ejoapps.m2d2_sub.orderingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ejoapps.m2d2_sub.orderingapp.containers.UserData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;


public class OrderAddressAndFinalConfirmationActivity extends AppCompatActivity {

    Spinner addressSpinner;

    TextView tv_finalPrice;
    TextView tv_addressStreet;
    TextView tv_addressNumber;
    TextView tv_addressCity;
    TextView tv_phoneNumber;

    EditText et_addressStreet_input;
    EditText et_addressNumber_imput;
    EditText et_addressCity_input;

    private double finalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_address_and_final_confirmation);

        tv_finalPrice = findViewById(R.id.oa_tv_total_order_price_output);
        tv_addressStreet = findViewById(R.id.oa_tv_delivery_address_street_output);
        tv_addressNumber = findViewById(R.id.oa_tv_delivery_address_number_output);
        tv_addressCity = findViewById(R.id.oa_tv_delivery_address_city_output);
        tv_phoneNumber = findViewById(R.id.oa_tv_phone_output);

        et_addressStreet_input = findViewById(R.id.oa_et_delivery_address_street_input);
        et_addressNumber_imput = findViewById(R.id.oa_et_delivery_address_number_input);
        et_addressCity_input = findViewById(R.id.oa_et_delivery_address_city_input);

        addressSpinner = findViewById(R.id.oa_spinner_delivery_address_picker);

        ArrayAdapter<CharSequence> addressAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.address_spinner));

        addressAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        addressSpinner.setAdapter(addressAdapter);

        setUserDetails(addressSpinner);

        addressSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        getAddressFromDB();
                        hideEditText();
                        break;
                    case 1:
                        //TODO sending message to subway that sub will be picked at restaurant
                        Log.d("PICK UP AT SUBWAY", "Pick-Up subs at subway");
                        break;
                    case 2:
                        //TODO replace TextView with EditText in order to input new address
                        Toast.makeText(getApplicationContext(), "STILL NEEDs TO BE IMPLEMENTED", Toast.LENGTH_SHORT).show();
                        showEditText();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Intent intentFromSummary = getIntent();
        Bundle dataFromSummary = intentFromSummary.getExtras();
        assert dataFromSummary != null;
        if (CateringListToBuildActivity.isCatering) {
            finalPrice = dataFromSummary.getDouble(CateringListToBuildActivity.FINAL_ORDER_PRICE);
        } else {
            finalPrice = dataFromSummary.getDouble(OrderSummaryActivity.TOTAL_ORDER_FINAL);
        }

        DecimalFormat format = new DecimalFormat("0.00");
        String sFinalPrice = format.format(finalPrice) + " PLN";
        tv_finalPrice.setText(sFinalPrice);

    }

    void showEditText() {
        et_addressStreet_input.setVisibility(View.VISIBLE);
        et_addressNumber_imput.setVisibility(View.VISIBLE);
        et_addressCity_input.setVisibility(View.VISIBLE);
        tv_addressStreet.setVisibility(View.GONE);
        tv_addressNumber.setVisibility(View.GONE);
        tv_addressCity.setVisibility(View.GONE);
    }

    void hideEditText() {
        et_addressStreet_input.setVisibility(View.GONE);
        et_addressNumber_imput.setVisibility(View.GONE);
        et_addressCity_input.setVisibility(View.GONE);
        tv_addressStreet.setVisibility(View.VISIBLE);
        tv_addressNumber.setVisibility(View.VISIBLE);
        tv_addressCity.setVisibility(View.VISIBLE);
    }

    void setUserDetails(Spinner spinner) {
        int selectedItem = spinner.getSelectedItemPosition();

        switch (selectedItem) {
            case 0:
                getAddressFromDB();
                break;
            case 1:
                //TODO sending message to subway that sub will be picked at restaurant
                Log.d("PICK UP AT SUBWAY", "Pick-Up subs at subway");
                break;
            case 2:
                //TODO replace TextView with EditText in order to input new address
                break;
        }

    }

    void getAddressFromDB() {

        final String currentLoggedUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("clients");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserData userDataFromDB = dataSnapshot.child(currentLoggedUserId).getValue(UserData.class);

                try {
                    assert userDataFromDB != null;
                    tv_addressStreet.setText(userDataFromDB.getUserAddressStreet());
                    tv_addressNumber.setText(userDataFromDB.getUserAddressNumber());
                    tv_addressCity.setText(userDataFromDB.getUserAddressCity());
                    tv_phoneNumber.setText(userDataFromDB.getUserPhone());
                } catch (NullPointerException npe) {
                    Log.d("Database Problem", "Could not load data from DB");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
