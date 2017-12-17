package com.ejoapps.m2d2_sub.orderingapp.storage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soundoffear on 15/12/2017.
 */

public class SandwichListStorage {

    public static final String CARRIER_SUB15 = "sub15";
    public static final String CARRIER_SUB30 = "sub30";
    public static final String CARRIER_WRAP = "wrap";
    public static final String CARRIER_SALAD = "salad";

    public static int positionFromToReplace;

    public static boolean isSandwichBuilt = true;

    public static String carrierType;

    public static List<String> allCarriersTogether = new ArrayList<>();

}
