package com.ejoapps.m2d2_sub.orderingapp.interfaces;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by soundoffear on 13/12/2017.
 */

public interface OnGetDataListener {
    void onSuccess(DataSnapshot dataSnapshot);
    void onStart();
    void onFailure();
}
