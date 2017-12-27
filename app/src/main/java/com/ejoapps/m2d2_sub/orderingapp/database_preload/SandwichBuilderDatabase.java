package com.ejoapps.m2d2_sub.orderingapp.database_preload;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ejoapps.m2d2_sub.orderingapp.containers.BreadTypesData;
import com.ejoapps.m2d2_sub.orderingapp.containers.PaidAddOnsData;
import com.ejoapps.m2d2_sub.orderingapp.containers.SandwichNameData;
import com.ejoapps.m2d2_sub.orderingapp.containers.SauceData;
import com.ejoapps.m2d2_sub.orderingapp.containers.VegetablesData;

import java.util.ArrayList;
import java.util.List;

public class SandwichBuilderDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "SandwichDetails";
    private static SQLiteDatabase mDatabase;

    public SandwichBuilderDatabase (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 1st database - SANDWICH NAMES
        final String CREATE_TABLE_SANDWICH_NAMES = "CREATE TABLE " + ContractSandwichBuilder.SandwichNames.TABLE_NAME_SANDWICH_NAMES
                + "(" + ContractSandwichBuilder.SandwichNames.SANDWICH_NAME + " TEXT NOT NULL, "
                + ContractSandwichBuilder.SandwichNames.SANDWICH_DESCRIPTION + " TEXT NOT NULL, "
                + ContractSandwichBuilder.SandwichNames.SANDWICH_PRICE + " TEXT NOT NULL)";

        db.execSQL(CREATE_TABLE_SANDWICH_NAMES);

        // 2nd database - BREAD TYPES
        final String CREATE_TABLE_BREAD_TYPES = "CREATE TABLE " + ContractSandwichBuilder.BreadTypes.TABLE_NAME_BREAD_TYPES
                + "(" + ContractSandwichBuilder.BreadTypes.BREAD_TYPE + " TEXT NOT NULL)";
        db.execSQL(CREATE_TABLE_BREAD_TYPES);

        // 3rd database - PAID ADD ONS
        final String CREATE_TABLE_PAID_ADD_ONS = "CREATE TABLE " + ContractSandwichBuilder.PaidAddOns.TABLE_NAME_PAID_ADD
                + "(" + ContractSandwichBuilder.PaidAddOns.ADD_ON_NAME + " TEXT NOT NULL,"
                + ContractSandwichBuilder.PaidAddOns.ADD_ON_PRICE + " TEXT NOT NULL)";
        db.execSQL(CREATE_TABLE_PAID_ADD_ONS);

        // 4th database - Vegetables
        final String CREATE_TABLE_VEGE = "CREATE TABLE " + ContractSandwichBuilder.Vegetables.TABLE_NAME_VEGE
                + "(" + ContractSandwichBuilder.Vegetables.VEGE_NAME + " TEXT NOT NULL)";
        db.execSQL(CREATE_TABLE_VEGE);

        // 5th database - SAUCES
        final String CREATE_TABLE_SAUCES = "CREATE TABLE " + ContractSandwichBuilder.Sauces.TABLE_NAME_SAUCE
                + "(" + ContractSandwichBuilder.Sauces.SAUCE_NAME + " TEXT NOT NULL)";
        db.execSQL(CREATE_TABLE_SAUCES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ContractSandwichBuilder.SandwichNames.TABLE_NAME_SANDWICH_NAMES);
        db.execSQL("DROP TABLE IF EXISTS " + ContractSandwichBuilder.BreadTypes.TABLE_NAME_BREAD_TYPES);
        db.execSQL("DROP TABLE IF EXISTS " + ContractSandwichBuilder.PaidAddOns.TABLE_NAME_PAID_ADD);
        db.execSQL("DROP TABLE IF EXISTS " + ContractSandwichBuilder.Vegetables.TABLE_NAME_VEGE);
        db.execSQL("DROP TABLE IF EXISTS " + ContractSandwichBuilder.Sauces.TABLE_NAME_SAUCE);
        onCreate(db);
    }

    // Insert to each database

    // 1st DB

    public void insertIntoSandwichNamesDB(SandwichNameData sandwichNameData) {

        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ContractSandwichBuilder.SandwichNames.SANDWICH_NAME, sandwichNameData.getsName());
        contentValues.put(ContractSandwichBuilder.SandwichNames.SANDWICH_DESCRIPTION, sandwichNameData.getsDescription());
        contentValues.put(ContractSandwichBuilder.SandwichNames.SANDWICH_PRICE, sandwichNameData.getsPrice());
        database.insert(ContractSandwichBuilder.SandwichNames.TABLE_NAME_SANDWICH_NAMES, null, contentValues);

    }

    // 2nd DB

    public void insertIntoBreadTypesDB(BreadTypesData breadTypesData) {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ContractSandwichBuilder.BreadTypes.BREAD_TYPE, breadTypesData.getBreadType());

        database.insert(ContractSandwichBuilder.BreadTypes.TABLE_NAME_BREAD_TYPES, null, contentValues);

    }

    // 3rd DB
    public void insertIntoPaidAddOnsDB(PaidAddOnsData paidAddOnsData) {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ContractSandwichBuilder.PaidAddOns.ADD_ON_NAME, paidAddOnsData.getPaidAddOnName());
        contentValues.put(ContractSandwichBuilder.PaidAddOns.ADD_ON_PRICE, paidAddOnsData.getPaidAddOnPrice());

        database.insert(ContractSandwichBuilder.PaidAddOns.TABLE_NAME_PAID_ADD, null, contentValues);
    }

    // 4th DB
    public void insertIntoVegetablesDB(VegetablesData vegetablesData) {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ContractSandwichBuilder.Vegetables.VEGE_NAME, vegetablesData.getVegetables());

        database.insert(ContractSandwichBuilder.Vegetables.TABLE_NAME_VEGE, null, contentValues);

    }

    // 5th DB

    public void insertIntoSaucesDB(SauceData sauceData) {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ContractSandwichBuilder.Sauces.SAUCE_NAME, sauceData.getSauces());

        database.insert(ContractSandwichBuilder.Sauces.TABLE_NAME_SAUCE, null, contentValues);
    }

    public List<SandwichNameData> getSandwichNameAllData(String table_name) {

        List<SandwichNameData> sandwichNameDataList = new ArrayList<>();
        SandwichNameData sandwichNameData;
        SQLiteDatabase database = getReadableDatabase();

        Cursor cursor = database.query(table_name, null, null, null, null, null, null);

        if(cursor != null) {
            if(cursor.moveToFirst()) {
                do {
                    String sandwichName = cursor.getString(cursor.getColumnIndex(ContractSandwichBuilder.SandwichNames.SANDWICH_NAME));
                    String sandwichDescription = cursor.getString(cursor.getColumnIndex(ContractSandwichBuilder.SandwichNames.SANDWICH_DESCRIPTION));
                    String sandwichPrice = cursor.getString(cursor.getColumnIndex(ContractSandwichBuilder.SandwichNames.SANDWICH_PRICE));
                    sandwichNameData = new SandwichNameData(sandwichName, sandwichDescription, sandwichPrice);
                    sandwichNameDataList.add(sandwichNameData);
                } while (cursor.moveToNext());
            }
        }

        database.close();

        return sandwichNameDataList;
    }

    public List<BreadTypesData> getBreadTypeAllData() {
        List<BreadTypesData> breadTypesDataList = new ArrayList<>();
        BreadTypesData breadTypesData;
        SQLiteDatabase breadDB = getReadableDatabase();

        Cursor cursor = breadDB.query(ContractSandwichBuilder.BreadTypes.TABLE_NAME_BREAD_TYPES,
                null,null,null,null,null,null);

        if(cursor.moveToFirst()) {
            do {
                String breadType = cursor.getString(cursor.getColumnIndex(ContractSandwichBuilder.BreadTypes.BREAD_TYPE));
                breadTypesData = new BreadTypesData(breadType);
                breadTypesDataList.add(breadTypesData);
            } while (cursor.moveToNext());
        }

        breadDB.close();

        return breadTypesDataList;
    }

    public List<PaidAddOnsData> getPaidAddOnsAllData() {
        List<PaidAddOnsData> paidAddOnsDataList = new ArrayList<>();
        PaidAddOnsData paidAddOnsData;
        SQLiteDatabase paidAddOnsDB = getReadableDatabase();

        Cursor cursor = paidAddOnsDB.query(ContractSandwichBuilder.PaidAddOns.TABLE_NAME_PAID_ADD,
                null, null, null, null, null, null);
        if(cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String addOnName = cursor.getString(cursor.getColumnIndex(ContractSandwichBuilder.PaidAddOns.ADD_ON_NAME));
                    String addOnPrice = cursor.getString(cursor.getColumnIndex(ContractSandwichBuilder.PaidAddOns.ADD_ON_PRICE));
                    paidAddOnsData = new PaidAddOnsData(addOnName, addOnPrice);
                    paidAddOnsDataList.add(paidAddOnsData);
                } while (cursor.moveToNext());
            }
        }

        paidAddOnsDB.close();

        return paidAddOnsDataList;
    }

    public List<VegetablesData> getVegeDataAll() {
        List<VegetablesData> vegetablesDataList = new ArrayList<>();
        VegetablesData vegetablesData;
        SQLiteDatabase vegeDB = getReadableDatabase();

        Cursor cursor = vegeDB.query(ContractSandwichBuilder.Vegetables.TABLE_NAME_VEGE,
                null, null, null, null, null, null);
        if(cursor != null) {
            if(cursor.moveToFirst()) {
                do {
                    String vegetableName = cursor.getString(cursor.getColumnIndex(ContractSandwichBuilder.Vegetables.VEGE_NAME));
                    vegetablesData = new VegetablesData(vegetableName);
                    vegetablesDataList.add(vegetablesData);
                } while (cursor.moveToNext());
            }
        }

        vegeDB.close();

        return vegetablesDataList;
    }

    public List<SauceData> getSauceDataAll() {
        List<SauceData> sauceDataList = new ArrayList<>();
        SauceData sauceData;
        SQLiteDatabase sauceDB = getReadableDatabase();

        Cursor cursor = sauceDB.query(ContractSandwichBuilder.Sauces.TABLE_NAME_SAUCE,
                null, null, null, null, null, null);
        if(cursor != null) {
            if(cursor.moveToFirst()) {
                do {
                    String sauceName = cursor.getString(cursor.getColumnIndex(ContractSandwichBuilder.Sauces.SAUCE_NAME));
                    sauceData = new SauceData(sauceName);
                    sauceDataList.add(sauceData);
                } while (cursor.moveToNext());
            }
        }

        sauceDB.close();

        return sauceDataList;
    }

    public void deleteAllRecordsFromDB(String databaseName) {

        SQLiteDatabase db = getWritableDatabase();
        if(dbExists(databaseName, db.isOpen())) {
            db = getWritableDatabase();
            db.delete(databaseName, null, null);
            db.close();
        }
    }

    private boolean dbExists(String tableName, boolean isOpen) {
        if(isOpen) {
            if(mDatabase == null || mDatabase.isOpen()) {
                mDatabase = getReadableDatabase();
            }

            if(!mDatabase.isReadOnly()) {
                mDatabase.close();
                mDatabase = getReadableDatabase();
            }
        }

        String rawQuery = "select DISTINCT tbl_name from sqlite_master where tbl_name = '" + tableName + "'";

        Cursor cursor = mDatabase.rawQuery(rawQuery, null);
        if(cursor != null) {
            if(cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }

        return false;
    }

}
