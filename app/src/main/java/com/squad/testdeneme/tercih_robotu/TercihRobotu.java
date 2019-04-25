package com.squad.testdeneme.tercih_robotu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.squad.testdeneme.R;

import java.util.List;

public class TercihRobotu extends AppCompatActivity {

    private List<Bilgi> bilgiList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tercih_robotu);


        bilgiList = TercihDb.getInstance(getApplicationContext()).verileriCek();
        int listeBoyut = bilgiList.size();

    }
}
