package com.squad.testdeneme.kisilik_testi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.squad.testdeneme.R;

public class KtSonucKaydedilen extends AppCompatActivity {

    private String TV_KEY = "com.squad.testdeneme.kisilik_testi.TV";
    private String TVDETAY_KEY = "com.squad.testdeneme.kisilik_testi.TVDETAY";
    private String MAIN_KEY = "com.squad.testdeneme.kisilik_testi.MAIN_DATA";

    TextView tvAdi, tvDetay, tvUyari;
    String cekilenAd, cekilenDetay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kt_sonuc_kaydedilen);

        tvAdi = findViewById(R.id.tvKisilikAdi);
        tvDetay = findViewById(R.id.tvKisilikDetay);
        tvUyari = findViewById(R.id.uyariId);


        cekilenAd = getSharedPreferences(MAIN_KEY, MODE_PRIVATE).getString(TV_KEY, "");
        cekilenDetay = getSharedPreferences(MAIN_KEY, MODE_PRIVATE).getString(TVDETAY_KEY, "");

        tvAdi.setText(cekilenAd);
        tvDetay.setText(cekilenDetay);

        if (cekilenAd.length() == 0){
            tvUyari.setVisibility(View.VISIBLE);
            tvAdi.setVisibility(View.INVISIBLE);
            tvDetay.setVisibility(View.INVISIBLE);

        }else tvUyari.setVisibility(View.INVISIBLE);

    }
}
