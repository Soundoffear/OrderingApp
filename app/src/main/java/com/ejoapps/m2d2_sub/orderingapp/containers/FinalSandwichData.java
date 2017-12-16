package com.ejoapps.m2d2_sub.orderingapp.containers;

/**
 * Created by soundoffear on 15/12/2017.
 */

public class FinalSandwichData {

    private String subTag;
    private String subName;
    private String subBread;
    private String subPaid;
    private String subVege;
    private String subSauce;
    private String subPrice;

    public FinalSandwichData(String subTag, String subName, String subBread, String subPaid, String subVege, String subSauce, String subPrice) {
        this.subTag = subTag;
        this.subName = subName;
        this.subBread = subBread;
        this.subPaid = subPaid;
        this.subVege = subVege;
        this.subSauce = subSauce;
        this.subPrice = subPrice;
    }

    public String getSubTag() {
        return subTag;
    }

    public void setSubTag(String subTag) {
        this.subTag = subTag;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getSubBread() {
        return subBread;
    }

    public void setSubBread(String subBread) {
        this.subBread = subBread;
    }

    public String getSubPaid() {
        return subPaid;
    }

    public void setSubPaid(String subPaid) {
        this.subPaid = subPaid;
    }

    public String getSubVege() {
        return subVege;
    }

    public void setSubVege(String subVege) {
        this.subVege = subVege;
    }

    public String getSubSauce() {
        return subSauce;
    }

    public void setSubSauce(String subSauce) {
        this.subSauce = subSauce;
    }

    public String getSubPrice() {
        return subPrice;
    }

    public void setSubPrice(String subPrice) {
        this.subPrice = subPrice;
    }
}
