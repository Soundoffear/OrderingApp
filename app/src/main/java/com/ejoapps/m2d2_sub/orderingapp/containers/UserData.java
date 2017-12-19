package com.ejoapps.m2d2_sub.orderingapp.containers;

/**
 * Created by soundoffear on 18/12/2017.
 */

public class UserData {

    String userName;
    String userUserSurname;
    String userPhone;
    String userAddressStreet;
    String userAddressNumber;
    String userAddressCity;
    String userEmail;

    public UserData() {

    }

    public UserData(String userName, String userUserSurname, String userPhone, String userAddressStreet, String userAddressNumber, String userAddressCity, String userEmail) {
        this.userName = userName;
        this.userUserSurname = userUserSurname;
        this.userPhone = userPhone;
        this.userAddressStreet = userAddressStreet;
        this.userAddressNumber = userAddressNumber;
        this.userAddressCity = userAddressCity;
        this.userEmail = userEmail;
    }
}
