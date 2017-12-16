package com.ejoapps.m2d2_sub.orderingapp.storage;

import com.ejoapps.m2d2_sub.orderingapp.SandwichBuilderActivity;

import java.text.DecimalFormat;
import java.util.Formatter;

/**
 * Created by soundoffear on 14/12/2017.
 */

public class TotalSandwichValue {


    public static SandwichBuilderActivity sandwichBuilderActivity;
    public static double sandwichValue = 0;
    public static double paidAddOns = 0;

    public static String finalValue() {
        sandwichBuilderActivity = new SandwichBuilderActivity();
        double finalValue = sandwichValue + paidAddOns;

        return alwaysTwoDecimal(finalValue);
    }

    private static String alwaysTwoDecimal(double value) {
        DecimalFormat valueDecimal = new DecimalFormat("0.00");
        String valueFormated = valueDecimal.format(value);

        return valueFormated;
    }



}
