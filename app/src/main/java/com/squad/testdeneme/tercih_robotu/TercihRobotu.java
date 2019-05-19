package com.squad.testdeneme.tercih_robotu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.squad.testdeneme.R;

import java.util.List;

public class TercihRobotu extends AppCompatActivity { //Tercih Robotu sonuclarinin listelendigi ekran
    //Bu ekranda RecyclerView, adapter ve Cardview ozelliklerini kullanarak sonuclari listeliyoruz.

    List<Bilgi> bilgiList;
    List<Bilgi> filtrelemeList;
    List<Bilgi> meslekList;
    RecyclerView recyclerView;
    TrAdapter adapter;
    TextView uyariTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tercih_robotu);
        recyclerView = findViewById(R.id.recylerView);
        uyariTv = findViewById(R.id.robotUyarıTv);
        uyariTv.setVisibility(View.INVISIBLE);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Bu kosullar, sayfa gecisi(intent) verilerine gore farklı islem yapmaya ve ona gore
        // farklı verileri ekrana basmaya yariyor.
        if(savedInstanceState == null)
        {
            Bundle extras = getIntent().getExtras();    //Intent metoduna yerlesen extra degerlerini al
            if (extras == null)     //Extra kismi boş ise eger tum tercih robotu verilerini cek
            {
                //tum verileri db'den verileriCek metoduyla cekip bilgiList'e al.
                bilgiList = TercihDB.getInstance(getApplicationContext()).verileriCek();
                //bilgiList'tekileri adapter'e ve recycler view'a yerlestir.
                adapter = new TrAdapter(this, bilgiList);
                recyclerView.setAdapter(adapter);

            }else{  //Bu sayfaya gecis yaparken eger onceki sayfa putExtra metoduyla veri yolladiysa
                    //bu kosulu calistir.

                String method = extras.getString("filtrele");   //TercihFiltre'den gelen degeri al
                String meslekMethod = extras.getString("meslekSec"); //MtSonucEkranindan gelen degeri al


                if ("filtrelemeYap".equals(method))  //Eger TercihFiltre sayfasindan buraya gecildiyse
                {

                    //TercihFiltre sayfasinda, intent extra icine koyulan filtreleme verilerini al.
                    Intent intent = getIntent();

                    Bilgi bilgiYolla = new Bilgi();

                    bilgiYolla.setUniversite(intent.getStringExtra("universite"));
                    bilgiYolla.setBolum(intent.getStringExtra("bolum"));
                    bilgiYolla.setSehir(intent.getStringExtra("sehir"));
                    bilgiYolla.setMaxPuan(intent.getIntExtra("maximum_puan", 0));
                    bilgiYolla.setMinPuan(intent.getIntExtra("minimum_puan", 0));
                    bilgiYolla.setMaxSiralama(intent.getStringExtra("siralamaMax"));
                    bilgiYolla.setMinSiralama(intent.getStringExtra("siralamaMin"));
                    bilgiYolla.setBolum_turu(intent.getStringExtra("bolum_turu"));
                    bilgiYolla.setPuan_turu(intent.getStringExtra("puan_turu"));

                    //filtreCek metoduna degerleri yerlestir. Devaminda veritabanindan donen verileri filtrelemeList'e yerlestir
                    filtrelemeList = TercihDB.getInstance(getApplicationContext()).filtreCek(bilgiYolla);
                    int listeBoyut = filtrelemeList.size();


                    //Liste boşsa, yani filtreleme degerlerine gore sonuc bulunamadıysa, uyarı bas;
                    // Doluysa filtrelemeList'tekileri adapter'e ve recycler view'a yerlestir.
                    if (listeBoyut==0){     //Liste boşsa sonuc bulunamadı bas, doluysa listeyi yükle
                        uyariTv.setText("Aranan kriterlere uygun sonuç bulunamadı");
                        uyariTv.getLayoutParams().height = RecyclerView.LayoutParams.WRAP_CONTENT;
                        uyariTv.setVisibility(View.VISIBLE);
                    }else{
                        adapter = new TrAdapter(this, filtrelemeList);
                        recyclerView.setAdapter(adapter);
                    }
                }
                else if ("meslekSecimi".equals(meslekMethod))   //Eger Meslek sonuc sayfasindan buraya gecildiyse
                {
                    Intent in = getIntent();

                    //Meslek sonucu sayfasindan, intent extra icine koyulup yollanan meslegi secilenMeslek'e al.
                    String secilenMeslek = in.getStringExtra("secilen_meslek");

                    //meslekListeCek metoduna secilen meslegi yerlestir.
                    // Devaminda veritabanindan donen verileri meslekList'e yerlestir.
                    meslekList = TercihDB.getInstance(getApplicationContext()).meslekListeCek(secilenMeslek);
                    int meslekListBoyut = meslekList.size();

                    //Liste boşsa, yani tercih robotunda secilen meslege gore veri bulunamadıysa, uyarı bas;
                    // Liste doluysa, filtrelemeList'tekileri adapter'e ve recycler view'a yerlestir.
                    if (meslekListBoyut==0){
                        uyariTv.setText("Tercih robotunda, secilen mesleğe ait veri bulunamadı");
                        uyariTv.getLayoutParams().height = RecyclerView.LayoutParams.WRAP_CONTENT;
                        uyariTv.setVisibility(View.VISIBLE);
                        uyariTv.requestLayout();
                    }else{
                        adapter = new TrAdapter(this, meslekList);
                        recyclerView.setAdapter(adapter);
                    }

                }
            }
        }
    }
}
