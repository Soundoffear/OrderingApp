package com.ejoapps.m2d2_sub.orderingapp.containers;

/**
 * Created by soundoffear on 18/12/2017.
 */

public class UserData {

    private String userName;
    private String userUserSurname;
    private String userPhone;
    private String userAddressStreet;
    private String userAddressNumber;
    private String userAddressCity;
    private String userEmail;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserUserSurname() {
        return userUserSurname;
    }

    public void setUserUserSurname(String userUserSurname) {
        this.userUserSurname = userUserSurname;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserAddressStreet() {
        return userAddressStreet;
    }

    public void setUserAddressStreet(String userAddressStreet) {
        this.userAddressStreet = userAddressStreet;
    }

    public String getUserAddressNumber() {
        return userAddressNumber;
    }

    public void setUserAddressNumber(String userAddressNumber) {
        this.userAddressNumber = userAddressNumber;
    }

    public String getUserAddressCity() {
        return userAddressCity;
    }

    public void setUserAddressCity(String userAddressCity) {
        this.userAddressCity = userAddressCity;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
