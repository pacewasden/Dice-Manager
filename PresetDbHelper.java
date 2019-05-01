package com.fracturedscale.spectrar.dicemanager.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fracturedscale.spectrar.dicemanager.data.PresetDbContract.PresetEntry;

//Manages a local database for preset data.
public class PresetDbHelper extends SQLiteOpenHelper {

    //name of database
    public static final String DATABASE_NAME = "preset.db";

    //database version. increment by 1 if you make any changes to database.
    private static final int DATABASE_VERSION = 1;

    public PresetDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //creates table in Android file system if it doesn't already exist
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

       //SQL command for creating table
        final String SQL_CREATE_WEATHER_TABLE =

                "CREATE TABLE " + PresetEntry.TABLE_NAME + " (" +


                        PresetEntry._ID               + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                        PresetEntry.COLUMN_PRESET      + " TEXT"          +

                         ");";


        //executes the create table command
        sqLiteDatabase.execSQL(SQL_CREATE_WEATHER_TABLE);
    }

    //if table version is incremented, this runs
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PresetEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
