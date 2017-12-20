package com.ejoapps.m2d2_sub.orderingapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ejoapps.m2d2_sub.orderingapp.containers.BreadTypesData;
import com.ejoapps.m2d2_sub.orderingapp.containers.PaidAddOnsData;
import com.ejoapps.m2d2_sub.orderingapp.containers.SandwichNameData;
import com.ejoapps.m2d2_sub.orderingapp.containers.SauceData;
import com.ejoapps.m2d2_sub.orderingapp.containers.VegetablesData;
import com.ejoapps.m2d2_sub.orderingapp.database_preload.ContractSandwichBuilder;
import com.ejoapps.m2d2_sub.orderingapp.database_preload.SandwichBuilderDatabase;
import com.ejoapps.m2d2_sub.orderingapp.interfaces.OnGetDataListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    TextView tv_email;

    EditText emailValue;
    EditText passValue;

    Button sign_in_button;
    Button create_account_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        if(database == null) {
            database.setPersistenceEnabled(true);
        }

        emailValue = findViewById(R.id.et_email_input);
        passValue = findViewById(R.id.et_password_input);

        tv_email = findViewById(R.id.tv_email_address_label);

        sign_in_button = findViewById(R.id.btn_sign_in);
        create_account_button = findViewById(R.id.btn_create_account);

        sign_in_button.setOnClickListener(this);
        create_account_button.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        updateUI(firebaseUser);
    }

    void updateUI(FirebaseUser firebaseUser) {
        if (firebaseUser != null) {
            Toast.makeText(this, "User logged in " + firebaseUser.getUid(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "User NOT logged in", Toast.LENGTH_SHORT).show();
        }
    }

    private void signIn(String emailAddress, String password) {

        if(!validateInput()) {
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(emailAddress, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Log.d("NEW USER", "createUserWithEmail: success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            tv_email.setText("Email: " + firebaseAuth.getCurrentUser().isEmailVerified());


                            goToNextActivity();
                            updateUI(user);
                        } else {
                            Log.w("NEW USER", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void signOut() {
        firebaseAuth.signOut();
    }

    private boolean validateInput() {
        boolean validate = true;

        String email = emailValue.getText().toString();
        if(TextUtils.isEmpty(email)) {
            emailValue.setError("Required");
            validate = false;
        } else {
            emailValue.setError(null);
        }

        String pass = passValue.getText().toString();
        if(TextUtils.isEmpty(pass)) {
            passValue.setError("Required");
            validate = false;
        } else {
            passValue.setError(null);
        }

        return validate;
    }

    private void goToNextActivity() {
        Intent intent = new Intent(MainActivity.this, MainScreenNavDrawer.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        int idView = v.getId();
        if(idView == R.id.btn_sign_in) {
            signIn(emailValue.getText().toString(), passValue.getText().toString());

            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference dbReference = firebaseDatabase.getReference().child("sandwiches");

            readDataFromFirebase(dbReference, new OnGetDataListener() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {

                    putSandwichNameDataList((Map<String, Object>) dataSnapshot.child("s_name_desc").getValue());
                    fetchBreadData((Map<String, Object>) dataSnapshot.child("breadTypes").getValue());
                    fetchPaidAddOnsData((Map<String, Object>) dataSnapshot.child("paid").getValue());
                    fetchVegetableData((Map<String, Object>) dataSnapshot.child("veges").getValue());
                    fetchSauceData((Map<String, Object>) dataSnapshot.child("sauces").getValue());
                }

                @Override
                public void onStart() {

                }

                @Override
                public void onFailure() {

                }
            });

        } else if (idView == R.id.btn_create_account) {
            Intent intentToCreateUser = new Intent(MainActivity.this, CreateAccountActivity.class);
            startActivity(intentToCreateUser);
        }
    }

    public void readDataFromFirebase(DatabaseReference dbReference, final OnGetDataListener onGetDataListener) {
        onGetDataListener.onStart();

        dbReference.addListenerForSingleValueEvent(new ValueEventListener() {
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

    public void putSandwichNameDataList(Map<String, Object> sandwichNameData) {

        SandwichBuilderDatabase sandwichBuilderDatabase = new SandwichBuilderDatabase(this);
        sandwichBuilderDatabase.deleteAllRecordsFromDB(ContractSandwichBuilder.SandwichNames.TABLE_NAME_SANDWICH_NAMES);

        for(Map.Entry<String, Object> dataEntry : sandwichNameData.entrySet()) {
            String sandwichName = dataEntry.getKey();
            String sandwichDescNPrice = (String) dataEntry.getValue();
            String[] splittedDescAndPrice = sandwichDescNPrice.split("_");
            SandwichNameData sandwichNameDataDB = new SandwichNameData(sandwichName, splittedDescAndPrice[0], splittedDescAndPrice[1]);
            sandwichBuilderDatabase.insertIntoSandwichNamesDB(sandwichNameDataDB);
        }
    }

    public void fetchBreadData(Map<String, Object> breadTypesDataMap) {
        SandwichBuilderDatabase sandwichBuilderDatabase = new SandwichBuilderDatabase(this);
        sandwichBuilderDatabase.deleteAllRecordsFromDB(ContractSandwichBuilder.BreadTypes.TABLE_NAME_BREAD_TYPES);

        for(Map.Entry<String, Object> breadTypeEntries : breadTypesDataMap.entrySet()) {
            String breadType = (String) breadTypeEntries.getValue();
            BreadTypesData breadTypesData = new BreadTypesData(breadType);
            sandwichBuilderDatabase.insertIntoBreadTypesDB(breadTypesData);
        }
    }

    public void fetchPaidAddOnsData(Map<String, Object> paidDataAddOnsMap) {
        SandwichBuilderDatabase sandwichBuilderDatabase = new SandwichBuilderDatabase(this);
        sandwichBuilderDatabase.deleteAllRecordsFromDB(ContractSandwichBuilder.PaidAddOns.TABLE_NAME_PAID_ADD);

        for(Map.Entry<String, Object> paidAddOnEntry : paidDataAddOnsMap.entrySet()) {
            String paidAddOnNameAndPrice = (String) paidAddOnEntry.getValue();
            String[] paidAddOnNameAndPriceSplit = paidAddOnNameAndPrice.split("_");
            PaidAddOnsData paidAddOnsData = new PaidAddOnsData(paidAddOnNameAndPriceSplit[0], paidAddOnNameAndPriceSplit[1]);
            sandwichBuilderDatabase.insertIntoPaidAddOnsDB(paidAddOnsData);
        }
    }

    public void fetchVegetableData(Map<String, Object> vegetableDataMap) {
        SandwichBuilderDatabase sandwichBuilderDatabase = new SandwichBuilderDatabase(this);
        sandwichBuilderDatabase.deleteAllRecordsFromDB(ContractSandwichBuilder.Vegetables.TABLE_NAME_VEGE);

        for(Map.Entry<String, Object> vegetableEntry : vegetableDataMap.entrySet()) {
            String vegeName = (String) vegetableEntry.getValue();
            VegetablesData vegetablesData = new VegetablesData(vegeName);
            sandwichBuilderDatabase.insertIntoVegetablesDB(vegetablesData);
        }
    }

    public void fetchSauceData(Map<String, Object> sauceDataMap) {
        SandwichBuilderDatabase sandwichBuilderDatabase = new SandwichBuilderDatabase(this);
        sandwichBuilderDatabase.deleteAllRecordsFromDB(ContractSandwichBuilder.Sauces.TABLE_NAME_SAUCE);

        for(Map.Entry<String, Object> sauceEntry : sauceDataMap.entrySet()) {
            String sauceName = (String) sauceEntry.getValue();
            SauceData sauceData = new SauceData(sauceName);
            sandwichBuilderDatabase.insertIntoSaucesDB(sauceData);
        }
    }

}

