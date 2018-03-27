package com.example.nagys.chatty.Classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nagys on 3/23/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Contacts.db";
    public static final String TABLE_NAME = "contacts_table";
    public static final String COL_1 = "UID";
    public static final String COL_2 = "DN";
    public static final String COL_3 = "PIC";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (UID TEXT PRIMARY KEY, DN TEXT, PIC TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String uid, String displayName, String picURL) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, uid);
        contentValues.put(COL_2, displayName);
        contentValues.put(COL_3, picURL);

        if(db.insert(TABLE_NAME, null, contentValues) == -1) {
            return  false;
        }

        return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME + ";", null);

        return res;
    }

    public void removeTable() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DROP TABLE " + TABLE_NAME + ";");
    }
}
