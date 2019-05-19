package com.squad.testdeneme.meslek_testi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squad.testdeneme.Question;
import com.squad.testdeneme.R;

import java.util.Collections;
import java.util.List;


public class MtTestActivity extends AppCompatActivity {     //Meslek testimizin oldugu sayfa

    private TextView textViewQuestion;
    private TextView textViewQuestionCount;
    private RadioGroup rbGroup;
    private RadioButton rb1, rb2, rb3, rb4, rb5;
    private Button buttonConfirmNext;

    private List<Question> questionList;

    private int questionCounter;
    private int questionCountTotal;
    private Question currentQuestion;

    private long backPressedTime;

    //grup puanlarini hesaplamak uzere tanimlanan degiskenler
    static int bilgisayar, egitim, elektronik, gastronomi, harita, havacilik, hukuk, toplum;
    static int isletme, konser, makine, mimarlik, saglik, spor, ziraat;

    static int answerNr;

    static int ilkGrupId, ikinciGrupId, ucuncuGrupId;

    static int ilkYuzde, ikinciYuzde, ucuncuYuzde;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mt_test);

        //Arayuzle olan baglantilar
        textViewQuestion = findViewById(R.id.mt_text_view_question);
        textViewQuestionCount = findViewById(R.id.mt_text_view_question_count);
        rbGroup = findViewById(R.id.mt_radio_group);
        rb1 = findViewById(R.id.mt_radio_button1);
        rb2 = findViewById(R.id.mt_radio_button2);
        rb3 = findViewById(R.id.mt_radio_button3);
        rb4 = findViewById(R.id.mt_radio_button4);
        rb5 = findViewById(R.id.mt_radio_button5);
        buttonConfirmNext = findViewById(R.id.mt_button_confirm_next);

        //Veritabanından soruları cek ve listeye yerlestir
        questionList = MeslekDB.getInstance(getApplicationContext()).getAllQuestions();
        questionCountTotal = questionList.size();           //toplam soru bulma
        Collections.shuffle(questionList);                  //soru listesini karisik listeleme


        showNextQuestion();                                 //siradaki soruya gec

        //Secim yapildiysa cevabı kontrol et. Yapilmadiysa uyari ver.
        buttonConfirmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked() || rb5.isChecked()){
                    checkAnswer();
                }else{
                    Toast.makeText(MtTestActivity.this, "Lütfen seçim yapın", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void showNextQuestion() {                       //siradaki soruya gec
        rbGroup.clearCheck();                               //şık secimini temizle


        if (questionCounter < questionCountTotal)   //Eger testimiz bitmediyse
        {

            //bulundugumuz soruyu Question nesnesine yerlestir.
            currentQuestion = questionList.get(questionCounter);

            textViewQuestion.setText(currentQuestion.getQuestion());    //soruyu texte yerlestir

            questionCounter++;//soru sayacini arttir

            //Bulundugumuz sorunun sayisini sayaca yerlestir.
            textViewQuestionCount.setText("Soru: " + questionCounter + "/" + questionCountTotal);

            if (questionCounter < questionCountTotal){  //buton ismini duruma gore ayarla
                buttonConfirmNext.setText("Sıradaki soru");
            }else {
                buttonConfirmNext.setText("Testi bitir");
            }
        }else {     //Bu noktada kisilikler karşılaştırılır ve sonuc sayfasına basmak uzere test sona erer.
            finishQuiz();
        }
    }

    public void  checkAnswer(){     //Secilen şıkkın id'sini al ve katsayi hesabi yap.

        RadioButton rbSelected = findViewById(rbGroup.getCheckedRadioButtonId()); //secilenin id'yi al
        answerNr = rbGroup.indexOfChild(rbSelected);        //secilen id'yi answerNr'de tut

        //mevcut sorunun id'sini, gruplara ait kaysayiyi cekecek olan katsayiHesapDB metoduna yolla
        MeslekDB.getInstance(getApplicationContext()).katsayiHesapDB(currentQuestion.getSoru_id());
        showNextQuestion();     //siradaki soruya gec

    }


    public static int cvp(){   //secilen şıkka gore cevap katsayisi dondur.

        int cvp_katsayi = 0;
        switch (answerNr){
            case 0: cvp_katsayi = 8;
            break;
            case 1: cvp_katsayi = 6;
            break;
            case 2: cvp_katsayi = 4;
            break;
            case 3: cvp_katsayi = 2;
            break;
            case 4: cvp_katsayi = 0;
            break;
        }

        return cvp_katsayi;
    }


    public static void hesap(int soruKatsayisi, int grupId){  //Gruplara ait katsayilari hesaplama

        int cevap_katsayisi = cvp();    //şık katsayisini cek

        switch(grupId){    //Grup Id'ye gore soru ve şık katsayasilarini toplam kişilik puanina ekle.
            case 1: bilgisayar = bilgisayar + (soruKatsayisi * cevap_katsayisi);
                break;
            case 2: egitim += soruKatsayisi * cevap_katsayisi;
                break;
            case 3: elektronik += soruKatsayisi * cevap_katsayisi;
                break;
            case 4: gastronomi += soruKatsayisi * cevap_katsayisi;
                break;
            case 5: harita += soruKatsayisi * cevap_katsayisi;
                break;
            case 6: havacilik += soruKatsayisi * cevap_katsayisi;
                break;
            case 7: hukuk += soruKatsayisi * cevap_katsayisi;
                break;
            case 8: toplum += soruKatsayisi * cevap_katsayisi;
                break;
            case 9: isletme += soruKatsayisi * cevap_katsayisi;
                break;
            case 10: konser += soruKatsayisi * cevap_katsayisi;
                break;
            case 11: makine += soruKatsayisi * cevap_katsayisi;
                break;
            case 12: mimarlik += soruKatsayisi * cevap_katsayisi;
                break;
            case 13: saglik += soruKatsayisi * cevap_katsayisi;
                break;
            case 14: spor += soruKatsayisi * cevap_katsayisi;
                break;
            case 15: ziraat += soruKatsayisi * cevap_katsayisi;
                break;
        }

        int deneme = 22;

    }

    private void finishQuiz() { //gecisten once kisilik puanlarını karsılastıran metodu calıstırıyor

        grupPuani();    //En yuksek puan alan ilk 3 grubu hesapla

        //Sonuc sayfasina gec
        Intent intent = new Intent(MtTestActivity.this, MtSonucEkrani.class);
        startActivity(intent);
    }

    public void grupPuani(){    //En yuksek puan alan ilk 3 grubu ve yuzdelerini hesapla

        int[] liste = new int[15];

        liste[0] = bilgisayar;
        liste[1] = egitim;
        liste[2] = elektronik;
        liste[3] = gastronomi;
        liste[4] = harita;
        liste[5] = havacilik;
        liste[6] = hukuk;
        liste[7] = toplum;
        liste[8] = isletme;
        liste[9] = konser;
        liste[10] = makine;
        liste[11] = mimarlik;
        liste[12] = saglik;
        liste[13] = spor;
        liste[14] = ziraat;

        int ilk = 0;
        int ikinci = 0;
        int ucuncu = 0;

        ilkGrupId = 0;
        ikinciGrupId = 0;
        ucuncuGrupId = 0;

        //Ilk 3 grubu liste dizisinde ara ve idlerini kaydet.
        for(int i=0; i<liste.length; i++ ){
            if (liste[i]>ilk)
            {
                ucuncu = ikinci;
                ucuncuGrupId = ikinciGrupId;
                ikinci = ilk;
                ikinciGrupId = ilkGrupId;
                ilk = liste[i];
                ilkGrupId = i+1;
            }
            else if (liste[i]> ikinci && liste[i] <= ilk)
            {
                ucuncu = ikinci;
                ucuncuGrupId = ikinciGrupId;
                ikinci = liste[i];
                ikinciGrupId = (i+1);
            }
            else if (liste[i]> ucuncu && liste[i] <= ikinci)
            {
                ucuncu=liste[i];
                ucuncuGrupId = i+1;
            }
        }

        //Yuzdeleri hesapla.
        ikinciYuzde = ikinci*100 / (ilk + ikinci + ucuncu);
        ucuncuYuzde = ucuncu*100 / (ilk + ikinci + ucuncu);
        ilkYuzde = 100 - (ikinciYuzde + ucuncuYuzde);
        int dembaba = 0;

    }

    @Override
    public void onBackPressed() {           //2sn icinde iki defa geri basarsa cik yoksa teste devam
        if (backPressedTime + 2000 > System.currentTimeMillis()){
            finish();   //TODO: sonuc sayfasina gecis tamamlandiginda, bunu finish metoduna dondur
        }else {
            Toast.makeText(this, "Çıkmak için tekrar geriye basın", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();
    }
}
