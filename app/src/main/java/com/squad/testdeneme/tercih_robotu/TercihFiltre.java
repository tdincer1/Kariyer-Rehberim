package com.squad.testdeneme.tercih_robotu;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squad.testdeneme.meslek_testi.MtSonucEkrani;
import com.squad.testdeneme.R;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.util.List;

public class TercihFiltre extends AppCompatActivity  {
    RangeSeekBar rangeSeekBar;
    Spinner bolumTuruSp, puanTuruSp;
    AutoCompleteTextView uniEt, bolumEt, sehirEt;
    Button gecisBtn;
    EditText maxEt, minEt;

    String uni, bolum, sehir, maxSiralama, minSiralama, bolumTuru, puanTuru;
    int minPuan;
    int maxPuan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tercih_filtre);

        //Arayuzle iliskilendirme
        rangeSeekBar = findViewById(R.id.rangeSeekbar);
        uniEt = findViewById(R.id.uniAramaTv);
        bolumEt = findViewById(R.id.bolumAramaTv);
        sehirEt = findViewById(R.id.sehirAramaTv);
        bolumTuruSp = findViewById(R.id.bolumTuruSpinner);
        puanTuruSp = findViewById(R.id.puanTuruSpinner);
        gecisBtn = findViewById(R.id.btn_uygula);
        maxEt = findViewById(R.id.maxPuanEt);
        minEt = findViewById(R.id.minPuanEt);


        //Filtreleme ekranimizda text kutucuklarina yazilani tamamlama ve
        // tahmin ozelligi saglamak amaciyla tum uni, bolum ve sehirleri veritabanından cekiyoruz.
        String[] uniListe = TercihDB.getInstance(getApplicationContext()).uniCek();
        String[] bolumListe = TercihDB.getInstance(getApplicationContext()).bolumCek();
        String[] sehirListe = TercihDB.getInstance(getApplicationContext()).sehirCek();

        //cekilen degerleri string arraye almistik. Simdi adapter'e yerlestiriyoruz.
        uniEt.setAdapter(new ArrayAdapter<>(TercihFiltre.this, android.R.layout.simple_list_item_1, uniListe));
        bolumEt.setAdapter(new ArrayAdapter<>(TercihFiltre.this, android.R.layout.simple_list_item_1, bolumListe));
        sehirEt.setAdapter(new ArrayAdapter<>(TercihFiltre.this, android.R.layout.simple_list_item_1, sehirListe));

        //Puan aramak icin kullanılan kaydirma cubugunun degerleri
        rangeSeekBar.setSelectedMaxValue(600);
        rangeSeekBar.setSelectedMinValue(0);

        //Puan kaydirma cubugunda secilen degerleri kaydet ve Toast mesajı olarak goster
        rangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                Number min_value = bar.getSelectedMinValue();
                Number max_value = bar.getSelectedMaxValue();

                minPuan = (int) min_value;
                maxPuan = (int) max_value;

                Toast.makeText(getApplicationContext(),"Min= " + minPuan + "\n"+ "Max=" + maxPuan,Toast.LENGTH_SHORT).show();
            }
        });


        gecisBtn.setOnClickListener(new View.OnClickListener() {    //Uygula butonuna basildiginda
            @Override
            public void onClick(View v) {
                baslatRobot();
            }
        });

    }

    public void baslatRobot(){  //Girilen filtreleme degerlerini tespit edip, sayfa gecisini yap

        //Girilen degerleri kaydet
        uni = uniEt.getText().toString();
        bolum = bolumEt.getText().toString();
        sehir = sehirEt.getText().toString();
        maxSiralama = maxEt.getText().toString();
        minSiralama = minEt.getText().toString();
        bolumTuru = bolumTuruSp.getSelectedItem().toString();
        puanTuru = puanTuruSp.getSelectedItem().toString();

        //Filtreleme verilerini intent ve extra metoduyla TercihRobotu sayfasina aktar.
        Intent intent = new Intent(TercihFiltre.this, TercihRobotu.class);
        intent.putExtra("universite", uni);
        intent.putExtra("bolum", bolum);
        intent.putExtra("sehir", sehir);
        intent.putExtra("siralamaMax", maxSiralama);
        intent.putExtra("siralamaMin", minSiralama);
        intent.putExtra("maximum_puan", maxPuan);
        intent.putExtra("minimum_puan", minPuan);
        intent.putExtra("bolum_turu", bolumTuru);
        intent.putExtra("puan_turu", puanTuru);
        intent.putExtra("filtrele","filtrelemeYap");
        startActivity(intent);
    }

}
