package com.squad.testdeneme.Meslek_Testi;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class Sonuc_ekrani extends AppCompatActivity {

    List<String> pc, pc1, pc2;
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
                Toast.makeText(getBaseContext(), selected, Toast.LENGTH_LONG)
                        .show();

                return true;
            }
        });

    }

    private void initData() {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        listDataHeader.add(ilk_grup + " %" + QuizActivity.ilkYuzde);
        listDataHeader.add(ikinci_grup + " %" + QuizActivity.ikinciYuzde);
        listDataHeader.add(ucuncu_grup + " %" + QuizActivity.ucuncuYuzde);

        pc = QuizDbHelper.getInstance(getApplicationContext()).getMeslek(QuizActivity.ilkGrupId);
        pc1 = QuizDbHelper.getInstance(getApplicationContext()).getMeslek(QuizActivity.ikinciGrupId);
        pc2 = QuizDbHelper.getInstance(getApplicationContext()).getMeslek(QuizActivity.ucuncuGrupId);


        listHash.put(listDataHeader.get(0), pc);
        listHash.put(listDataHeader.get(1), pc1);
        listHash.put(listDataHeader.get(2), pc2);


    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()){
            Intent intent = new Intent(Sonuc_ekrani.this, MainActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(this, "Ana ekrana donmek icin tekrar geriye basin", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();
    }
}
