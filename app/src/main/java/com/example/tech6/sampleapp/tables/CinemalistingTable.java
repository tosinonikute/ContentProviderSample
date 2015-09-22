package com.example.tech6.sampleapp.tables;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by tech6 on 9/7/15.
 */
public class CinemalistingTable {
    // Database table
    public static final String TABLE_CINEMALISTING = "cinemalisting";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_IMAGEURL = "imageurl";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_STARRING = "starring";

    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_CINEMALISTING
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TITLE + " text not null, "
            + COLUMN_DESCRIPTION + " text not null, "
            + COLUMN_IMAGEURL + " text not null, "
            + COLUMN_CATEGORY + " text not null, "
            + COLUMN_STARRING + " text not null "
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        Log.w(CinemalistingTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_CINEMALISTING);
        onCreate(database);
    }
}
