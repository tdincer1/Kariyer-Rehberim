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


public class MtTestActivity extends AppCompatActivity {

    private TextView textViewQuestion;
    private TextView textViewQuestionCount;
    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private RadioButton rb5;
    private Button buttonConfirmNext;

    private List<Question> questionList;

    private int questionCounter;
    private int questionCountTotal;
    private Question currentQuestion;

    private long backPressedTime;

    static int bilgisayar;
    static int egitim;
    static int elektronik;
    static int gastronomi;
    static int harita;
    static int havacilik;
    static int hukuk;
    static int toplum;
    static int isletme;
    static int konser;
    static int makine;
    static int mimarlik;
    static int saglik;
    static int spor;
    static int ziraat;

    static int answerNr;

    static int ilkGrupId;
    static int ikinciGrupId;
    static int ucuncuGrupId;
    static int ilkYuzde;
    static int ikinciYuzde;
    static int ucuncuYuzde;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mt_test);


        textViewQuestion = findViewById(R.id.mt_text_view_question);
        textViewQuestionCount = findViewById(R.id.mt_text_view_question_count);
        rbGroup = findViewById(R.id.mt_radio_group);
        rb1 = findViewById(R.id.mt_radio_button1);
        rb2 = findViewById(R.id.mt_radio_button2);
        rb3 = findViewById(R.id.mt_radio_button3);
        rb4 = findViewById(R.id.mt_radio_button4);
        rb5 = findViewById(R.id.mt_radio_button5);
        buttonConfirmNext = findViewById(R.id.mt_button_confirm_next);

        questionList = MeslekDB.getInstance(getApplicationContext()).getAllQuestions();     //listeye yerlestirme
        questionCountTotal = questionList.size();           //toplam soru bulma
        Collections.shuffle(questionList);                  //soru listesini karisik listeleme



        showNextQuestion();                                 //siradaki soruya gec

        buttonConfirmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked() || rb5.isChecked()){
                    checkAnswer();          //opsiyonel ama secilen_sık * katsayida kullanabiliriz. Bunu kaldırırsan
                    //answered = true; yapmak lazım
                    //katsayiHesapDB(currentQuestion.getSoru_id());                TODO: Buraya da konulabilir
                }else{
                    Toast.makeText(MtTestActivity.this, "Lütfen seçim yapın", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void showNextQuestion() {                       //siradaki soruya gec
        rbGroup.clearCheck();                               //şık secimini temizle


        if (questionCounter < questionCountTotal)
        {
            currentQuestion = questionList.get(questionCounter);

            textViewQuestion.setText(currentQuestion.getQuestion());

            questionCounter++;
            textViewQuestionCount.setText("Soru: " + questionCounter + "/" + questionCountTotal);   //Mevcut soruyu bastirma

            if (questionCounter < questionCountTotal){
                buttonConfirmNext.setText("Sıradaki soru");
            }else {
                buttonConfirmNext.setText("Testi bitir");
            }
        }else {     //TODO: Bu noktada gruplar karşılaştırılır ve sonuc sayfasına basmak uzere hazırlanır
            finishQuiz();
        }
    }

    public void  checkAnswer(){                         //Secilen sikkin katsayiyla carpimi burada mı olacak?

        RadioButton rbSelected = findViewById(rbGroup.getCheckedRadioButtonId()); //secilenin id'yi al
        answerNr = rbGroup.indexOfChild(rbSelected);        //secilen id'yi answerNr'de tut

        MeslekDB.getInstance(getApplicationContext()).katsayiHesapDB(currentQuestion.getSoru_id());
        showNextQuestion();

    }


    public static int cvp(){

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


    public static void hesap(int ks, int gId){

        int cevap_katsayisi = cvp();

        switch(gId){
            case 1: bilgisayar = bilgisayar + (ks * cevap_katsayisi);
                break;
            case 2: egitim += ks * cevap_katsayisi;
                break;
            case 3: elektronik += ks * cevap_katsayisi;
                break;
            case 4: gastronomi += ks * cevap_katsayisi;
                break;
            case 5: harita += ks * cevap_katsayisi;
                break;
            case 6: havacilik += ks * cevap_katsayisi;
                break;
            case 7: hukuk += ks * cevap_katsayisi;
                break;
            case 8: toplum += ks * cevap_katsayisi;
                break;
            case 9: isletme += ks * cevap_katsayisi;
                break;
            case 10: konser += ks * cevap_katsayisi;
                break;
            case 11: makine += ks * cevap_katsayisi;
                break;
            case 12: mimarlik += ks * cevap_katsayisi;
                break;
            case 13: saglik += ks * cevap_katsayisi;
                break;
            case 14: spor += ks * cevap_katsayisi;
                break;
            case 15: ziraat += ks * cevap_katsayisi;
                break;
        }

        int deneme = 22;

    }

    private void finishQuiz() { //TODO: finish metodu yerine intentle sonuc sayfasina gecis
                                //ayrica gecisten once grup puanlarını karsılastıran metodu calıstır
        grupPuani();
        Intent intent = new Intent(MtTestActivity.this, MtSonucEkrani.class);
        startActivity(intent);
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

    public void grupPuani(){    //TODO: ilk 3 sec. Puan yüzde hesapla. DB'den o grupların meslegini cek.

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


        for(int i=0; i<liste.length; i++ ){
            if (liste[i]>ilk){
                ucuncu = ikinci;
                ucuncuGrupId = ikinciGrupId;
                ikinci = ilk;
                ikinciGrupId = ilkGrupId;
                ilk = liste[i];
                ilkGrupId = i+1;
            }
            else if (liste[i]> ikinci && liste[i] <= ilk) {
                ucuncu = ikinci;
                ucuncuGrupId = ikinciGrupId;
                ikinci = liste[i];
                ikinciGrupId = (i+1);
            }
            else if (liste[i]> ucuncu && liste[i] <= ikinci){
                ucuncu=liste[i];
                ucuncuGrupId = i+1;
            }
        }

        ikinciYuzde = ikinci*100 / (ilk + ikinci + ucuncu);
        ucuncuYuzde = ucuncu*100 / (ilk + ikinci + ucuncu);
        ilkYuzde = 100 - (ikinciYuzde + ucuncuYuzde);
        int dembaba = 0;

    }

}
