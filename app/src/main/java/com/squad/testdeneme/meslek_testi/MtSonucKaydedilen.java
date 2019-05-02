package com.squad.testdeneme.meslek_testi;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.squad.testdeneme.ExpandableListAdapter;
import com.squad.testdeneme.R;
import com.squad.testdeneme.tercih_robotu.TercihFiltre;
import com.squad.testdeneme.tercih_robotu.TercihRobotu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MtSonucKaydedilen extends AppCompatActivity {

    private String grup1 = "com.squad.testdeneme.meslek_testi.GRUP1";
    private String grup2 = "com.squad.testdeneme.meslek_testi.GRUP2";
    private String grup3 = "com.squad.testdeneme.meslek_testi.GRUP3";
    private String grup1Yuzde = "com.squad.testdeneme.meslek_testi.GRUP1YUZDE";
    private String grup2Yuzde = "com.squad.testdeneme.meslek_testi.GRUP2YUZDE";
    private String grup3Yuzde = "com.squad.testdeneme.meslek_testi.GRUP3YUZDE";
    private String MAIN_KEY = "com.squad.testdeneme.meslek_testi.MAIN_DATA";

    String ilk_grup;
    String ikinci_grup;
    String ucuncu_grup;

    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHash;
    private TextView tvUyari;

    private long backPressedTime;

    public static String secilen;

    int baslikId, baslikIdIki, baslikIdUc, ilkGrupYuzde, ikinciGrupYuzde, ucuncuGrupYuzde;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mt_sonuc_kaydedilen);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        baslikId = getSharedPreferences(MAIN_KEY, MODE_PRIVATE).getInt(grup1, 0);
        baslikIdIki = getSharedPreferences(MAIN_KEY, MODE_PRIVATE).getInt(grup2, 0);
        baslikIdUc = getSharedPreferences(MAIN_KEY, MODE_PRIVATE).getInt(grup3, 0);
        ilkGrupYuzde = getSharedPreferences(MAIN_KEY, MODE_PRIVATE).getInt(grup1Yuzde, 2);
        ikinciGrupYuzde = getSharedPreferences(MAIN_KEY, MODE_PRIVATE).getInt(grup2Yuzde, 2);
        ucuncuGrupYuzde = getSharedPreferences(MAIN_KEY, MODE_PRIVATE).getInt(grup3Yuzde, 3);

        ilk_grup = QuizDbHelper.getInstance(getApplicationContext()).grupAdiCek(baslikId);
        ikinci_grup = QuizDbHelper.getInstance(getApplicationContext()).grupAdiCek(baslikIdIki);
        ucuncu_grup = QuizDbHelper.getInstance(getApplicationContext()).grupAdiCek(baslikIdUc);

        listView = findViewById(R.id.lvExp);
        tvUyari = findViewById(R.id.uyariId);

        initData();
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listHash);
        listView.setAdapter(listAdapter);

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {


            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                final String selected = (String) listAdapter.getChild(groupPosition, childPosition);
                secilen = selected;

                Intent i = new Intent(listView.getContext(), TercihRobotu.class);
                i.putExtra("secilen_meslek",secilen);
                i.putExtra("meslekSec","meslekSecimi");

                startActivity(i);


                return true;
            }
        });

        if (baslikId == 0){
            tvUyari.setVisibility(View.VISIBLE);
            listView.setVisibility(View.INVISIBLE);
        }else tvUyari.setVisibility(View.INVISIBLE);

    }

    private void initData() {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        listDataHeader.add(ilk_grup + " %" + ilkGrupYuzde);
        listDataHeader.add(ikinci_grup + " %" + ikinciGrupYuzde);
        listDataHeader.add(ucuncu_grup + " %" + ucuncuGrupYuzde);

        List<String> pc = QuizDbHelper.getInstance(getApplicationContext()).getMeslek(baslikId);
        List<String> pc1 = QuizDbHelper.getInstance(getApplicationContext()).getMeslek(baslikIdIki);
        List<String> pc2 = QuizDbHelper.getInstance(getApplicationContext()).getMeslek(baslikIdUc);

        listHash.put(listDataHeader.get(0), pc);
        listHash.put(listDataHeader.get(1), pc1);
        listHash.put(listDataHeader.get(2), pc2);

    }
}
