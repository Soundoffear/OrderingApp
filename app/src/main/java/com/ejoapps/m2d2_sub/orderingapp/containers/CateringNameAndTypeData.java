package com.ejoapps.m2d2_sub.orderingapp.containers;

import android.os.Parcel;
import android.os.Parcelable;

public class CateringNameAndTypeData implements Parcelable {

    private String cateringName;
    private String sub1;
    private String sub2;
    private String sub3;
    private String sub4;
    private String sCateringPrice;
    private int cateringValue;
    private double cateringPrice;

    public CateringNameAndTypeData(String cateringName, String sub1, String sub2, String sub3, String sub4, String sCatPrice) {
        this.cateringName = cateringName;
        this.sub1 = sub1;
        this.sub2 = sub2;
        this.sub3 = sub3;
        this.sub4 = sub4;
        this.sCateringPrice = sCatPrice;
    }

    public CateringNameAndTypeData(String cateringName, int cateringValue, double cateringPrice) {
        this.cateringName = cateringName;
        this.cateringValue = cateringValue;
        this.cateringPrice = cateringPrice;
    }

    public String getsCateringPrice() {
        return sCateringPrice;
    }

    public void setsCateringPrice(String sCateringPrice) {
        this.sCateringPrice = sCateringPrice;
    }

    public String getSub1() {
        return sub1;
    }

    public void setSub1(String sub1) {
        this.sub1 = sub1;
    }

    public String getSub2() {
        return sub2;
    }

    public void setSub2(String sub2) {
        this.sub2 = sub2;
    }

    public String getSub3() {
        return sub3;
    }

    public void setSub3(String sub3) {
        this.sub3 = sub3;
    }

    public String getSub4() {
        return sub4;
    }

    public void setSub4(String sub4) {
        this.sub4 = sub4;
    }

    public double getCateringPrice() {
        return cateringPrice;
    }

    public void setCateringPrice(double cateringPrice) {
        this.cateringPrice = cateringPrice;
    }

    public String getCateringName() {
        return cateringName;
    }

    public void setCateringName(String cateringName) {
        this.cateringName = cateringName;
    }

    public int getCateringValue() {
        return cateringValue;
    }

    public void setCateringValue(int cateringValue) {
        this.cateringValue = cateringValue;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cateringName);
        dest.writeInt(cateringValue);
        dest.writeDouble(cateringPrice);
    }

    private CateringNameAndTypeData(Parcel in) {
        cateringName = in.readString();
        cateringValue = in.readInt();
        cateringPrice = in.readDouble();
    }

    public static final Parcelable.Creator<CateringNameAndTypeData> CREATOR = new Creator<CateringNameAndTypeData>() {
        @Override
        public CateringNameAndTypeData createFromParcel(Parcel source) {
            return new CateringNameAndTypeData(source);
        }

        @Override
        public CateringNameAndTypeData[] newArray(int size) {
            return new CateringNameAndTypeData[size];
        }
    };
}
