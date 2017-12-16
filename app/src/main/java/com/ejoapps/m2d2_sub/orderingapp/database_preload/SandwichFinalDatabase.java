package com.ejoapps.m2d2_sub.orderingapp.database_preload;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ejoapps.m2d2_sub.orderingapp.containers.FinalSandwichData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soundoffear on 15/12/2017.
 */

public class SandwichFinalDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "final_sandwich_data";

    public SandwichFinalDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String TABLE_CREATE = "CREATE TABLE "
                + ContractFinalSandwich.FinalSandwichEntry.TABLE_FINAL_SANDWICH
                + "(" + ContractFinalSandwich.FinalSandwichEntry.SUB_TAG + " TEXT NOT NULL, "
                + ContractFinalSandwich.FinalSandwichEntry.SANDWICH_NAME + " TEXT NOT NULL, "
                + ContractFinalSandwich.FinalSandwichEntry.BREAD_TYPE + " TEXT NOT NULL, "
                + ContractFinalSandwich.FinalSandwichEntry.PAID_ADD_ONS + " TEXT NOT NULL, "
                + ContractFinalSandwich.FinalSandwichEntry.VEGETABLES + " TEXT NOT NULL, "
                + ContractFinalSandwich.FinalSandwichEntry.SAUCES + " TEXT NOT NULL, "
                + ContractFinalSandwich.FinalSandwichEntry.FINAL_PRICE_FOR_SUB + " TEXT NOT NULL)";
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ContractFinalSandwich.FinalSandwichEntry.TABLE_FINAL_SANDWICH);
        db.close();
    }

    public void insertDataIntoFinalSubDB(FinalSandwichData finalSandwichData) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ContractFinalSandwich.FinalSandwichEntry.SUB_TAG, finalSandwichData.getSubTag());
        contentValues.put(ContractFinalSandwich.FinalSandwichEntry.SANDWICH_NAME, finalSandwichData.getSubName());
        contentValues.put(ContractFinalSandwich.FinalSandwichEntry.BREAD_TYPE, finalSandwichData.getSubBread());
        contentValues.put(ContractFinalSandwich.FinalSandwichEntry.PAID_ADD_ONS, finalSandwichData.getSubPaid());
        contentValues.put(ContractFinalSandwich.FinalSandwichEntry.VEGETABLES, finalSandwichData.getSubVege());
        contentValues.put(ContractFinalSandwich.FinalSandwichEntry.SAUCES, finalSandwichData.getSubSauce());
        contentValues.put(ContractFinalSandwich.FinalSandwichEntry.FINAL_PRICE_FOR_SUB, finalSandwichData.getSubPrice());

        sqLiteDatabase.insert(ContractFinalSandwich.FinalSandwichEntry.TABLE_FINAL_SANDWICH, null, contentValues);
    }

    public List<FinalSandwichData> getAllFinalSubData() {
        List<FinalSandwichData> finalSandwichDataList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(ContractFinalSandwich.FinalSandwichEntry.TABLE_FINAL_SANDWICH, null,null,null,null,null,null);

        if(cursor != null) {
            if(cursor.moveToFirst()) {
                do {
                    String sTag = cursor.getString(cursor.getColumnIndex(ContractFinalSandwich.FinalSandwichEntry.SUB_TAG));
                    String sName = cursor.getString(cursor.getColumnIndex(ContractFinalSandwich.FinalSandwichEntry.TABLE_FINAL_SANDWICH));
                    String sBread = cursor.getString(cursor.getColumnIndex(ContractFinalSandwich.FinalSandwichEntry.BREAD_TYPE));
                    String sPaid = cursor.getString(cursor.getColumnIndex(ContractFinalSandwich.FinalSandwichEntry.PAID_ADD_ONS));
                    String sVege = cursor.getString(cursor.getColumnIndex(ContractFinalSandwich.FinalSandwichEntry.VEGETABLES));
                    String sSauce = cursor.getString(cursor.getColumnIndex(ContractFinalSandwich.FinalSandwichEntry.SAUCES));
                    String sPrice = cursor.getString(cursor.getColumnIndex(ContractFinalSandwich.FinalSandwichEntry.FINAL_PRICE_FOR_SUB));
                    FinalSandwichData finalSandwichData = new FinalSandwichData(sTag, sName, sBread, sPaid, sVege,sSauce,sPrice);
                    finalSandwichDataList.add(finalSandwichData);
                } while (cursor.moveToNext());
            }
        }

        cursor.close();

        return finalSandwichDataList;

    }

}
