package com.ejoapps.m2d2_sub.orderingapp.database_preload;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ejoapps.m2d2_sub.orderingapp.containers.CateringNameAndTypeData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soundoffear on 24/12/2017.
 */

public class CateringBuilderDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "catering_database.db";

    public CateringBuilderDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        final String createTable = "CREATE TABLE " + ContractCateringBuilderDB.ContractCateringEntry.CATERING_TABLE_NAME +
                "(" + ContractCateringBuilderDB.ContractCateringEntry._ID + " INTEGER PRIMARY KEY, "
                + ContractCateringBuilderDB.ContractCateringEntry.CATERING_ITEM_NAME + " TEXT NOT NULL, "
                + ContractCateringBuilderDB.ContractCateringEntry.CATERING_SUB_1 + " TEXT NOT NULL, "
                + ContractCateringBuilderDB.ContractCateringEntry.CATERING_SUB_2 + " TEXT NOT NULL, "
                + ContractCateringBuilderDB.ContractCateringEntry.CATERING_SUB_3 + " TEXT NOT NULL, "
                + ContractCateringBuilderDB.ContractCateringEntry.CATERING_SUB_4 + " TEXT NOT NULL, "
                + ContractCateringBuilderDB.ContractCateringEntry.CATERING_PRICE + " TEXT NOT NULL)";
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ContractCateringBuilderDB.ContractCateringEntry.CATERING_TABLE_NAME);
        onCreate(db);
    }

    public void insertItemsIntoCateringDB(CateringNameAndTypeData cateringNameAndTypeData) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ContractCateringBuilderDB.ContractCateringEntry.CATERING_ITEM_NAME, cateringNameAndTypeData.getCateringName());
        cv.put(ContractCateringBuilderDB.ContractCateringEntry.CATERING_SUB_1, cateringNameAndTypeData.getSub1());
        cv.put(ContractCateringBuilderDB.ContractCateringEntry.CATERING_SUB_2, cateringNameAndTypeData.getSub2());
        cv.put(ContractCateringBuilderDB.ContractCateringEntry.CATERING_SUB_3, cateringNameAndTypeData.getSub3());
        cv.put(ContractCateringBuilderDB.ContractCateringEntry.CATERING_SUB_4, cateringNameAndTypeData.getSub4());
        cv.put(ContractCateringBuilderDB.ContractCateringEntry.CATERING_PRICE, cateringNameAndTypeData.getsCateringPrice());
        db.insert(ContractCateringBuilderDB.ContractCateringEntry.CATERING_TABLE_NAME, null, cv);

    }

    public void updateItemsInCateringDB(CateringNameAndTypeData cateringNameAndTypeData, int id) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ContractCateringBuilderDB.ContractCateringEntry.CATERING_ITEM_NAME, cateringNameAndTypeData.getCateringName());
        cv.put(ContractCateringBuilderDB.ContractCateringEntry.CATERING_SUB_1, cateringNameAndTypeData.getSub1());
        cv.put(ContractCateringBuilderDB.ContractCateringEntry.CATERING_SUB_2, cateringNameAndTypeData.getSub2());
        cv.put(ContractCateringBuilderDB.ContractCateringEntry.CATERING_SUB_3, cateringNameAndTypeData.getSub3());
        cv.put(ContractCateringBuilderDB.ContractCateringEntry.CATERING_SUB_4, cateringNameAndTypeData.getSub4());
        cv.put(ContractCateringBuilderDB.ContractCateringEntry.CATERING_PRICE, cateringNameAndTypeData.getsCateringPrice());
        db.update(ContractCateringBuilderDB.ContractCateringEntry.CATERING_TABLE_NAME, cv, ContractCateringBuilderDB.ContractCateringEntry._ID + "=" + id, null);
    }

    public List<CateringNameAndTypeData> getAllCateringData() {

        List<CateringNameAndTypeData> cateringNameAndTypeDataList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(ContractCateringBuilderDB.ContractCateringEntry.CATERING_TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        if(cursor != null) {
            if(cursor.moveToFirst()) {
                do {
                    String sName = cursor.getString(cursor.getColumnIndex(ContractCateringBuilderDB.ContractCateringEntry.CATERING_ITEM_NAME));
                    String sSub1 = cursor.getString(cursor.getColumnIndex(ContractCateringBuilderDB.ContractCateringEntry.CATERING_SUB_1));
                    String sSub2 = cursor.getString(cursor.getColumnIndex(ContractCateringBuilderDB.ContractCateringEntry.CATERING_SUB_2));
                    String sSub3 = cursor.getString(cursor.getColumnIndex(ContractCateringBuilderDB.ContractCateringEntry.CATERING_SUB_3));
                    String sSub4 = cursor.getString(cursor.getColumnIndex(ContractCateringBuilderDB.ContractCateringEntry.CATERING_SUB_4));
                    String price = cursor.getString(cursor.getColumnIndex(ContractCateringBuilderDB.ContractCateringEntry.CATERING_PRICE));
                    CateringNameAndTypeData cateringNameAndTypeData = new CateringNameAndTypeData(sName,
                            sSub1,
                            sSub2,
                            sSub3,
                            sSub4,
                            price);
                    cateringNameAndTypeDataList.add(cateringNameAndTypeData);
                } while (cursor.moveToNext());
            }
        }

        db.close();

        return cateringNameAndTypeDataList;
    }

    public void deleteAllCateringRecords() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(ContractCateringBuilderDB.ContractCateringEntry.CATERING_TABLE_NAME, null, null);
        db.close();
    }
}
