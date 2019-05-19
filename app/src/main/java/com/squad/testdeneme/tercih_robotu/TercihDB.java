package com.squad.testdeneme.tercih_robotu;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.squad.testdeneme.MySQLiteHelper;

import java.util.ArrayList;
import java.util.List;

public class TercihDB {                     //Tercih Robotumuzun veritabani erisim sayfasi
    private static TercihDB INSTANCE;
    private static Context context;

    private static SQLiteDatabase database;
    private static MySQLiteHelper myhelper; //Veritabanina erisim icin MySQLiteHelper objesi olusturduk

    public TercihDB() {

        myhelper = new MySQLiteHelper(context);
    }

    public static TercihDB getInstance(Context context) {

        TercihDB.context = context;

        if(INSTANCE == null)
        {
            INSTANCE = new TercihDB();
        }
        return INSTANCE;
    }

    private void openDB() throws SQLException {

        database = myhelper.getWritableDatabase();
    }

    private void closeDB() {

        database.close();
    }

    public List<Bilgi> verileriCek(){           //Veritabanından tum verileri ceken metod
        List<Bilgi> bilgiList = new ArrayList<>();

        //Sqlite sorgumuzu tanımladık.
        String sorguTr = "SELECT * FROM tr_bolum b " +
                "INNER JOIN tr_fakulte f ON b.fakulte_id = f.fakulte_id " +
                "INNER JOIN tr_universite u ON f.uni_id = u.uni_id";
        Bilgi bilgi;
        openDB();   //Veritabani baglanti ac

        //TODO: ORDER KONTROL ET
        sorguTr = sorguTr + " ORDER BY CAST(taban_puani AS INTEGER) DESC";


        //Cursor yardımıyla db'den verileri alıp Question nesnemize yazıyoruz
        Cursor cTr = database.rawQuery(sorguTr, null);

        if (cTr!=null && cTr.getCount()!=0)
        {
            while (cTr.moveToNext())
            {
                //gelen veriyi bilgi nesnesine yaz(set et).
                bilgi = new Bilgi();

                bilgi.setPr_kodu(cTr.getInt(cTr.getColumnIndex("bolum_id")));
                bilgi.setBolum(cTr.getString(cTr.getColumnIndex("bolum_adi")));
                bilgi.setDil(cTr.getString(cTr.getColumnIndex("dil")));
                bilgi.setBolum_turu(cTr.getString(cTr.getColumnIndex("bolum_turu")));
                bilgi.setTaban_puani(cTr.getString(cTr.getColumnIndex("taban_puani")));
                bilgi.setSiralama(cTr.getString(cTr.getColumnIndex("basari_sirasi")));
                bilgi.setKontenjan(cTr.getInt(cTr.getColumnIndex("kontenjan")));
                bilgi.setUniversite(cTr.getString(cTr.getColumnIndex("uni_adi")));
                bilgi.setSehir(cTr.getString(cTr.getColumnIndex("il_adi")));
                bilgi.setFakulte(cTr.getString(cTr.getColumnIndex("fakulte_adi")));
                bilgi.setPuan_turu(cTr.getString(cTr.getColumnIndex("puan_turu")));

                bilgiList.add(bilgi);       //cekilen verileri arrayliste koy
            }
        }
        closeDB();                          //islem bitince veritabanini kapat.
        return bilgiList;                   //array listesini dondur
    }

