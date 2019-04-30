package com.squad.testdeneme;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.squad.testdeneme.kisilik_testi.KtSonucEkrani;
import com.squad.testdeneme.kisilik_testi.KtTestActivity;
import com.squad.testdeneme.meslek_testi.QuizActivity;
import com.squad.testdeneme.meslek_testi.SonucEkrani;
import com.squad.testdeneme.tercih_robotu.TercihFiltre;
import com.squad.testdeneme.tercih_robotu.TercihRobotu;

public class AnaEkran extends AppCompatActivity {
    private static final int REQUEST_CODE_QUIZ = 1;
    Button buttonMeslekTesti;
    Button buttonTercihRobotu;
    Button buttonKisilikTesti;
    Button buttonTercih,meslek_btn;
    Button buttonSonuc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ana_ekran);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        buttonMeslekTesti = findViewById(R.id.button_meslek_start);
        buttonTercihRobotu = findViewById(R.id.button_tercih_start);
        buttonKisilikTesti = findViewById(R.id.button_kisilik_start);
        buttonTercih = findViewById(R.id.button_robot);
        meslek_btn=findViewById(R.id.button2);
        buttonSonuc= findViewById(R.id.sonucid);
        buttonSonuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AnaEkran.this, KtSonucEkrani.class);
                startActivity(i);
            }
        });
        buttonMeslekTesti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMT();
            }
        });
        meslek_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ktm = new Intent(AnaEkran.this, SonucEkrani.class);
                startActivity(ktm);

            }
        });
        buttonKisilikTesti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kti = new Intent(AnaEkran.this, KtTestActivity.class);
                startActivity(kti);
            }
        });

        buttonTercihRobotu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnaEkran.this, TercihFiltre.class);
                startActivity(intent);
            }
        });

        buttonTercih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(AnaEkran.this, TercihRobotu.class);
                startActivity(inte);
            }
        });
    }

    private void startMT() {
        Intent intent = new Intent(AnaEkran.this, QuizActivity.class);
        startActivity(intent);
        //startActivityForResult(intent, REQUEST_CODE_QUIZ);
    }
}
