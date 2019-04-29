package com.squad.testdeneme.tercih_robotu;

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

    }
}
