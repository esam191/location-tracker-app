package com.example.locationfinderapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class LocationDatabase extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Location.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Location_Table";
    private static final String Column_ID = "ID";
    private static final String Column_Address = "Address";
    private static final String Column_Latitude = "Latitude";
    private static final String Column_Longitude = "Longitude";
    private SQLiteDatabase read_db = this.getReadableDatabase();
    private SQLiteDatabase write_db = this.getWritableDatabase();

    public LocationDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
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

    public void addLocation(String address, String latitude, String longitude) {
        ContentValues cv = new ContentValues();
        cv.put(Column_Address, address);
        cv.put(Column_Latitude, latitude);
        cv.put(Column_Longitude, longitude);
        long result = write_db.insert(TABLE_NAME,null,cv);
        if(result != -1){
            Toast.makeText(context,"Location Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteLocation(String id){
        long result = write_db.delete(TABLE_NAME, "ID = ?", new String[] {id});
        if(result != -1){
            Toast.makeText(context, "Deleted Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateLocation(String id, String address, String latitude, String longitude){
        ContentValues cv = new ContentValues();
        cv.put(Column_Address , address);
        cv.put(Column_Latitude , latitude);
        cv.put(Column_Longitude , longitude);
        long result = write_db.update (TABLE_NAME, cv , Column_ID +"=?", new String[] {id} );
        if(result != -1){
            Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor getData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = null;
        if (read_db != null) {
            cursor = read_db.rawQuery(query, null);
        }
        return cursor;
    }

    Cursor getSearchData(String t_address) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + Column_Address + " Like " + "'" +t_address + "%'";
        Cursor cursor = null;
        if (read_db != null) {
            cursor = read_db.rawQuery(query, null);
        }
        return cursor;
    }
}
