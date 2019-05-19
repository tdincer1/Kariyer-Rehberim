package com.squad.testdeneme.kisilik_testi;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.squad.testdeneme.MySQLiteHelper;
import com.squad.testdeneme.Question;

import java.util.ArrayList;
import java.util.List;

public class KisilikDB {      //Kisilik Testimizin Veritabanı bağlantısı

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

    private void openDB() throws SQLException {     //db bağlantı açma

        database = myhelper.getWritableDatabase();
    }

    private void closeDB() {

        database.close();
    }

    public List<Question> getAllQuestions(){    //Kişilik testimizin sorularını db'den çeken method
        List<Question> questionList = new ArrayList<>();

        openDB();
        String sqlQuestion = "SELECT * FROM kt_soru";   //Sqlite sorgumuzu tanımladık.
        Question question;

        Cursor c = database.rawQuery(sqlQuestion, null);
        if (c!=null && c.getCount()!=0) //Cursor yardımıyla db'den verileri alıp Question nesnemize yazıyoruz
        {
            while (c.moveToNext())
            {
                question = new Question();  //Dbden gelenleri nesneye yerleştiriyoruz.
                question.setQuestion(c.getString(c.getColumnIndex("soru_detay")));
                question.setSoru_id(c.getInt(c.getColumnIndex("soru_id")));

                questionList.add(question);           //cekilen verileri arrayliste koy
            }
        }
        closeDB();              //islem bitince veritabanini kapat.
        return questionList;    //soru array listesini dondur
    }

    public void katsayiHesapDB (int soruId){ //Kişilik Testimizdeki soruların katsayilarini veritabanından çeker.

        String sql = "SELECT * FROM kt_sorukisilik WHERE soru_id = " + soruId;
        HesaplaKt hesapla;
        openDB();

        Cursor cb = database.rawQuery(sql, null);
        if (cb!=null && cb.getCount()!=0)
        {
            while (cb.moveToNext())
            {
                hesapla = new HesaplaKt();
                hesapla.setKatsayi(cb.getInt(cb.getColumnIndex("katsayi")));
                hesapla.setSoruId(cb.getInt(cb.getColumnIndex("soru_id")));
                hesapla.setKisilikId(cb.getInt(cb.getColumnIndex("kisilik_id")));

                //Cekilen katsayi ve kisilik id'yi, Test Sayfamizdaki hesap metoduna yolla.
                KtTestActivity.hesap(hesapla.getKatsayi(),hesapla.getKisilikId());
            }
        }
        closeDB();
    }

    public String kisilikAdiCek(int kslkId){   //Sonuc ekranımızda basmak üzere Kişilik Adını çekiyoruz

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

    public String kisilikDetayCek(int kslkkId){ //Sonuc ekranımızda basmak üzere Kişilik Detayini çekiyoruz

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
