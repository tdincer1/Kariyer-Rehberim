package com.squad.testdeneme.meslek_testi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.squad.testdeneme.AnaEkran;
import com.squad.testdeneme.R;
import com.squad.testdeneme.tercih_robotu.TercihRobotu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MtSonucEkrani extends AppCompatActivity {


    String ilk_grup;
    String ikinci_grup;
    String ucuncu_grup;
    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHash;

    private long backPressedTime;

    public static String secilen;

    private String grup1 = "com.squad.testdeneme.meslek_testi.GRUP1";
    private String grup2 = "com.squad.testdeneme.meslek_testi.GRUP2";
    private String grup3 = "com.squad.testdeneme.meslek_testi.GRUP3";
    private String grup1Yuzde = "com.squad.testdeneme.meslek_testi.GRUP1YUZDE";
    private String grup2Yuzde = "com.squad.testdeneme.meslek_testi.GRUP2YUZDE";
    private String grup3Yuzde = "com.squad.testdeneme.meslek_testi.GRUP3YUZDE";
    private String MAIN_KEY = "com.squad.testdeneme.meslek_testi.MAIN_DATA";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mt_sonuc_ekrani);


        sharedPreferences=getSharedPreferences(MAIN_KEY,MODE_PRIVATE);
        editor=sharedPreferences.edit();

        ilk_grup = MeslekDB.getInstance(getApplicationContext()).grupAdiCek(MtTestActivity.ilkGrupId);
        ikinci_grup = MeslekDB.getInstance(getApplicationContext()).grupAdiCek(MtTestActivity.ikinciGrupId);
        ucuncu_grup = MeslekDB.getInstance(getApplicationContext()).grupAdiCek(MtTestActivity.ucuncuGrupId);


        listView = findViewById(R.id.lvExp);
        initData();
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listHash);
        listView.setAdapter(listAdapter);




        listView.expandGroup(0);
        listView.expandGroup(1);
        listView.expandGroup(2);


        editor.putInt(grup1, MtTestActivity.ilkGrupId);
        editor.putInt(grup2,  MtTestActivity.ikinciGrupId);
        editor.putInt(grup3,  MtTestActivity.ucuncuGrupId);
        editor.putInt(grup1Yuzde, MtTestActivity.ilkYuzde);
        editor.putInt(grup2Yuzde,  MtTestActivity.ikinciYuzde);
        editor.putInt(grup3Yuzde,  MtTestActivity.ucuncuYuzde);
        editor.commit();


        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {


            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                final String selected = (String) listAdapter.getChild(groupPosition, childPosition);
                secilen = selected;
                //TODO: if(secilen=="Konservatuvar") baska bi sayfa baslat
                //else meslek secimi baslat
                //kaydedilene de eklemek lazim

                if ("Konservatuvar".equalsIgnoreCase(secilen)){
                    Intent intent = new Intent(MtSonucEkrani.this, KonservatuarEkran.class);
                    startActivity(intent);
                }else {
                    Intent i= new Intent(listView.getContext(), TercihRobotu.class);
                    i.putExtra("secilen_meslek",secilen);
                    i.putExtra("meslekSec","meslekSecimi");

                    startActivity(i);
                }

                return true;
            }
        });

    }


    private void initData() {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        listDataHeader.add(ilk_grup + " %" + MtTestActivity.ilkYuzde);
        listDataHeader.add(ikinci_grup + " %" + MtTestActivity.ikinciYuzde);
        listDataHeader.add(ucuncu_grup + " %" + MtTestActivity.ucuncuYuzde);

        List<String> pc = MeslekDB.getInstance(getApplicationContext()).getMeslek(MtTestActivity.ilkGrupId);
        List<String> pc1 = MeslekDB.getInstance(getApplicationContext()).getMeslek(MtTestActivity.ikinciGrupId);
        List<String> pc2 = MeslekDB.getInstance(getApplicationContext()).getMeslek(MtTestActivity.ucuncuGrupId);


        listHash.put(listDataHeader.get(0), pc);
        listHash.put(listDataHeader.get(1), pc1);
        listHash.put(listDataHeader.get(2), pc2);


    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()){
            Intent intent = new Intent(MtSonucEkrani.this, AnaEkran.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);    //hafızadaki(stack) activityleri temizle, geriye basildiginda gostrme
            startActivity(intent);
        }else {
            Toast.makeText(this, "Ana ekrana donmek icin tekrar geriye basin", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();
    }
}
