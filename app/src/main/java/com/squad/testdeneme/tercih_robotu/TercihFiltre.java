package com.squad.testdeneme.tercih_robotu;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.squad.testdeneme.meslek_testi.SonucEkrani;
import com.squad.testdeneme.R;

import org.florescu.android.rangeseekbar.RangeSeekBar;

public class TercihFiltre extends AppCompatActivity{

    RangeSeekBar rangeSeekBar;
    Spinner bolumTuruSp, puanTuruSp;
    AutoCompleteTextView uniEt, bolumEt, sehirEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tercih_filtre);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        rangeSeekBar = findViewById(R.id.rangeSeekbar);
        uniEt = findViewById(R.id.uniAramaTv);
        bolumEt = findViewById(R.id.bolumAramaTv);
        sehirEt = findViewById(R.id.sehirAramaTv);
        bolumTuruSp = findViewById(R.id.bolumTuruSpinner);
        puanTuruSp = findViewById(R.id.puanTuruSpinner);

        String[] uniListe = TercihDb.getInstance(getApplicationContext()).uniCek();
        String[] bolumListe = TercihDb.getInstance(getApplicationContext()).bolumCek();
        String[] sehirListe = TercihDb.getInstance(getApplicationContext()).sehirCek();

        uniEt.setAdapter(new ArrayAdapter<>(TercihFiltre.this, android.R.layout.simple_list_item_1, uniListe));
        bolumEt.setAdapter(new ArrayAdapter<>(TercihFiltre.this, android.R.layout.simple_list_item_1, bolumListe));
        sehirEt.setAdapter(new ArrayAdapter<>(TercihFiltre.this, android.R.layout.simple_list_item_1, sehirListe));
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(TercihFiltre.this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.puan_turu));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bolumTuruSp.setAdapter(myAdapter);

        ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(TercihFiltre.this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.bolum_turu));
        myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        puanTuruSp.setAdapter(myAdapter2);


        //deneme amaclı testten gelen meslegi toastta bas
        String deneme = SonucEkrani.secilen;
        //String secilen_meslek = getIntent().getStringExtra("secilen_meslek");

        if (deneme!=null) Toast.makeText(getApplicationContext(),"Secilen meslek: " + SonucEkrani.secilen, Toast.LENGTH_SHORT).show();
        //if (deneme!=null) Toast.makeText(getApplicationContext(),"Secilen_meslek_putExtra: " + secilen_meslek, Toast.LENGTH_SHORT).show();

        rangeSeekBar.setSelectedMaxValue(500);
        rangeSeekBar.setSelectedMinValue(0);

        rangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                Number min_value = bar.getSelectedMinValue();
                Number max_value = bar.getSelectedMaxValue();

                int min = (int) min_value;
                int max = (int) max_value;

                Toast.makeText(getApplicationContext(),"Min= " + min + "\n"+ "Max=" + max,Toast.LENGTH_SHORT).show();
            }
        });



    }



}
