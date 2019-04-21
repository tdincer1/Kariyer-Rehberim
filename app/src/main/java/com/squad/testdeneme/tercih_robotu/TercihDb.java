package com.squad.testdeneme.tercih_robotu;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.squad.testdeneme.MySQLiteHelper;

public class TercihDb {
    private static TercihDb INSTANCE;
    private static Context context;

    private static SQLiteDatabase database;
    private static MySQLiteHelper myhelper; //Veritabanina erisim icin MySQLiteHelper objesi olusturduk

    public TercihDb() {

        myhelper = new MySQLiteHelper(context);
    }

    public static TercihDb getInstance(Context context) {

        TercihDb.context = context;

        if(INSTANCE == null)
        {
            INSTANCE = new TercihDb();
        }
        return INSTANCE;
    }

    private void openDB() throws SQLException {

        database = myhelper.getWritableDatabase();
    }

    private void closeDB() {

        database.close();
    }

    
}
