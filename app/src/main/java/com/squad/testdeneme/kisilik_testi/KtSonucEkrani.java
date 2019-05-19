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

public class KtSonucEkrani extends AppCompatActivity {  //Kisilik Testinin sonuc ekrani

    private long backPressedTime;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private String TV_KEY ="com.squad.testdeneme.kisilik_testi.TV";
    private String  TVDETAY_KEY="com.squad.testdeneme.kisilik_testi.TVDETAY";
    private String MAIN_KEY="com.squad.testdeneme.kisilik_testi.MAIN_DATA";

    TextView tvAdi;
    TextView tvDetay;

    int kisilikId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kt_sonuc_ekrani);


        tvAdi = findViewById(R.id.tvKisilikAdi);
        tvDetay = findViewById(R.id.tvKisilikDetay);

        kisilikId = KtTestActivity.ilkKisilikId;

        //Kisilik Adi ve detayini veritabanindan cekiyoruz.
        String kisilikAdi = KisilikDB.getINSTANCE(getApplicationContext()).kisilikAdiCek(kisilikId);
        String kisilikDetay = KisilikDB.getINSTANCE(getApplicationContext()).kisilikDetayCek(kisilikId);


        tvAdi.setText("Kişilik Adınız: " + kisilikAdi);
        tvDetay.setText(kisilikDetay);

        //SharedPreferences ile test sonucumuzu kaydediyoruz.
        //Boylece test sonucumuza, daha sonra KtSonucKaydedilen sayfasında erisim saglayabiliyoruz.
        sharedPreferences=getSharedPreferences(MAIN_KEY,MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.putString(TV_KEY, tvAdi.getText().toString());
        editor.putString(TVDETAY_KEY,tvDetay.getText().toString());
        editor.commit();

    }

    @Override
    public void onBackPressed() { //2 saniye içinde 2 defa basılırsa ana ekrana dön
        if (backPressedTime + 2000 > System.currentTimeMillis()){
            Intent intent = new Intent(KtSonucEkrani.this, AnaEkran.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);    //hafizadaki(stack) activityleri temizle, geriye basildiginda gostrme
            startActivity(intent);
        }else {
            Toast.makeText(this, "Ana ekrana donmek icin tekrar geriye basin", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();
    }
}
