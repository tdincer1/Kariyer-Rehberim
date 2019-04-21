package com.squad.testdeneme.kisilik_testi;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.squad.testdeneme.MySQLiteHelper;
import com.squad.testdeneme.Question;

import java.util.ArrayList;
import java.util.List;

public class KisilikDB {

    private static KisilikDB INSTANCE;
    private static Context context;

    private static SQLiteDatabase database;
    private static MySQLiteHelper myhelper; //Veritabanina erisim icin MySQLiteHelper objesi olusturduk

    public KisilikDB() {
        myhelper = new MySQLiteHelper(context);
    }

    public static KisilikDB getINSTANCE(Context context) {

        KisilikDB.context = context;
        if(INSTANCE == null)
        {
            INSTANCE = new KisilikDB();
        }

        return INSTANCE;
    }

    private void openDB() throws SQLException {

        database = myhelper.getWritableDatabase();
    }

    private void closeDB() {

        database.close();
    }

    public List<Question> getAllQuestions(){    //Tablo ismini String olarak alarak iki test icin de calisir
        List<Question> questionList = new ArrayList<>();

        openDB();
        String sqlQuestion = "SELECT * FROM kt_soru";
        Question question;

        Cursor c = database.rawQuery(sqlQuestion, null);
        if (c!=null && c.getCount()!=0)
        {
            // c.moveToFirst();
            while (c.moveToNext())
            {
                question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex("soru_detay")));
                question.setSoru_id(c.getInt(c.getColumnIndex("soru_id")));

                questionList.add(question);                         //cekilen verileri arrayliste koy
            }
        }
        closeDB();
        return questionList;
    }

    public void katsayiHesapDB (int soruId){ //grup_id, kisilik_id farkı ortak kullanımda sıkıntı

        String sql = "SELECT * FROM kt_sorukisilik WHERE soru_id = " + soruId;
        HesaplaKt hesapla;
        openDB();

        Cursor cb = database.rawQuery(sql, null);
        if (cb!=null && cb.getCount()!=0)
        {
            // c.moveToFirst();
            while (cb.moveToNext())
            {
                hesapla = new HesaplaKt();
                hesapla.setKatsayi(cb.getInt(cb.getColumnIndex("katsayi")));
                hesapla.setSoruId(cb.getInt(cb.getColumnIndex("soru_id")));
                hesapla.setKisilikId(cb.getInt(cb.getColumnIndex("kisilik_id")));

                KtTestActivity.hesap(hesapla.getKatsayi(),hesapla.getKisilikId());

            }
        }
        closeDB();
    }

    public String kisilikAdiCek(int kslkId){        //kisilik adi ve detay bereaber arraylistle cekilebilir

        String kslk_query = "SELECT * FROM kt_kisilik WHERE kisilik_id = " + kslkId;
        String kisilikAdi = null;
        openDB();

        Cursor csc = database.rawQuery(kslk_query,null);
        if (csc!=null && csc.getCount()!=0){
            while (csc.moveToNext())
            {
                kisilikAdi = csc.getString(csc.getColumnIndex("kisilik_adi"));
            }
        }
        closeDB();

        return kisilikAdi;
    }

    public String kisilikDetayCek(int kslkkId){

        String detayQuery = "SELECT * FROM kt_kisilik WHERE kisilik_id = " + kslkkId;
        String ksDetay = null;
        openDB();

        Cursor csdetay = database.rawQuery(detayQuery,null);
        if (csdetay!=null && csdetay.getCount()!=0){
            while (csdetay.moveToNext())
            {
                ksDetay = csdetay.getString(csdetay.getColumnIndex("kisilik_detay"));
            }
        }
        closeDB();

        return ksDetay;


    }
}
