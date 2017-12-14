package com.ejoapps.m2d2_sub.orderingapp.containers;

/**
 * Created by soundoffear on 13/12/2017.
 */

public class PaidAddOnsData {

    String paidAddOnName;
    String paidAddOnPrice;

    public PaidAddOnsData(String paidAddOnName, String paidAddOnPrice) {
        this.paidAddOnName = paidAddOnName;
        this.paidAddOnPrice = paidAddOnPrice;
    }

    public String getPaidAddOnName() {
        return paidAddOnName;
    }

    public void setPaidAddOnName(String paidAddOnName) {
        this.paidAddOnName = paidAddOnName;
    }

    public String getPaidAddOnPrice() {
        return paidAddOnPrice;
    }

    public void setPaidAddOnPrice(String paidAddOnPrice) {
        this.paidAddOnPrice = paidAddOnPrice;
    }
}
