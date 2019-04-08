package com.squad.testdeneme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Sonuc_ekrani extends AppCompatActivity {

    TextView tv1;
    TextView tv2;
    TextView tv3;
    TextView tv4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sonuc_ekrani);

        tv1 = findViewById(R.id.textView);
        tv2 = findViewById(R.id.textView2);
        tv3 = findViewById(R.id.textView3);
        tv4 = findViewById(R.id.textView4);


        //Puan puan = QuizActivity.puan;
        //tv1.setText("Hukuk puani: " + QuizActivity.puan.getHukuk());

        tv1.setText("Hukuk puani: " + QuizActivity.Hukuk);
        tv2.setText("3.: " + QuizActivity.ucuncuYuzde);
        tv3.setText("2.: " + QuizActivity.ikinciYuzde);
        tv4.setText("1. grup: "+ QuizActivity.ilkYuzde);


    }
}
