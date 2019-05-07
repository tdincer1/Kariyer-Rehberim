package com.squad.testdeneme.tercih_robotu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.squad.testdeneme.R;

import java.util.List;

public class TercihRobotu extends AppCompatActivity {

    List<Bilgi> bilgiList;
    List<Bilgi> denemeList;
    List<Bilgi> meslekList;
    RecyclerView recyclerView;
    TrAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tercih_robotu);
        recyclerView = findViewById(R.id.recylerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if(savedInstanceState == null)
        {
            Bundle extras = getIntent().getExtras();
            if (extras == null)
            {
                //Extra bundle is null
                bilgiList = TercihDB.getInstance(getApplicationContext()).verileriCek();
                int listeBoyut = bilgiList.size();

                adapter = new TrAdapter(this, bilgiList);
                recyclerView.setAdapter(adapter);

            }else{  // else if(String "filtrelemeYap" == extras.getString("filtrele"))
                String method = extras.getString("filtrele");
                String meslekMethod = extras.getString("meslekSec");
                //"filtrelemeYap"
                //method.equals("filtrelemeYap")
                if ("filtrelemeYap".equals(method))
                {
                    //Call method here!
                    Intent intent = getIntent();


                    String uni = intent.getStringExtra("universite");
                    String bolum = intent.getStringExtra("bolum");
                    String sehir = intent.getStringExtra("sehir");
                    int maximumPuan = intent.getIntExtra("maximum_puan",0);
                    int minimumPuan = intent.getIntExtra("minimum_puan",0);
                    String siralamaMax = intent.getStringExtra("siralamaMax");
                    String siralamaMin = intent.getStringExtra("siralamaMin");
                    String bolumTuru = intent.getStringExtra("bolum_turu");
                    String puanTuru = intent.getStringExtra("puan_turu");

                    String[] yolla = {uni, bolum, sehir, siralamaMax, siralamaMin, bolumTuru, puanTuru};


                    denemeList = TercihDB.getInstance(getApplicationContext()).filtreCek(yolla, maximumPuan, minimumPuan);
                    int listeBoyut = denemeList.size();

                    adapter = new TrAdapter(this, denemeList);
                    recyclerView.setAdapter(adapter);
                }
                else if ("meslekSecimi".equals(meslekMethod))   // once oncreate'e koy gerekirse
                {
                    Intent in = getIntent();

                    String secilenMeslek = in.getStringExtra("secilen_meslek");

                    //String secilenMeslek = MtSonucEkrani.secilen;

                    meslekList = TercihDB.getInstance(getApplicationContext()).meslekListeCek(secilenMeslek);
                    int meslekListBoyut = meslekList.size();

                    adapter = new TrAdapter(this, meslekList);
                    recyclerView.setAdapter(adapter);
                }
            }
        }
    }
}
