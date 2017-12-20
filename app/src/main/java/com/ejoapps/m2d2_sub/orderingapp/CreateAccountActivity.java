package com.ejoapps.m2d2_sub.orderingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ejoapps.m2d2_sub.orderingapp.containers.UserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccountActivity extends AppCompatActivity {

    EditText et_name;
    EditText et_surname;
    EditText et_mobile_phone;
    EditText et_address_street;
    EditText et_address_number;
    EditText et_address_city;
    EditText et_email;
    EditText et_password;

    Button newAccountCreate;
    Button cancel;

    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        firebaseAuth = FirebaseAuth.getInstance();

        et_name = findViewById(R.id.ca_user_name_input);
        et_surname = findViewById(R.id.ca_user_surname_input);
        et_mobile_phone = findViewById(R.id.ca_user_phone_input);
        et_address_street = findViewById(R.id.ca_user_street_delivery_input);
        et_address_number = findViewById(R.id.ca_user_home_number_input);
        et_address_city = findViewById(R.id.ca_user_city_input);
        et_email = findViewById(R.id.ca_e_mail_input);
        et_password = findViewById(R.id.ca_password_input);

        cancel = findViewById(R.id.cancel_ca);

        newAccountCreate = findViewById(R.id.create_ca);

        newAccountCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount(et_email.getText().toString(), et_password.getText().toString());

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void createAccount(String emailAddress, String password) {

        if (!validateInput()) {
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(emailAddress, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("NEW USER", "createUserWithEmail: success");
                            sendValidationEmail();
                            user = firebaseAuth.getCurrentUser();
                            createUserData(user);
                        } else {
                            Log.w("NEW USER", task.getException());
                            Toast.makeText(CreateAccountActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void createUserData(FirebaseUser fUser) {
        String sName = et_name.getText().toString();
        String sSurname = et_surname.getText().toString();
        String sMobilePhone = et_mobile_phone.getText().toString();
        String sAddressStreet = et_address_street.getText().toString();
        String sAddressNumber = et_address_number.getText().toString();
        String sAddressCity = et_address_city.getText().toString();
        String sEmail = et_email.getText().toString();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("clients");
        String userID = fUser.getUid();
        Log.d("USER ID", userID);

        UserData userData = new UserData(sName, sSurname, sMobilePhone, sAddressStreet, sAddressNumber, sAddressCity, sEmail);

        databaseReference.child(userID).setValue(userData);
    }

    private boolean validateInput() {
        boolean validate = true;

        String email = et_email.getText().toString();
        if (TextUtils.isEmpty(email)) {
            et_email.setError("Required");
            validate = false;
        } else {
            et_email.setError(null);
        }

        String pass = et_password.getText().toString();
        if (TextUtils.isEmpty(pass)) {
            et_password.setError("Required");
            validate = false;
        } else {
            et_password.setError(null);
        }

        return validate;
    }

    private void sendValidationEmail() {
        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        firebaseUser.sendEmailVerification().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(CreateAccountActivity.this, "Email sent", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CreateAccountActivity.this, "Failed to send email", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
