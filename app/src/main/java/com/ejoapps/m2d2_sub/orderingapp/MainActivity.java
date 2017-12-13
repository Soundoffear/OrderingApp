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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
            Toast.makeText(this, "User logged in", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "User NOT logged in", Toast.LENGTH_SHORT).show();
        }
    }

    private void createAccount(String emailAddress, String password) {

        if(!validateInput()) {
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(emailAddress, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("NEW USER", "createUserWithEmail: success");
                            sendValidationEmail();
                            tv_email.setText("Email: " + firebaseAuth.getCurrentUser().isEmailVerified());
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Log.w("NEW USER", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
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
        updateUI(null);
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
        Intent intent = new Intent(MainActivity.this, FirstPageActivity.class);
        startActivity(intent);
    }

    private void sendValidationEmail() {
        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        firebaseUser.sendEmailVerification().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Email sent", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Failed to send email", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        int idView = v.getId();
        if(idView == R.id.btn_sign_in) {
            //goToNextActivity();
            signIn(emailValue.getText().toString(), passValue.getText().toString());
        } else if (idView == R.id.btn_create_account) {
            createAccount(emailValue.getText().toString(), passValue.getText().toString());
        }
    }
}

