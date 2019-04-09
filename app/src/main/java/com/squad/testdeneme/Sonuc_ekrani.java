package com.squad.testdeneme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.HashMap;
import java.util.List;

public class Sonuc_ekrani extends AppCompatActivity {

    TextView tv1;
    TextView tv2;
    TextView tv3;
    TextView tv4;
    String ilk_grup;
    String ikinci_grup;
    String ucuncu_grup;
    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHash;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sonuc_ekrani);

        ilk_grup = QuizDbHelper.getInstance(getApplicationContext()).grupAdiCek(QuizActivity.ilkGrupId);
        ikinci_grup = QuizDbHelper.getInstance(getApplicationContext()).grupAdiCek(QuizActivity.ikinciGrupId);
        ucuncu_grup = QuizDbHelper.getInstance(getApplicationContext()).grupAdiCek(QuizActivity.ucuncuGrupId);


        listView = findViewById(R.id.lvExp);
        initData();
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listHash);
        listView.setAdapter(listAdapter);


        tv1 = findViewById(R.id.textView);
        tv2 = findViewById(R.id.textView2);
        tv3 = findViewById(R.id.textView3);
        tv4 = findViewById(R.id.textView4);


        tv1.setText("Birinci Grup: " + ilk_grup + " %" + QuizActivity.ilkYuzde);
        tv2.setText("İkinci Grup: " + ikinci_grup + " %" + QuizActivity.ikinciYuzde);
        tv3.setText("Ucuncu Grup: " + ucuncu_grup + " %" + QuizActivity.ucuncuYuzde);
        tv4.setText("Hukuk puani: " + QuizActivity.Hukuk);


    }

    private void initData() {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        listDataHeader.add(ilk_grup + " %" + QuizActivity.ilkYuzde);
        listDataHeader.add(ikinci_grup + " %" + QuizActivity.ikinciYuzde);
        listDataHeader.add(ucuncu_grup + " %" + QuizActivity.ucuncuYuzde);

        //List<String> pc = QuizDbHelper.getInstance(getApplicationContext()).getMeslek();
        List<String> pc = QuizDbHelper.getInstance(getApplicationContext()).getMeslek(QuizActivity.ilkGrupId);
        List<String> pc1 = QuizDbHelper.getInstance(getApplicationContext()).getMeslek(QuizActivity.ikinciGrupId);
        List<String> pc2 = QuizDbHelper.getInstance(getApplicationContext()).getMeslek(QuizActivity.ucuncuGrupId);
/*
        ArrayList<Meslek> pc = QuizDbHelper.getInstance(getApplicationContext()).getMeslek(QuizActivity.ilkGrupId);

        for(int i=0; i<pc.size(); i++){
            pc.get(i);
        }
        pc.get(0);
        pc.add("15");
        pc.add("i7 7700HQ İşlemci");
        pc.add("16 GB DDR4 Ram");
        pc.add("GTX1050 Ekran Kartı");
        pc.add("512 GB SSD");
        pc.add("10.319 TL");


        List<String> pc = new ArrayList<>();
        pc.add("15");
        pc.add("i7 7700HQ İşlemci");
        pc.add("16 GB DDR4 Ram");
        pc.add("GTX1050 Ekran Kartı");
        pc.add("512 GB SSD");
        pc.add("10.319 TL");

        List<String> pc1 = new ArrayList<>();
        pc1.add("15");
        pc1.add("i7 7700HQ İşlemci");
        pc1.add("16 GB DDR4 Ram");
        pc1.add("GTX1060 Ekran Kartı");
        pc1.add("1TB HDD + 512 GB SSD");

        List<String> pc2 = new ArrayList<>();
        pc2.add("15");
        pc2.add("i7 7700HQ İşlemci");
        pc2.add("16 GB DDR4 Ram");
        pc2.add("GTX1060 Ekran Kartı");
        pc2.add("1TB HDD + 512 GB SSD");

*/
        listHash.put(listDataHeader.get(0), pc);
        listHash.put(listDataHeader.get(1), pc1);
        listHash.put(listDataHeader.get(2), pc2);



    }
}
