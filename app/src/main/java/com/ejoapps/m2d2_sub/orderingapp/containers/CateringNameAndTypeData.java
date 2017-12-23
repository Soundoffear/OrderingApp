package com.ejoapps.m2d2_sub.orderingapp.containers;

import android.os.Parcel;
import android.os.Parcelable;

public class CateringNameAndTypeData implements Parcelable {

    private String cateringName;
    private int cateringValue;
    private double cateringPrice;

    public CateringNameAndTypeData(String cateringName, int cateringValue, double cateringPrice) {
        this.cateringName = cateringName;
        this.cateringValue = cateringValue;
        this.cateringPrice = cateringPrice;
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
