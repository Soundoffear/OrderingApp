package com.ejoapps.m2d2_sub.orderingapp.database_preload;

import android.provider.BaseColumns;

class ContractOrderedDrinks {

    class OrderedDrinksEntry implements BaseColumns {

        static final String DRINKS_TABLE_NAME = "drinks_ordered_table";
        static final String DRINK_NAME = "drink_name";
        static final String DRINK_QUANTITY = "drink_quantity";
        static final String DRINK_PRICE = "drink_price_sum";

    }

}
