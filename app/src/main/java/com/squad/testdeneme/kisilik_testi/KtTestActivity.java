package com.squad.testdeneme.kisilik_testi;

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

public class KtTestActivity extends AppCompatActivity {     //Kisilik testimizin oldugu sayfa

    private TextView textViewQuestion;
    private TextView textViewQuestionCount;
    private RadioGroup rbGroup;
    private RadioButton rb1, rb2, rb3, rb4, rb5;
    private Button buttonConfirmNext;

    private List<Question> questionList;    //soru listesi

    private int questionCounter;        //soru sayaci
    private int questionCountTotal;     //toplam soru
    private Question currentQuestion;   //bulundugumuz soruya ait nesne

    private long backPressedTime;

    static int cevapNo;

    //Kisilik puanlarini hesaplamak uzere tanimlanan degiskenler
    static int Arastirmaci, Barisci, BasariOdakli, Maceraci, MeydanOkuyan;
    static int Mukemmeliyetci, Ozgun, Sorgulayici, Yardimsever;

    static int ilkKisilikId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kt_test);

        //Arayuzle olan baglantilar
        textViewQuestion = findViewById(R.id.kt_text_view_question);
        textViewQuestionCount = findViewById(R.id.kt_text_view_question_count);
        rbGroup = findViewById(R.id.kt_radio_group);
        rb1 = findViewById(R.id.kt_radio_button1);
        rb2 = findViewById(R.id.kt_radio_button2);
        rb3 = findViewById(R.id.kt_radio_button3);
        rb4 = findViewById(R.id.kt_radio_button4);
        rb5 = findViewById(R.id.kt_radio_button5);
        buttonConfirmNext = findViewById(R.id.kt_button_confirm_next);

        //Veritabanından soruları cek ve listeye yerlestir
        questionList = KisilikDB.getINSTANCE(getApplicationContext()).getAllQuestions();
        questionCountTotal = questionList.size();           //toplam soru hesaplama
        Collections.shuffle(questionList);                  //soru listesini karisik listeleme

        showNextQuestion();                                 //siradaki soruya gec


        //Secim yapildiysa cevabı kontrol et. Yapilmadiysa uyari ver.
        buttonConfirmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked() || rb5.isChecked()){
                    checkAnswer();
                }else{
                    Toast.makeText(KtTestActivity.this, "Lütfen seçim yapın", Toast.LENGTH_SHORT).show();
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

            questionCounter++;  //soru sayacini arttir

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
        cevapNo = rbGroup.indexOfChild(rbSelected);        //secilen id'yi cevapNo'da tut

        //mevcut sorunun id'sini, kisiliklere ait kaysayiyi cekecek olan katsayiHesapDB metoduna yolla
        KisilikDB.getINSTANCE(getApplicationContext()).katsayiHesapDB(currentQuestion.getSoru_id());
        showNextQuestion();     //siradaki soruya gec
    }

    public static int cvp(){    //secilen şıkka gore cevap katsayisi dondur.

        int cvp_katsayi = 0;
        switch (cevapNo){
            case 0: cvp_katsayi = 4;
                break;
            case 1: cvp_katsayi = 3;
                break;
            case 2: cvp_katsayi = 2;
                break;
            case 3: cvp_katsayi = 1;
                break;
            case 4: cvp_katsayi = 0;
                break;
        }
        return cvp_katsayi;
    }

    public static void hesap(int soruKatsayisi, int kisilikId){    //Kisiliklere ait katsayilari hesaplama

        int cevap_katsayisi = cvp();    //şık katsayisini cek

        switch(kisilikId){  //Kisilik Id'ye gore soru ve şık katsayasilarini toplam kişilik puanina ekle.
            case 1: Arastirmaci = Arastirmaci + (soruKatsayisi * cevap_katsayisi);
                break;
            case 2: Barisci += soruKatsayisi * cevap_katsayisi;
                break;
            case 3: BasariOdakli += soruKatsayisi * cevap_katsayisi;
                break;
            case 4: Maceraci += soruKatsayisi * cevap_katsayisi;
                break;
            case 5: MeydanOkuyan += soruKatsayisi * cevap_katsayisi;
                break;
            case 6: Mukemmeliyetci += soruKatsayisi * cevap_katsayisi;
                break;
            case 7: Ozgun += soruKatsayisi * cevap_katsayisi;
                break;
            case 8: Sorgulayici += soruKatsayisi * cevap_katsayisi;
                break;
            case 9: Yardimsever += soruKatsayisi * cevap_katsayisi;
                break;
        }
    }

    private void finishQuiz() { //gecisten once kisilik puanlarını karsılastıran metodu calıstırıyor

        kisilikPuani();         //En yuksek puan alan kisiligi hesapla

        //Sonuc sayfasina gec
        Intent intent = new Intent(KtTestActivity.this, KtSonucEkrani.class);
        startActivity(intent);
    }

    public void kisilikPuani(){ //En yuksek puani alan kisiligi ve id'sini bulma.

        int[] liste = new int[9];

        liste[0] = Arastirmaci;
        liste[1] = Barisci;
        liste[2] = BasariOdakli;
        liste[3] = Maceraci;
        liste[4] = MeydanOkuyan;
        liste[5] = Mukemmeliyetci;
        liste[6] = Ozgun;
        liste[7] = Sorgulayici;
        liste[8] = Yardimsever;

        int ilk = 0;
        ilkKisilikId = 0;

        //En yuksek puanli kisiligi liste dizisinde ara.
        for(int i=0; i<liste.length; i++){
            if (liste[i]>ilk){
                ilk = liste[i];
                ilkKisilikId = i+1;
            }
        }

        int demmm = ilkKisilikId;
        int dansdsa = 0;
    }

    @Override
    public void onBackPressed() {           //2sn icinde iki defa geri basilirsa cik yoksa teste devam
        if (backPressedTime + 2000 > System.currentTimeMillis()){
            finish();
        }else {
            Toast.makeText(this, "Çıkmak için tekrar geriye basın", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();
    }
}
