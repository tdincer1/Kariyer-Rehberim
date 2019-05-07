package com.squad.testdeneme.meslek_testi;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.squad.testdeneme.MySQLiteHelper;
import com.squad.testdeneme.Question;

import java.util.ArrayList;
import java.util.List;

public class MeslekDB {

    private static MeslekDB INSTANCE;
    private static Context context;

    private static SQLiteDatabase database;
    private static MySQLiteHelper myhelper; //Veritabanina erisim icin MySQLiteHelper objesi olusturduk

    private static int deneme = 1;

    public MeslekDB() {

        myhelper = new MySQLiteHelper(context);
    }

    public static MeslekDB getInstance(Context context) {

        MeslekDB.context = context;

        if(INSTANCE == null)
        {
            INSTANCE = new MeslekDB();
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
        String sqlQuestion = "SELECT * FROM mt_soru";
        Question question;

        Cursor c = database.rawQuery(sqlQuestion, null);
        if (c!=null && c.getCount()!=0)
        {
            // baslikIdUc.moveToFirst();
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

        String sql = "SELECT * FROM mt_sorumeslek WHERE soru_id = " + soruId;
        HesaplaMt hesaplaMt;
        openDB();

        Cursor cb = database.rawQuery(sql, null);
        if (cb!=null && cb.getCount()!=0)
        {
            // baslikIdUc.moveToFirst();
            while (cb.moveToNext())
            {
                hesaplaMt = new HesaplaMt();
                hesaplaMt.setKatsayi(cb.getInt(cb.getColumnIndex("katsayi")));
                hesaplaMt.setSoruId(cb.getInt(cb.getColumnIndex("soru_id")));
                hesaplaMt.setGrupId(cb.getInt(cb.getColumnIndex("grup_id")));

                //int katsayi = cb.getInt(cb.getColumnIndex("katsayi"));
                //int grupId = cb.getInt(cb.getColumnIndex("grup_id"));

                MtTestActivity.hesap(hesaplaMt.getKatsayi(), hesaplaMt.getGrupId());

                //MtTestActivity activity = new MtTestActivity();
                //activity.hesap(hesaplaMt.getKatsayi(),hesaplaMt.getGrupId());
            }
        }
        closeDB();
    }

    public String grupAdiCek(int id){

        String sorgu = "SELECT * FROM mt_grup WHERE grup_id = " + id;
        String grupAdi = null;

        openDB();

        Cursor cursor = database.rawQuery(sorgu, null);

        if (cursor!=null && cursor.getCount()!=0){
            while (cursor.moveToNext())
            {
                grupAdi = cursor.getString(cursor.getColumnIndex("grup_adi"));
            }
        }
        closeDB();
        return grupAdi;
    }

    public List<String> getMeslek(int grupId){
        List<String> meslekList = new ArrayList<>();

        openDB();
        String meslekSorgu = "SELECT * FROM mt_meslek WHERE grup_id = " + grupId;


        Cursor cc = database.rawQuery(meslekSorgu, null);

        if(cc != null && cc.getCount()!=0){
            while (cc.moveToNext())
            {
                meslekList.add(cc.getString(cc.getColumnIndex("meslek_adi")));
            }
        }

        closeDB();
        return meslekList;
    }

    public List<String> getMeslek2(){
        List<String> meslekList = new ArrayList<>();

        openDB();
        String meslekSorgu = "SELECT * FROM mt_meslek";


        Cursor cc = database.rawQuery(meslekSorgu, null);

        if(cc != null && cc.getCount()!=0){
            while (cc.moveToNext())
            {
                meslekList.add(cc.getString(cc.getColumnIndex("meslek_adi")));
            }
        }

        closeDB();
        return meslekList;
    }

    public ArrayList<Meslek> getMeslek1(int grupId){
        ArrayList<Meslek> meslekList = new ArrayList<>();

        openDB();
        String meslekSorgu = "SELECT * FROM mt_meslek WHERE grup_id = " + grupId;
        Meslek meslek;

        Cursor cc = database.rawQuery(meslekSorgu, null);

        if(cc != null && cc.getCount()!=0){
            while (cc.moveToNext())
            {
                meslek = new Meslek();
                meslek.setMeslek_id(cc.getInt(cc.getColumnIndex("meslek_id")));
                meslek.setMeslek_adi(cc.getString(cc.getColumnIndex("meslek_adi")));
                meslek.setMeslek_detay(cc.getString(cc.getColumnIndex("meslek_detay")));
                meslek.setGrup_id(cc.getInt(cc.getColumnIndex("grup_id")));

                meslekList.add(meslek);
            }
        }

        closeDB();
        return meslekList;
    }


}
