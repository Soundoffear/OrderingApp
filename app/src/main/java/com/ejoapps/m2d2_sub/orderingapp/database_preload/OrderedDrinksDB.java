package com.ejoapps.m2d2_sub.orderingapp.database_preload;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ejoapps.m2d2_sub.orderingapp.containers.OrderedDrinksData;

import java.util.ArrayList;
import java.util.List;

public class OrderedDrinksDB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "drinks_database.db";

    public OrderedDrinksDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String createOrderedDrinksTable = "CREATE TABLE " + ContractOrderedDrinks.OrderedDrinksEntry.DRINKS_TABLE_NAME
                +"(" + ContractOrderedDrinks.OrderedDrinksEntry._ID + " INTEGER PRIMARY KEY, "
                + ContractOrderedDrinks.OrderedDrinksEntry.DRINK_NAME + " TEXT NOT NULL,"
                + ContractOrderedDrinks.OrderedDrinksEntry.DRINK_QUANTITY + " TEXT NOT NULL,"
                + ContractOrderedDrinks.OrderedDrinksEntry.DRINK_PRICE + " TEXT NOT NULL )";
        db.execSQL(createOrderedDrinksTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ContractOrderedDrinks.OrderedDrinksEntry.DRINKS_TABLE_NAME);
        onCreate(db);
    }

    public void insertDataToOrderedDrinksDB(OrderedDrinksData orderedDrinksData) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ContractOrderedDrinks.OrderedDrinksEntry.DRINK_NAME, orderedDrinksData.getDrinkName());
        cv.put(ContractOrderedDrinks.OrderedDrinksEntry.DRINK_QUANTITY, orderedDrinksData.getDrinkQuantity());
        cv.put(ContractOrderedDrinks.OrderedDrinksEntry.DRINK_PRICE, orderedDrinksData.getDrinkPrice());

        db.insert(ContractOrderedDrinks.OrderedDrinksEntry.DRINKS_TABLE_NAME,
                null,
                cv);
        db.close();
    }

    public List<OrderedDrinksData> getAllOrderedDataDrinks() {
        SQLiteDatabase db = getReadableDatabase();
        List<OrderedDrinksData> orderedDrinksData = new ArrayList<>();

        Cursor cursor = db.query(ContractOrderedDrinks.OrderedDrinksEntry.DRINKS_TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        if(cursor.moveToFirst()) {
            do {
                String drinkName = cursor.getString(cursor.getColumnIndex(ContractOrderedDrinks.OrderedDrinksEntry.DRINK_NAME));
                String drinkQuantity = cursor.getString(cursor.getColumnIndex(ContractOrderedDrinks.OrderedDrinksEntry.DRINK_QUANTITY));
                String drinkPrice = cursor.getString(cursor.getColumnIndex(ContractOrderedDrinks.OrderedDrinksEntry.DRINK_PRICE));
                OrderedDrinksData orderedDrinks = new OrderedDrinksData(drinkName, drinkQuantity, drinkPrice);
                orderedDrinksData.add(orderedDrinks);
            } while (cursor.moveToNext());
        }

        db.close();

        return orderedDrinksData;
    }

    public void deleteAllDrinksData() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(ContractOrderedDrinks.OrderedDrinksEntry.DRINKS_TABLE_NAME, null, null);
        db.close();
    }
}
