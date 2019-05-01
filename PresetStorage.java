package com.fracturedscale.spectrar.dicemanager.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;


import com.fracturedscale.spectrar.dicemanager.Presets;
import com.google.gson.Gson;


import java.util.ArrayList;


public class PresetStorage {

    private static ArrayList<Presets> PresetList = new ArrayList<Presets>();
    private static ArrayList<Integer> PresetIds = new ArrayList<Integer>();

    /**
     * Loads list from database into the arraylist
     * runs at app startup
     * @param context will most often just be the name of the class that's currently running+".this"
     *                e.g. MainActivity.this
     */
    public static void loadList(Context context){
        PresetList.clear();
        PresetIds.clear();
        //establish database connection and get
        //readable database
        PresetDbHelper dbHelper = new PresetDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        //specify which columns want to use
        String[] projection = {
                BaseColumns._ID,
                PresetDbContract.PresetEntry.COLUMN_PRESET
        };

//        // How you want the results sorted in the resulting Cursor

        Cursor cursor = db.query(
                PresetDbContract.PresetEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        Gson gson = new Gson();

        if (cursor.moveToFirst()) {
            do {
                String gsonPreset = cursor.getString(1);
                PresetList.add(gson.fromJson(gsonPreset, Presets.class));

                PresetIds.add(cursor.getInt(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        dbHelper.close();
    }

    /**
     * replace preset in ArrayList and database table at indicated index
     * @param preset the new preset you want to replace the old one.
     * @param arrayListPosition the position of the preset you want to replace in the ArrayList
     * @param context
     */
    public static void updatePreset(Presets preset, int arrayListPosition, Context context){
        PresetList.set(arrayListPosition, preset);

        //convert object ot GSON for database
        Gson gson = new Gson();
        String toStoreObject = gson.toJson(preset, Presets.class);

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(PresetDbContract.PresetEntry.COLUMN_PRESET, toStoreObject);
        //establish database connection
        PresetDbHelper dbHelper = new PresetDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = BaseColumns._ID + "="+PresetIds.get(arrayListPosition);
        //String[] selectionArgs = { toStoreObject };

        int count = db.update(
                PresetDbContract.PresetEntry.TABLE_NAME,
                values,
                selection,
                null);


    }

    /**
     * remove preset in ArrayList and database table at indicated PresetList index
     * @param index the position in the ArrayList of the preset you want to remove
     * @param context
     * @return boolean value that tells you whether the removal was successful from the database
     * and the ArrayList. One won't happen without the other
     */
    public static boolean removePreset(int index, Context context){
        //establish database connection and get
        //writable database
        PresetDbHelper dbHelper = new PresetDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        boolean isSuccessful = false;

        if(index<= PresetList.size()) {
             isSuccessful = db.delete(PresetDbContract.PresetEntry.TABLE_NAME, BaseColumns._ID + "=" +
                    PresetIds.get(index), null) > 0;
            PresetList.remove(index);
            PresetIds.remove(index);
        }
        dbHelper.close();
        db.close();
        return isSuccessful;
    }

    /**
     * add preset to end of ArrayList and end of database table
     * @param preset the preset you want to add to the ArrayList and table
     * @param context
     */
    public static void addPreset(Presets preset, Context context){
        PresetDbHelper dbHelper = new PresetDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Gson gson = new Gson();
        String toStoreObject = gson.toJson(preset, Presets.class);

        ContentValues values = new ContentValues();
        values.put(PresetDbContract.PresetEntry.COLUMN_PRESET, toStoreObject);

        db.insert(PresetDbContract.PresetEntry.TABLE_NAME,
                null,
                values);

        PresetList.add(preset);

        String[] projection = {
                BaseColumns._ID,
                PresetDbContract.PresetEntry.COLUMN_PRESET
        };
        Cursor cursor = db.query(
                PresetDbContract.PresetEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );
        cursor.moveToLast();
        PresetIds.add(cursor.getInt(0));

        cursor.close();
        db.close();
        dbHelper.close();
    }

    //TODO consider returning iterable so that writing power is restricted to the methods of this
    //class since it could be dangerous to let the list and the table go out of sync
    /**
     * returns ArrayList
     * @return
     */
    public static ArrayList<Presets> getPresetList() {
        return PresetList;
    }
}