    public List<Bilgi> filtreCek(Bilgi bilgiAl){

        //TercihFiltre sayfasinda filtreleme yaptigimizda verileri, veritabanindan ceken metodumuz

        List<Bilgi> filtreList = new ArrayList<>();

        //Öncelikle, metodumuza yollanan nesneden, filtre verilerimizi alalım.
        String universite = bilgiAl.getUniversite();
        String bolum = bilgiAl.getBolum();
        String sehir = bilgiAl.getSehir();
        String siralamaMax = bilgiAl.getMaxSiralama();
        String siralamaMin = bilgiAl.getMinSiralama();
        String bolumTuru = bilgiAl.getBolum_turu();
        String puanTuru = bilgiAl.getPuan_turu();
        int MaxPuan = bilgiAl.getMaxPuan();
        int MinPuan = bilgiAl.getMinPuan();

        //Sqlite sorgumuzu tanımladık.
        String queryy = "SELECT * FROM tr_bolum b " +
                "INNER JOIN tr_fakulte f ON b.fakulte_id = f.fakulte_id " +
                "INNER JOIN tr_universite u ON f.uni_id = u.uni_id";



        //Filtreleme kisimlari dolduruldugunda veritabanı sorgumuza eklemeler yapılan kisim
        if (MaxPuan!=0 && MinPuan!=0) queryy += " WHERE taban_puani BETWEEN " + MinPuan + " AND " + MaxPuan;
        else if (MaxPuan!=0 && MinPuan==0) queryy += " WHERE taban_puani NOT BETWEEN " + MaxPuan + " AND 600" ;
        else queryy += " WHERE bolum_id > 0";

        if(universite.length()!=0) {    //eger uni kismi doldurulduysa asagidakini ekleme yap
            queryy += " AND uni_adi LIKE '%" + universite + "%'";
        }
        if(bolum.length()!=0) {
            queryy += " AND bolum_adi LIKE '%" + bolum + "%'";
        }
        if(sehir.length()!=0) {
            queryy += " AND il_adi LIKE '%" + sehir + "%'";
        }
        if(bolumTuru.length()!=0 && bolumTuru.length()!=4) {    //length=4 cunku spinnerdan "tümü" 4 uzunlugunda geliyo
            queryy += " AND bolum_turu = '" + bolumTuru + "'";
        }
        if(puanTuru.length()!=0 && puanTuru.length()!=4) {
            queryy += " AND puan_turu = '" + puanTuru + "'";
        }
        if ((siralamaMax.length()!=0) && (siralamaMin.length()!=0) ) queryy += " AND basari_sirasi BETWEEN " + siralamaMin + " AND " + siralamaMax;
        else if ( (siralamaMax.length()==0)&& (siralamaMin.length()!=0) ) queryy += " AND basari_sirasi > " + siralamaMin;
        else if ( (siralamaMax.length()!=0) && (siralamaMin.length()==0) ) queryy += " AND basari_sirasi < " + siralamaMax;



        //queryy = "SELECT * FROM (" + queryy + " ORDER BY bolum_adi ASC) ORDER BY uni_adi ASC";
        //ORDER BY metoduyla sorgumuzu taban puanına gore buyukten kucuge dogru siraliyoruz
        queryy = queryy + " ORDER BY CAST(taban_puani AS INTEGER) DESC";


        openDB();       //Veritabani baglanti ac
        Bilgi bilgi;

        //Cursor yardımıyla db'den verileri alıp bilgi nesnemize yazıyoruz
        Cursor crs = database.rawQuery(queryy, null);

        if (crs!=null && crs.getCount()!=0)
        {
            while (crs.moveToNext())
            {
                //gelen veriyi bilgi nesnesine yaz(set et).
                bilgi = new Bilgi();

                bilgi.setPr_kodu(crs.getInt(crs.getColumnIndex("bolum_id")));
                bilgi.setBolum(crs.getString(crs.getColumnIndex("bolum_adi")));
                bilgi.setDil(crs.getString(crs.getColumnIndex("dil")));
                bilgi.setBolum_turu(crs.getString(crs.getColumnIndex("bolum_turu")));
                bilgi.setTaban_puani(crs.getString(crs.getColumnIndex("taban_puani")));
                bilgi.setSiralama(crs.getString(crs.getColumnIndex("basari_sirasi")));
                bilgi.setKontenjan(crs.getInt(crs.getColumnIndex("kontenjan")));
                bilgi.setUniversite(crs.getString(crs.getColumnIndex("uni_adi")));
                bilgi.setSehir(crs.getString(crs.getColumnIndex("il_adi")));
                bilgi.setFakulte(crs.getString(crs.getColumnIndex("fakulte_adi")));
                bilgi.setPuan_turu(crs.getString(crs.getColumnIndex("puan_turu")));

                filtreList.add(bilgi);                  //cekilen verileri arrayliste koy
            }
        }
        closeDB();                  //islem bitince veritabanini kapat.

        return filtreList;          //array listesini dondur
    }

