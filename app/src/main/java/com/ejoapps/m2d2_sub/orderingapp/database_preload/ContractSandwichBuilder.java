package com.ejoapps.m2d2_sub.orderingapp.database_preload;

import android.provider.BaseColumns;

/**
 * Created by soundoffear on 13/12/2017.
 */

public class ContractSandwichBuilder implements BaseColumns {

    public static class SandwichNames {
        public static final String TABLE_NAME_SANDWICH_NAMES = "sandwiches_table";
        public static final String SANDWICH_NAME = "sandwich_name";
        public static final String SANDWICH_DESCRIPTION = "sandwich_description";
        public static final String SANDWICH_PRICE = "sandwich_price";
    }

    public static class BreadTypes {
        public static final String TABLE_NAME_BREAD_TYPES = "bread_types_table";
        public static final String BREAD_TYPE = "bread_types";
    }

    public static class PaidAddOns {
        public static final String TABLE_NAME_PAID_ADD = "paid_adds_table";
        public static final String ADD_ON_NAME = "add_on_name";
        public static final String ADD_ON_PRICE = "add_on_price";
    }

    public static class Cheeses {
        public static final String TABLE_NAME_CHEESES = "cheeses_table";
        public static final String CHEESES_NAME = "cheeses_name";
    }

    public static class Vegetables {
        public static final String TABLE_NAME_VEGE = "vege_table";
        public static final String VEGE_NAME = "vege_name";
    }

    public static class Sauces {
        public static final String TABLE_NAME_SAUCE = "sauce_table";
        public static final String SAUCE_NAME = "sauce_name";
    }

}
