package com.squad.testdeneme.kisilik_testi;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.squad.testdeneme.R;

public class KtSonucKaydedilen extends AppCompatActivity {  //Ana ekrandan ulaşılan test sonuc ekrani

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

        // SharedPreferences'a kaydedilen Kisilik adi ve detayini cek ve textView'a yerlestir.
        cekilenAd = getSharedPreferences(MAIN_KEY, MODE_PRIVATE).getString(TV_KEY, "");
        cekilenDetay = getSharedPreferences(MAIN_KEY, MODE_PRIVATE).getString(TVDETAY_KEY, "");

        tvAdi.setText(cekilenAd);
        tvDetay.setText(cekilenDetay);

        if (cekilenAd.length() == 0){   //Test çozulmediyse uyarı yazisini bas.

            tvUyari.setText("Daha önceden test sonucunuz bulunmamaktadır!");
            tvUyari.getLayoutParams().height = RecyclerView.LayoutParams.WRAP_CONTENT;
            tvUyari.setVisibility(View.VISIBLE);
            tvAdi.setVisibility(View.INVISIBLE);
            tvDetay.setVisibility(View.INVISIBLE);

        }else tvUyari.setVisibility(View.INVISIBLE);    //cozulduyse uyarı yazisini gorunmez yap.

    }
}
