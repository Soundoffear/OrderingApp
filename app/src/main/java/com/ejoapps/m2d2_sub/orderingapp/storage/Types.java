package com.ejoapps.m2d2_sub.orderingapp.storage;

import java.io.Serializable;

/**
 * Created by soundoffear on 12/12/2017.
 */

public class Types implements Serializable {

    public String typeName;

    public Types(String typeName) {
        this.typeName = typeName;
    }

    public Types() {
    }
}
