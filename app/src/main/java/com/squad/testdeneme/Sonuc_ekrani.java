package com.squad.testdeneme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class Sonuc_ekrani extends AppCompatActivity {

    TextView tv1;
    TextView tv2;
    TextView tv3;
    TextView tv4;
    String ilk_grup;
    String ikinci_grup;
    String ucuncu_grup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sonuc_ekrani);

        tv1 = findViewById(R.id.textView);
        tv2 = findViewById(R.id.textView2);
        tv3 = findViewById(R.id.textView3);
        tv4 = findViewById(R.id.textView4);


        ilk_grup = QuizDbHelper.getInstance(getApplicationContext()).grupAdiCek(QuizActivity.ilkGrupId);
        ikinci_grup = QuizDbHelper.getInstance(getApplicationContext()).grupAdiCek(QuizActivity.ikinciGrupId);
        ucuncu_grup = QuizDbHelper.getInstance(getApplicationContext()).grupAdiCek(QuizActivity.ucuncuGrupId);


        //Puan puan = QuizActivity.puan;
        //tv1.setText("Hukuk puani: " + QuizActivity.puan.getHukuk());
/*
        tv1.setText("Birinci Grup: " + QuizActivity.ilkGrupId + " %" + QuizActivity.ilkYuzde);
        tv2.setText("İkinci Grup: " + QuizActivity.ikinciGrupId + " %" + QuizActivity.ikinciYuzde);
        tv3.setText("Ucuncu Grup: " + QuizActivity.ucuncuGrupId + " %" + QuizActivity.ucuncuYuzde);
        tv4.setText("Hukuk puani: " + QuizActivity.Hukuk);

*/

        tv1.setText("Birinci Grup: " + ilk_grup + " %" + QuizActivity.ilkYuzde);
        tv2.setText("İkinci Grup: " + ikinci_grup + " %" + QuizActivity.ikinciYuzde);
        tv3.setText("Ucuncu Grup: " + ucuncu_grup + " %" + QuizActivity.ucuncuYuzde);
        tv4.setText("Hukuk puani: " + QuizActivity.Hukuk);


/*
        tv1.setText("Hukuk puani: " + QuizActivity.Hukuk);
        tv2.setText("3.: " + QuizActivity.ucuncuYuzde);
        tv3.setText("2.: " + QuizActivity.ikinciYuzde);
        tv4.setText("1. grup: "+ QuizActivity.ilkYuzde);
        */

    }
}
