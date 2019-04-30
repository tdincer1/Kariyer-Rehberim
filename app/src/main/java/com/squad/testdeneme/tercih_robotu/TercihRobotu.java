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
    RecyclerView recyclerView;
    TrAdapter adapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tercih_robotu);
        recyclerView = findViewById(R.id.recylerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bilgiList = TercihDb.getInstance(getApplicationContext()).verileriCek();
        int listeBoyut = bilgiList.size();

        adapter = new TrAdapter(this, bilgiList);
        recyclerView.setAdapter(adapter);


        /*  on receiving side
                Intent intent = getIntent();
                String var1 = intent.getStringExtra("key1");
                int i = var2.getIntExtra("key2", 0);
                */
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

        String queryy = "SELECT";

        if(uni != null) {
            queryy += " AND uni_adi = '" + uni + "'";
            //secimArguman += universite;
        }
        if(sehir != null) {
            queryy += " AND il_adi = '" + sehir + "'";
        }

        int deneme = 0;

    }
}
