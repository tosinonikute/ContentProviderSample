package com.example.tech6.sampleapp.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tech6.sampleapp.tables.CinemalistingTable;

/**
 * Created by tech6 on 9/7/15.
 */
public class CinemalistingDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "cinemalistingtable.db";
    private static final int DATABASE_VERSION = 1;

    public CinemalistingDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase database) {
        CinemalistingTable.onCreate(database);
    }

    // Method is called during an upgrade of the database,
    // e.g. if you increase the database version
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion,
                          int newVersion) {
        CinemalistingTable.onUpgrade(database, oldVersion, newVersion);
    }

}