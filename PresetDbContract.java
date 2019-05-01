package com.fracturedscale.spectrar.dicemanager.data;

import android.provider.BaseColumns;


//  Defines table and column names for the database. This class keeps the code organized.

public class PresetDbContract {

    // Inner class that defines the table contents of the preset table
    public static final class PresetEntry implements BaseColumns {
        //preset table name
        public static final String TABLE_NAME = "preset";

        //name of id column
        public static final String COLUMN_PRESET_ID = "preset_id";

        //name of preset column
        public static final String COLUMN_PRESET = "preset_object";

    }
}
