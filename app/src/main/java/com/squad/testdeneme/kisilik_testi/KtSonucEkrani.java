package com.squad.testdeneme.kisilik_testi;

import android.content.Intent;
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

    TextView tv;
    TextView tvId;
    TextView tvDetay;

    int kisilikId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kt_sonuc_ekrani);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        tv = findViewById(R.id.ktTV);
        tvId = findViewById(R.id.tvKisilikId);
        tvDetay = findViewById(R.id.tvKisilikDetay);

        kisilikId = KtTestActivity.ilkKisilikId;

        String kisilikAdi = KisilikDB.getINSTANCE(getApplicationContext()).kisilikAdiCek(kisilikId);
        String kisilikDetay = KisilikDB.getINSTANCE(getApplicationContext()).kisilikDetayCek(kisilikId);

        tv.setText("Kisilik Id'niz: " + kisilikId);
        tvId.setText("Kisilik Adı: " + kisilikAdi);
        tvDetay.setText("Kisilik Detay: " + kisilikDetay);


    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()){
            Intent intent = new Intent(KtSonucEkrani.this, AnaEkran.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);    //hafızadaki(stack) activityleri temizle, geriye basildiginda gostrme
            startActivity(intent);
        }else {
            Toast.makeText(this, "Ana ekrana donmek icin tekrar geriye basin", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();
    }
}
