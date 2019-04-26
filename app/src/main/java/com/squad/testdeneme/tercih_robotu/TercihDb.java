package com.squad.testdeneme.tercih_robotu;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.squad.testdeneme.MySQLiteHelper;

import java.util.ArrayList;
import java.util.List;

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

    public List<Bilgi> verileriCek(){           //Veritabanından
        List<Bilgi> bilgiList = new ArrayList<>();

        openDB();
        String sorguTr = "SELECT * FROM tr_bolum";
        Bilgi bilgi;

        Cursor cTr = database.rawQuery(sorguTr, null);

        //rawQuery yerine query kullanip filtreleme kolaylastırabilir
        if (cTr!=null && cTr.getCount()!=0)
        {
            while (cTr.moveToNext())
            {
                bilgi = new Bilgi();

                //TODO: taban puani string alinirsa sayiya gore siralama sikinti, sayi alinirsa "dolmadi"lar 0 geliyor.
                bilgi.setPr_kodu(cTr.getInt(cTr.getColumnIndex("bolum_id")));
                bilgi.setBolum(cTr.getString(cTr.getColumnIndex("bolum_adi")));
                bilgi.setDil(cTr.getString(cTr.getColumnIndex("dil")));
                bilgi.setBolum_turu(cTr.getString(cTr.getColumnIndex("burs")));
                bilgi.setTaban_puani(cTr.getDouble(cTr.getColumnIndex("taban_puani")));
                bilgi.setSiralama(cTr.getInt(cTr.getColumnIndex("basari_sirasi")));
                bilgi.setKontenjan(cTr.getInt(cTr.getColumnIndex("kontenjan")));

                bilgiList.add(bilgi);
            }
        }
        closeDB();
        return bilgiList;
    }

    public String[] bolumleriCek(){
        String sorguBolum = "SELECT * FROM tr_bolum";
        int n = 0;
        openDB();

        Cursor cc = database.rawQuery(sorguBolum, null);
        String[] bolum = new String[cc.getCount()];
        if (cc!=null && cc.getCount()!=0)
        {
            while (cc.moveToNext())
            {
                bolum[n] = cc.getString(cc.getColumnIndex("bolum_adi"));
                n++;
            }
        }

        return bolum;
    }

    public String[] uniCek(){
        String sorguUni = "SELECT * FROM tr_universite";
        int z = 0;
        openDB();

        Cursor cc = database.rawQuery(sorguUni, null);
        String[] universite = new String[cc.getCount()];

        if (cc!=null && cc.getCount()!=0)
        {
            while (cc.moveToNext())
            {
                universite[z] = cc.getString(cc.getColumnIndex("uni_adi"));
                z++;
            }
        }

        return universite;
    }

    
}
