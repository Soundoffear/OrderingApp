package com.ejoapps.m2d2_sub.orderingapp.storage;

/**
 * Created by soundoffear on 12/12/2017.
 */

public class TypeAndNumberOfCarriersStorage {

    public String type;
    public int quantity;

    public TypeAndNumberOfCarriersStorage() {

    }

    public TypeAndNumberOfCarriersStorage(String _type, int _quantity) {
        this.type = _type;
        this.quantity = _quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
