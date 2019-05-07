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


        String sorguTr = "SELECT * FROM tr_bolum b " +
                "INNER JOIN tr_fakulte f ON b.fakulte_id = f.fakulte_id " +
                "INNER JOIN tr_universite u ON f.uni_id = u.uni_id";
        //String sorguTr = "SELECT * FROM tr_bolum";
        Bilgi bilgi;
        openDB();

        //TODO: ORDER KONTROL ET
        sorguTr = "SELECT * FROM (" + sorguTr + " ORDER BY bolum_adi ASC) ORDER BY uni_adi ASC";
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
                bilgi.setBolum_turu(cTr.getString(cTr.getColumnIndex("bolum_turu")));
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

    public List<Bilgi> filtreCek(String[] deneme, int MaxPuan, int MinPuan){
        List<Bilgi> filtreList = new ArrayList<>();

        String universite = deneme[0];
        String bolum = deneme[1];
        String sehir = deneme[2];
        String siralamaMax = deneme[3];
        String siralamaMin = deneme[4];
        String bolumTuru = deneme[5];
        String puanTuru = deneme[6];
        String uni ="";

        String queryy = "SELECT * FROM tr_bolum b " +
                "INNER JOIN tr_fakulte f ON b.fakulte_id = f.fakulte_id " +
                "INNER JOIN tr_universite u ON f.uni_id = u.uni_id ";

        String[] secimArguman = {};     //.query() icin

        if (MaxPuan!=0) queryy += "WHERE taban_puani BETWEEN " + MinPuan + " AND " + MaxPuan;
        else queryy += "WHERE bolum_id > 0";
        
        if(universite.length()!=0) {          //null yerine "" koyduk. Bos text oyle geliyo
            queryy += " AND uni_adi = '" + universite + "'";
            //secimArguman += universite;
        }
        if(bolum.length()!=0) {
            queryy += " AND bolum_adi = '" + bolum + "'";
        }
        if(sehir.length()!=0) {
            queryy += " AND il_adi = '" + sehir + "'";
        }
        if(bolumTuru.length()!=0 && bolumTuru.length()!=4) {    //length=5 cunku spinnerdan hepsi 5 uzunlugunda geliyo
            queryy += " AND bolum_turu = '" + bolumTuru + "'";
        }
        if(puanTuru.length()!=0 && puanTuru.length()!=4) {
            queryy += " AND puan_turu = '" + puanTuru + "'";
        }
        if ((siralamaMax.length()!=0) && (siralamaMin.length()!=0) ) queryy += " AND basari_sirasi BETWEEN " + siralamaMin + " AND " + siralamaMax;
        else if ( (siralamaMax.length()!=0)&& (siralamaMin.length()!=0) ) queryy += " AND basari_sirasi = " + siralamaMin;
        else if ( (siralamaMax.length()!=0) && (siralamaMin.length()!=0) ) queryy += " AND basari_sirasi = " + siralamaMax;

        queryy = "SELECT * FROM (" + queryy + " ORDER BY bolum_adi ASC) ORDER BY uni_adi ASC";
        // querry += " ORDER BY taban_puani DESC"

        String qq = "SELECT * FROM tr_bolum b INNER JOIN tr_fakulte f ON b.fakulte_id = f.fakulte_id INNER JOIN tr_universite u ON f.uni_id = u.uni_id WHERE taban_puani BETWEEN 500 AND 600";
        openDB();
        Bilgi bilgi;

        Cursor crs = database.rawQuery(queryy, null);

        //rawQuery yerine query kullanip filtreleme kolaylastırabilir


        if (crs!=null && crs.getCount()!=0)
        {
            while (crs.moveToNext())
            {
                bilgi = new Bilgi();

                //TODO: taban puani string alinirsa sayiya gore siralama sikinti, sayi alinirsa "dolmadi"lar 0 geliyor.
                bilgi.setPr_kodu(crs.getInt(crs.getColumnIndex("bolum_id")));
                bilgi.setBolum(crs.getString(crs.getColumnIndex("bolum_adi")));
                bilgi.setDil(crs.getString(crs.getColumnIndex("dil")));
                bilgi.setBolum_turu(crs.getString(crs.getColumnIndex("bolum_turu")));
                bilgi.setTaban_puani(crs.getString(crs.getColumnIndex("taban_puani")));
                bilgi.setSiralama(crs.getInt(crs.getColumnIndex("basari_sirasi")));
                bilgi.setKontenjan(crs.getInt(crs.getColumnIndex("kontenjan")));
                bilgi.setUniversite(crs.getString(crs.getColumnIndex("uni_adi")));
                bilgi.setSehir(crs.getString(crs.getColumnIndex("il_adi")));
                bilgi.setFakulte(crs.getString(crs.getColumnIndex("fakulte_adi")));
                bilgi.setPuan_turu(crs.getString(crs.getColumnIndex("puan_turu")));

                filtreList.add(bilgi);
            }
            //if(filtreList.size(0)==) return hata kodu??
        }
        closeDB();
        

        return filtreList;
    }

    public List<Bilgi> meslekListeCek(String meslek){
        List<Bilgi> meslekListe = new ArrayList<>();

        String sorguSade = "SELECT * FROM tr_bolum b " +
                "INNER JOIN tr_fakulte f ON b.fakulte_id = f.fakulte_id " +
                "INNER JOIN tr_universite u ON f.uni_id = u.uni_id " +
                "WHERE bolum_adi" ;

        String meslekSorgu = "SELECT * FROM tr_bolum b " +
                "INNER JOIN tr_fakulte f ON b.fakulte_id = f.fakulte_id " +
                "INNER JOIN tr_universite u ON f.uni_id = u.uni_id " +
                "WHERE bolum_adi = '" + meslek + "'";

        if ("Eğitim Fakültesi".equalsIgnoreCase(meslek)){          //"Eğitim Fakültesi" == meslek
            meslek = " LIKE '%Öğretmenliği%'";
            meslekSorgu = sorguSade + meslek;
        }else if ("Temel Bilimler".equalsIgnoreCase(meslek)){
            meslek = " IN ('Fizik','Kimya','Matematik','Biyoloji')";
            meslekSorgu = sorguSade + meslek;
        }else if ("Dil ve Edebiyat Bölümleri".equalsIgnoreCase(meslek)){
            meslek = " LIKE '%Dili ve Edebiyatı%'";
            meslekSorgu = sorguSade + meslek;
        }


        Bilgi bilgi;
        openDB();
        Cursor ccc = database.rawQuery(meslekSorgu, null);
        if (ccc!=null && ccc.getCount()!=0)
        {
            while (ccc.moveToNext())
            {
                bilgi = new Bilgi();

                //TODO: taban puani string alinirsa sayiya gore siralama sikinti, sayi alinirsa "dolmadi"lar 0 geliyor.
                bilgi.setPr_kodu(ccc.getInt(ccc.getColumnIndex("bolum_id")));
                bilgi.setBolum(ccc.getString(ccc.getColumnIndex("bolum_adi")));
                bilgi.setDil(ccc.getString(ccc.getColumnIndex("dil")));
                bilgi.setBolum_turu(ccc.getString(ccc.getColumnIndex("bolum_turu")));
                bilgi.setTaban_puani(ccc.getString(ccc.getColumnIndex("taban_puani")));
                bilgi.setSiralama(ccc.getInt(ccc.getColumnIndex("basari_sirasi")));
                bilgi.setKontenjan(ccc.getInt(ccc.getColumnIndex("kontenjan")));
                bilgi.setUniversite(ccc.getString(ccc.getColumnIndex("uni_adi")));
                bilgi.setSehir(ccc.getString(ccc.getColumnIndex("il_adi")));
                bilgi.setFakulte(ccc.getString(ccc.getColumnIndex("fakulte_adi")));
                bilgi.setPuan_turu(ccc.getString(ccc.getColumnIndex("puan_turu")));

                meslekListe.add(bilgi);
            }
        }
        closeDB();

        return meslekListe;
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