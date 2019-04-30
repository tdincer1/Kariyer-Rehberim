package com.squad.testdeneme.kisilik_testi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.squad.testdeneme.AnaEkran;
import com.squad.testdeneme.R;
import com.squad.testdeneme.meslek_testi.SonucEkrani;

public class KtSonucEkrani extends AppCompatActivity {

    private long backPressedTime;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private String TV_KEY ="com.squad.testdeneme.kisilik_testi.ISIM";
    private String  TVDETAY_KEY="com.squad.testdeneme.kisilik_testi.ISIM";
    private String MAIN_KEY="com.squad.testdeneme.kisilik_testi.MAIN_DATA";
    String a,b;
    TextView tv;
    TextView tvId;
    TextView tvDetay;

    int kisilikId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kt_sonuc_ekrani);

        sharedPreferences=getSharedPreferences(MAIN_KEY,MODE_PRIVATE);
        editor=sharedPreferences.edit();


        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        tv = findViewById(R.id.ktTV);
        tvId = findViewById(R.id.tvKisilikId);
        tvDetay = findViewById(R.id.tvKisilikDetay);

        kisilikId = KtTestActivity.ilkKisilikId;

        String kisilikAdi = KisilikDB.getINSTANCE(getApplicationContext()).kisilikAdiCek(kisilikId);
        String kisilikDetay = KisilikDB.getINSTANCE(getApplicationContext()).kisilikDetayCek(kisilikId);

        editor.putString(TV_KEY,tv.getText().toString());
        editor.putString(TVDETAY_KEY,tvDetay.getText().toString());
        editor.commit();

        tv.setText("Kisilik Id'niz: " + kisilikId);
        tvId.setText("Kisilik AdÄ±: " + kisilikAdi);
        tvDetay.setText("Kisilik Detay: " + kisilikDetay);

        a=getSharedPreferences(MAIN_KEY,MODE_PRIVATE).getString(TV_KEY,"");
        b=getSharedPreferences(MAIN_KEY,MODE_PRIVATE).getString(TVDETAY_KEY,"");


    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()){
            Intent intent = new Intent(KtSonucEkrani.this, AnaEkran.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);    //hafÄ±zadaki(stack) activityleri temizle, geriye basildiginda gostrme
            startActivity(intent);
        }else {
            Toast.makeText(this, "Ana ekrana donmek icin tekrar geriye basin", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();
    }
}
