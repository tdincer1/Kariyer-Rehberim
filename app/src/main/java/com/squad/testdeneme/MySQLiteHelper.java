package com.squad.testdeneme;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class MySQLiteHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "veritabani.db";
    private static final int DATABASE_VERSION = 1;

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void setForcedUpgrade() {
        super.setForcedUpgrade();
    }

    /*
    @Override
    public void setForcedUpgrade(int version) {
        super.setForcedUpgrade(version);
    }
    */
}
