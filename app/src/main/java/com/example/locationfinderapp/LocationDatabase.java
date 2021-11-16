package com.example.locationfinderapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class LocationDatabase extends SQLiteOpenHelper {
    //private Context context;
    private static final String DATABASE_NAME = "Location.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Location_Table";
    private static final String Column_ID = "ID";
    private static final String Column_Address = "Address";
    private static final String Column_Latitude = "Latitude";
    private static final String Column_Longitude = "Longitude";

    public LocationDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + Column_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Column_Address + " TEXT," +
                Column_Latitude + " TEXT," +
                Column_Longitude + " TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}
