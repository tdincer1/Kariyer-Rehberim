package com.squad.testdeneme.Meslek_Testi;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squad.testdeneme.ExpandableListAdapter;
import com.squad.testdeneme.MainActivity;
import com.squad.testdeneme.QuizDbHelper;
import com.squad.testdeneme.R;
import com.squad.testdeneme.Tercih_Robotu.TercihRobotu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class SonucEkrani extends AppCompatActivity {

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

    private long backPressedTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sonuc_ekrani);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

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


        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {


            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                final String selected = (String) listAdapter.getChild(
                        groupPosition, childPosition);
                //gonder(selected);
                Intent i= new Intent(listView.getContext(), TercihRobotu.class);
                startActivity(i);
                Toast.makeText(getBaseContext(), selected, Toast.LENGTH_LONG)
                        .show();

                return true;
            }
        });

    }

    /*
    private void gonder(String secilen){
        database ' e seçilen gönderilecek
    }
    */

    private void initData() {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        listDataHeader.add(ilk_grup + " %" + QuizActivity.ilkYuzde);
        listDataHeader.add(ikinci_grup + " %" + QuizActivity.ikinciYuzde);
        listDataHeader.add(ucuncu_grup + " %" + QuizActivity.ucuncuYuzde);

        List<String> pc = QuizDbHelper.getInstance(getApplicationContext()).getMeslek(QuizActivity.ilkGrupId);
        List<String> pc1 = QuizDbHelper.getInstance(getApplicationContext()).getMeslek(QuizActivity.ikinciGrupId);
        List<String> pc2 = QuizDbHelper.getInstance(getApplicationContext()).getMeslek(QuizActivity.ucuncuGrupId);


        listHash.put(listDataHeader.get(0), pc);
        listHash.put(listDataHeader.get(1), pc1);
        listHash.put(listDataHeader.get(2), pc2);


    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()){
            Intent intent = new Intent(SonucEkrani.this, MainActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(this, "Ana ekrana donmek icin tekrar geriye basin", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();
    }
}
