package com.squad.testdeneme.meslek_testi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.squad.testdeneme.R;
import com.squad.testdeneme.kisilik_testi.KtAnasayfa;
import com.squad.testdeneme.kisilik_testi.KtTestActivity;

public class MtAnasayfa extends AppCompatActivity {
    Button btn1,btn2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mt_anasayfa);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        btn1=findViewById(R.id.mt_anasayfa_btn1);
        btn2=findViewById(R.id.mt_anasayfa_btn2);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MtAnasayfa.this, MtTestActivity.class);
                startActivity(i);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MtAnasayfa.this,MtSonucKaydedilen.class);
                startActivity(i);
            }
        });
    }
}