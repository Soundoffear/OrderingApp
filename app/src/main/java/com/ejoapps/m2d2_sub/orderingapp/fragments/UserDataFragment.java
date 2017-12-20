package com.ejoapps.m2d2_sub.orderingapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ejoapps.m2d2_sub.orderingapp.R;
import com.ejoapps.m2d2_sub.orderingapp.containers.UserData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserDataFragment extends Fragment {

    DatabaseReference userDataBase;
    FirebaseUser currentUser;
    TextView userName;
    TextView userSurname;
    TextView userPhone;
    TextView userEmail;
    TextView userAddressStreet;
    TextView userAddressNumber;
    TextView userAddressCity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View userData = inflater.inflate(R.layout.activity_user_data_fragment, container, false);

        userName = userData.findViewById(R.id.udf_name_output);
        userSurname = userData.findViewById(R.id.udf_surname_output);
        userPhone = userData.findViewById(R.id.udf_phone_output);
        userEmail = userData.findViewById(R.id.udf_e_mail_output);
        userAddressStreet = userData.findViewById(R.id.udf_address_1_street_output);
        userAddressNumber = userData.findViewById(R.id.udf_address_1_number_output);
        userAddressCity = userData.findViewById(R.id.udf_address_1_city_output);

        userDataBase = FirebaseDatabase.getInstance().getReference().child("clients");

        return userData;
    }

    @Override
    public void onStart() {
        super.onStart();

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        final String currentUserId = currentUser.getUid();

        ValueEventListener userData = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserData userD = dataSnapshot.child(currentUserId).getValue(UserData.class);

                userName.setText(userD.getUserName());
                userSurname.setText(userD.getUserUserSurname());
                userPhone.setText(userD.getUserPhone());
                userEmail.setText(userD.getUserEmail());
                userAddressStreet.setText(userD.getUserAddressStreet());
                userAddressNumber.setText(userD.getUserAddressNumber());
                userAddressCity.setText(userD.getUserAddressCity());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        userDataBase.addValueEventListener(userData);
    }
}
