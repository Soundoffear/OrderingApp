package com.ejoapps.m2d2_sub.orderingapp.containers;

/**
 * Created by soundoffear on 13/12/2017.
 */

public class SandwichNameData {

    String sName;
    String sDescription;
    String sPrice;

    public SandwichNameData() {

    }

    public SandwichNameData(String sandwichName, String sandwichDescription, String sandwichPrice) {
        this.sName = sandwichName;
        this.sDescription = sandwichDescription;
        this.sPrice = sandwichPrice;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsDescription() {
        return sDescription;
    }

    public void setsDescription(String sDescription) {
        this.sDescription = sDescription;
    }

    public String getsPrice() {
        return sPrice;
    }

    public void setsPrice(String sPrice) {
        this.sPrice = sPrice;
    }
}
