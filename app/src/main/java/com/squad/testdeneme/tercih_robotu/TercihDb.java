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
        String sorguTr = "SELECT  b.bolum_id , " +
                "u.il_adi, " +
                "u.uni_adi, " +
                "f.fakulte_adi, " +
                "b.bolum_adi, " +
                "dil, " +
                "burs, " +
                "puan_turu, " +
                "taban_puani, " +
                "basari_sirasi, " +
                "kontenjan " +
                "FROM tr_bolum b " +
                "INNER JOIN tr_fakulte f ON b.fakulte_id = f.fakulte_id " +
                "INNER JOIN tr_universite u ON f.uni_id = u.uni_id";
        //String sorguTr = "SELECT * FROM tr_bolum";
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
                bilgi.setTaban_puani(cTr.getString(cTr.getColumnIndex("taban_puani")));
                bilgi.setSiralama(cTr.getInt(cTr.getColumnIndex("basari_sirasi")));
                bilgi.setKontenjan(cTr.getInt(cTr.getColumnIndex("kontenjan")));
                bilgi.setUniversite(cTr.getString(cTr.getColumnIndex("uni_adi")));
                bilgi.setSehir(cTr.getString(cTr.getColumnIndex("il_adi")));
                bilgi.setFakulte(cTr.getString(cTr.getColumnIndex("fakulte_adi")));
                bilgi.setPuan_turu(cTr.getString(cTr.getColumnIndex("puan_turu")));

                bilgiList.add(bilgi);
            }
        }
        closeDB();
        return bilgiList;
    }

    public List<Bilgi> filtreCek(){
        List<Bilgi> filtreList = new ArrayList<>();

        String universite = null;
        String bolum = null;
        String sehir = null;
        String siralamaMax = null;
        String siralamaMin = null;
        int maximumPuan = 0;
        int minimumPuan = 0;
        String bolumTuru = null;
        String puanTuru = null;

        String secim = null;
        String queryy = "SELECT  b.bolum_id , " +
                "u.il_adi, " +
                "u.uni_adi, " +
                "f.fakulte_adi, " +
                "b.bolum_adi, " +
                "dil, " +
                "burs, " +
                "puan_turu, " +
                "taban_puani, " +
                "basari_sirasi, " +
                "kontenjan " +
                "FROM tr_bolum b " +
                "INNER JOIN tr_fakulte f ON b.fakulte_id = f.fakulte_id " +
                "INNER JOIN tr_universite u ON f.uni_id = u.uni_id ";
        String[] secimArguman = {};

        if (maximumPuan!=0 && minimumPuan!=0) queryy += "WHERE taban_puani BETWEEN " + minimumPuan + " AND " + maximumPuan;
        else queryy += "WHERE";
        
        if(universite != null) {
            queryy += " AND uni_adi = " + universite;
            //secimArguman += universite;
        }
        if(sehir != null) {
            queryy += " AND il_adi = " + sehir;
        }
        if(bolumTuru != null) {
            queryy += " AND burs = " + bolumTuru;
        }
        if(puanTuru != null) {
            queryy += " AND puan_turu = " + puanTuru;
        }
        if (siralamaMax!=null && siralamaMin!=null) queryy += "AND basari_sirasi BETWEEN " + siralamaMin + " AND " + siralamaMax;
        else if (siralamaMax==null && siralamaMin!=null) queryy += "AND basari_sirasi = " + siralamaMin;
        else if (siralamaMax!=null && siralamaMin==null) queryy += "AND basari_sirasi = " + siralamaMax;



        

        return filtreList;
    }

    public String[] bolumCek(){
        String sorguBolum = "SELECT DISTINCT bolum_adi FROM tr_bolum";
        int b = 0;
        openDB();

        Cursor cc = database.rawQuery(sorguBolum, null);
        String[] bolum = new String[cc.getCount()];
        if (cc!=null && cc.getCount()!=0)
        {
            while (cc.moveToNext())
            {
                bolum[b] = cc.getString(cc.getColumnIndex("bolum_adi"));
                b++;
            }
        }

        return bolum;
    }

    public String[] uniCek(){
        String sorguUni = "SELECT * FROM tr_universite";
        int u = 0;
        openDB();

        Cursor cc = database.rawQuery(sorguUni, null);
        String[] universite = new String[cc.getCount()];

        if (cc!=null && cc.getCount()!=0)
        {
            while (cc.moveToNext())
            {
                universite[u] = cc.getString(cc.getColumnIndex("uni_adi"));
                u++;
            }
        }
        return universite;
    }

    public String[] sehirCek(){
        String sorguSehir = "SELECT DISTINCT il_adi FROM tr_universite";
        int s = 0;
        openDB();

        Cursor cs = database.rawQuery(sorguSehir, null);
        String[] sehir = new String[cs.getCount()];

        if (cs!=null && cs.getCount()!=0)
        {
            while (cs.moveToNext())
            {
                sehir[s] = cs.getString(cs.getColumnIndex("il_adi"));
                s++;
            }
        }
        return sehir;
    }
    
}