    public List<Bilgi> meslekListeCek(String meslek){
        //Meslek Testinde meslege tiklandiginda bu metod veritabanından o meslege
        // ait tercih robotu verilerini cekiyor.

        List<Bilgi> meslekListe = new ArrayList<>();

        //Test sonucunda basılan bazı mesleklere ozel db sorgusuna eklemeler yapiliyor.
        String sorguSade = "SELECT * FROM tr_bolum b " +
                "INNER JOIN tr_fakulte f ON b.fakulte_id = f.fakulte_id " +
                "INNER JOIN tr_universite u ON f.uni_id = u.uni_id " +
                "WHERE bolum_adi" ;

        String meslekSorgu = "SELECT * FROM tr_bolum b " +
                "INNER JOIN tr_fakulte f ON b.fakulte_id = f.fakulte_id " +
                "INNER JOIN tr_universite u ON f.uni_id = u.uni_id " +
                "WHERE bolum_adi = '" + meslek + "'";



        //LIKE ve IN sorgulariyla tiklanan bazi mesleklere gore sorgumuza eklemeler yapıyoruz

        if ("Eğitim Fakültesi".equalsIgnoreCase(meslek)){ //Egitim fakultesine tiklanirsa tum ogretmenlikleri ara

            meslek = " LIKE '%Öğretmenliği%'";
            meslekSorgu = sorguSade + meslek;

        }else if ("Temel Bilimler".equalsIgnoreCase(meslek)){   //Temel Bilimlere tiklanirsa asagidakileri ara
            meslek = " IN ('Fizik','Kimya','Matematik','Biyoloji')";
            meslekSorgu = sorguSade + meslek;

        }else if ("Dil ve Edebiyat Bölümleri".equalsIgnoreCase(meslek)){
            meslek = " LIKE '%Dili ve Edebiyatı%'";
            meslekSorgu = sorguSade + meslek;
        }else if ("Finans, Bankacılık ve Sigortacılık".equalsIgnoreCase(meslek)){
            meslek = " IN ('Uluslararası Finans','Ekonomi ve Finans','Bankacılık ve Sigortacılık')";
            meslekSorgu = sorguSade + meslek;
        }else if ("Lojistik".equalsIgnoreCase(meslek)){
            meslek = " LIKE '%Lojistik%'";
            meslekSorgu = sorguSade + meslek;
        }else if ("Otel, Lokanta ve İkram Hizmetleri".equalsIgnoreCase(meslek)){
            meslek = " IN ('Aşçılık','İkram Hizmetleri')";
            meslekSorgu = sorguSade + meslek;
        }else if ("Ziraat Mühendisliği".equalsIgnoreCase(meslek)){
            meslek = " IN ('Bitki Koruma','Bahçe Bitkileri','Tarım Makineleri ve Teknolojileri Mühendisliği')";
            meslekSorgu = sorguSade + meslek;
        }


        //meslekSorgu = "SELECT * FROM (" + meslekSorgu + " ORDER BY bolum_adi ASC) ORDER BY uni_adi ASC";
        //ORDER BY metoduyla sorgumuzu taban puanına gore buyukten kucuge dogru siraliyoruz
        meslekSorgu = meslekSorgu + " ORDER BY CAST(taban_puani AS INTEGER) DESC";


        Bilgi bilgi;
        openDB();


        Cursor ccc = database.rawQuery(meslekSorgu, null);
        if (ccc!=null && ccc.getCount()!=0)
        {
            while (ccc.moveToNext())
            {
                bilgi = new Bilgi();

                bilgi.setPr_kodu(ccc.getInt(ccc.getColumnIndex("bolum_id")));
                bilgi.setBolum(ccc.getString(ccc.getColumnIndex("bolum_adi")));
                bilgi.setDil(ccc.getString(ccc.getColumnIndex("dil")));
                bilgi.setBolum_turu(ccc.getString(ccc.getColumnIndex("bolum_turu")));
                bilgi.setTaban_puani(ccc.getString(ccc.getColumnIndex("taban_puani")));
                bilgi.setSiralama(ccc.getString(ccc.getColumnIndex("basari_sirasi")));
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

    public String[] bolumCek(){     //Filtreleme ekranimizda bolum kutucuguna yazilani tamamlama ve
        // tahmin ozelligi saglamak amaciyla tum bolumleri veritabanından cekiyoruz.

        String sorguBolum = "SELECT DISTINCT bolum_adi FROM tr_bolum ORDER BY bolum_adi ASC";
        int b = 0;
        openDB();

        Cursor cc = database.rawQuery(sorguBolum, null);
        String[] bolum = new String[cc.getCount()];     //bolum listesi olustur
        if (cc!=null && cc.getCount()!=0)
        {
            while (cc.moveToNext())
            {
                bolum[b] = cc.getString(cc.getColumnIndex("bolum_adi"));
                b++;
            }
        }
        closeDB();
        return bolum;       //listeyi dondur
    }

    public String[] uniCek(){   //Filtreleme ekranimizda universite kutucuguna yazilani tamamlama ve
        // tahmin ozelligi saglamak amaciyla tum universiteleri veritabanından cekiyoruz.

        String sorguUni = "SELECT * FROM tr_universite ORDER BY uni_adi ASC";
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
        closeDB();
        return universite;
    }

    public String[] sehirCek(){     //Filtreleme ekranimizda sehir kutucuguna yazilani tamamlama ve
        // tahmin ozelligi saglamak amaciyla tum sehirleri veritabanından cekiyoruz.

        String sorguSehir = "SELECT DISTINCT il_adi FROM tr_universite ORDER BY il_adi ASC";
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
        closeDB();
        return sehir;
    }
    
}