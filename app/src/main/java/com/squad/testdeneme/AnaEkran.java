package com.squad.testdeneme;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.squad.testdeneme.kisilik_testi.KtAnaSayfa;
import com.squad.testdeneme.meslek_testi.KonservatuarEkran;
import com.squad.testdeneme.meslek_testi.MtAnaSayfa;
import com.squad.testdeneme.tercih_robotu.TercihFiltre;

import java.text.SimpleDateFormat;
import java.util.Date;


public class AnaEkran extends AppCompatActivity {
    private static final int REQUEST_CODE_QUIZ = 1;
    Button buttonMeslekTesti;
    Button buttonTercihRobotu;
    Button buttonKisilikTesti;
    private TextView txtTimerDay, txtTimerHour, txtTimerMinute, txtTimerSecond;
    private TextView tvEvent;
    private Handler handler;
    private Runnable runnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ana_ekran);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        buttonMeslekTesti = findViewById(R.id.button_meslek_start);
        buttonTercihRobotu = findViewById(R.id.button_tercih_start);
        buttonKisilikTesti = findViewById(R.id.button_kisilik_start);

        txtTimerDay = findViewById(R.id.txtTimerDay);
        txtTimerHour = findViewById(R.id.txtTimerHour);
        txtTimerMinute = findViewById(R.id.txtTimerMinute);
        txtTimerSecond = findViewById(R.id.txtTimerSecond);
        tvEvent = findViewById(R.id.tvhappyevent);

        countDownStart();


        buttonMeslekTesti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMT();
            }
        });

        buttonKisilikTesti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kti = new Intent(AnaEkran.this, KtAnaSayfa.class);
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


    }

    private void startMT() {
        Intent intent = new Intent(AnaEkran.this, MtAnaSayfa.class);
        startActivity(intent);
        //startActivityForResult(intent, REQUEST_CODE_QUIZ);
    }
    public void countDownStart() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat(
                            "yyyy-MM-dd");
                    // Please here set your event date//YYYY-MM-DD
                    Date futureDate = dateFormat.parse("2019-06-15");
                    Date currentDate = new Date();
                    if (!currentDate.after(futureDate)) {
                        long diff = futureDate.getTime()
                                - currentDate.getTime();
                        long days = diff / (24 * 60 * 60 * 1000);
                        diff -= days * (24 * 60 * 60 * 1000);
                        long hours = diff / (60 * 60 * 1000);
                        diff -= hours * (60 * 60 * 1000);
                        long minutes = diff / (60 * 1000);
                        diff -= minutes * (60 * 1000);
                        long seconds = diff / 1000;
                        txtTimerDay.setText("" + String.format("%02d", days));
                        txtTimerHour.setText("" + String.format("%02d", hours));
                        txtTimerMinute.setText(""
                                + String.format("%02d", minutes));
                        txtTimerSecond.setText(""
                                + String.format("%02d", seconds));
                    } else {
                        tvEvent.setVisibility(View.VISIBLE);
                        tvEvent.setText("Sınav Yapılmıştır !");
                        textViewGone();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 1 * 1000);
    }

    public void textViewGone() {
        findViewById(R.id.LinearLayout10).setVisibility(View.GONE);
        findViewById(R.id.LinearLayout11).setVisibility(View.GONE);
        findViewById(R.id.LinearLayout12).setVisibility(View.GONE);
        findViewById(R.id.LinearLayout13).setVisibility(View.GONE);
        findViewById(R.id.textView1).setVisibility(View.GONE);
        findViewById(R.id.textView2).setVisibility(View.GONE);
    }
}

