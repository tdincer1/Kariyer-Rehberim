package com.squad.testdeneme;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class QuizDbHelper {

    private static QuizDbHelper INSTANCE;
    private static Context context;

    private static SQLiteDatabase database;
    private static MySQLiteHelper myhelper; //Veritabanina erisim icin MySQLiteHelper objesi olusturduk

    private static int deneme = 1;

    public QuizDbHelper() {

        myhelper = new MySQLiteHelper(context);
    }

    public static QuizDbHelper getInstance(Context context) {

        QuizDbHelper.context = context;

        if(INSTANCE == null)
        {
            INSTANCE = new QuizDbHelper();
        }
        return INSTANCE;
    }

    public List<Question> getAllQuestions(){
        List<Question> questionList = new ArrayList<>();

        openDB();
        String sql = "SELECT * FROM mt_soru";
        Question question;

        Cursor c = database.rawQuery(sql, null);
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

    private void openDB() throws SQLException {

        database = myhelper.getWritableDatabase();
    }

    private void closeDB() {

        database.close();
    }

    public void katsayiHesapDB (int soruId){
        String sql = "SELECT * FROM mt_sorumeslek WHERE soru_id = " + soruId;

        Hesapla hesapla;

        openDB();

        Cursor cb = database.rawQuery(sql, null);
        if (cb!=null && cb.getCount()!=0)
        {
            // c.moveToFirst();
            while (cb.moveToNext())
            {
                hesapla = new Hesapla();
                hesapla.setKatsayi(cb.getInt(cb.getColumnIndex("katsayi")));
                hesapla.setSoruId(cb.getInt(cb.getColumnIndex("soru_id")));
                hesapla.setGrupId(cb.getInt(cb.getColumnIndex("grup_id")));

                //int katsayi = cb.getInt(cb.getColumnIndex("katsayi"));
                //int grupId = cb.getInt(cb.getColumnIndex("grup_id"));

                QuizActivity.hesap(hesapla.getKatsayi(),hesapla.getGrupId());

                //QuizActivity activity = new QuizActivity();
                //activity.hesap(hesapla.getKatsayi(),hesapla.getGrupId());
            }
        }




        closeDB();
    }


/*
    public void deneme(int soru_id) {
        //QuizDbHelper.getHesap();



        String query = "SELECT * FROM mt_sorumeslek where soru_id="+ soru_id ;
        //answerNr
    }
*/
/*
    public static List<Hesapla> getHesap(){                //TODO
        List<Hesapla> hesaplaList = new ArrayList<>();
        openDB();

        String query = "SELECT * FROM mt_sorumeslek where soru_id="+ deneme ;

        Hesapla hesapla;

        Cursor cb = database.rawQuery(query, null);
        if (cb!=null && cb.getCount()!=0)
        {
            // c.moveToFirst();
            while (cb.moveToNext())
            {
                hesapla = new Hesapla();
                hesapla.setKatsayi(cb.getInt(cb.getColumnIndex("katsayi")));
                hesapla.setSoruId(cb.getInt(cb.getColumnIndex("soru_id")));
                hesapla.setGrupId(cb.getInt(cb.getColumnIndex("grup_id")));

                hesaplaList.add(hesapla);                        //cekilen verileri arrayliste koy
            }
        }


        closeDB();

        return hesaplaList;
    }
*/

}
