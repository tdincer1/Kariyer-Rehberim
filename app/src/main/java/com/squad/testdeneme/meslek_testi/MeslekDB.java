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

    public List<Question> getAllQuestions(){    //Meslek testimizin sorularini db'den ceken method
        List<Question> questionList = new ArrayList<>();

        openDB();   //Veritabani baglanti ac
        String sqlQuestion = "SELECT * FROM mt_soru";   //Sqlite sorgumuzu tanımladık.
        Question question;


        //Cursor yardımıyla db'den verileri alıp question nesnemize yazıyoruz
        Cursor c = database.rawQuery(sqlQuestion, null);
        if (c!=null && c.getCount()!=0)
        {
            while (c.moveToNext())
            {
                //gelen veriyi question nesnesine yaz(set et).
                question = new Question();

                question.setQuestion(c.getString(c.getColumnIndex("soru_detay")));
                question.setSoru_id(c.getInt(c.getColumnIndex("soru_id")));

                questionList.add(question);                         //cekilen verileri arrayliste koy
            }
        }
        closeDB();                  //islem bitince veritabanini kapat.
        return questionList;        //soru array listesini dondur
    }

    public void katsayiHesapDB (int soruId){ //Meslek Testimizdeki soruların katsayilarini veritabanından çeker.

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

                //Cekilen katsayi ve grup id'yi, Test Sayfamizdaki hesap metoduna yolla.
                MtTestActivity.hesap(hesaplaMt.getKatsayi(), hesaplaMt.getGrupId());
            }
        }
        closeDB();
    }

    public String grupAdiCek(int id){   //Db'den gelen Grup Adini, meslek sonucu sayfasina dondur

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


    //grupId'ye gore DB'den gelen Meslek Listesini, meslek sonucu sayfasina dondur
    public List<String> getMeslek(int grupId){
        List<String> meslekList = new ArrayList<>();

        openDB();
        String meslekSorgu = "SELECT * FROM mt_meslek WHERE grup_id = " + grupId;

        Cursor cc = database.rawQuery(meslekSorgu, null);

        if(cc != null && cc.getCount()!=0)
        {
            while (cc.moveToNext())
            {
                meslekList.add(cc.getString(cc.getColumnIndex("meslek_adi")));
            }
        }

        closeDB();
        return meslekList;  //meslek listesini dondur
    }

}
