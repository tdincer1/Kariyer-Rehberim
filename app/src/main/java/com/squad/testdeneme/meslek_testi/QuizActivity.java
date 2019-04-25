package com.squad.testdeneme.meslek_testi;

import android.content.Intent;
import android.support.v7.app.ActionBar;
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


public class QuizActivity extends AppCompatActivity {

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

    private boolean answered;

    private long backPressedTime;

    static int bilgisayar_puani;
    static int egitim_puani;
    static int elektronik_puani;
    static int Harita;
    static int Uzay;
    static int Hukuk;
    static int Toplum;
    static int Isletme;
    static int Konser;
    static int Makine;
    static int Gastronomi;
    static int Saglik;
    static int Spor;
    static int Tasarim;

    static int answerNr;

    static Puan puan;


    static int ilkGrupId;
    static int ikinciGrupId;
    static int ucuncuGrupId;
    static int ilkYuzde;
    static int ikinciYuzde;
    static int ucuncuYuzde;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mt_quiz);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        textViewQuestion = findViewById(R.id.mt_text_view_question);
        textViewQuestionCount = findViewById(R.id.mt_text_view_question_count);
        rbGroup = findViewById(R.id.mt_radio_group);
        rb1 = findViewById(R.id.mt_radio_button1);
        rb2 = findViewById(R.id.mt_radio_button2);
        rb3 = findViewById(R.id.mt_radio_button3);
        rb4 = findViewById(R.id.mt_radio_button4);
        rb5 = findViewById(R.id.mt_radio_button5);
        buttonConfirmNext = findViewById(R.id.mt_button_confirm_next);

        questionList = QuizDbHelper.getInstance(getApplicationContext()).getAllQuestions();     //listeye yerlestirme
        questionCountTotal = questionList.size();           //toplam soru bulma
        Collections.shuffle(questionList);                  //soru listesini karisik listeleme



        showNextQuestion();                                 //siradaki soruya gec

        buttonConfirmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answered)
                {
                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked() || rb5.isChecked()){
                        checkAnswer();          //opsiyonel ama secilen_sık * katsayida kullanabiliriz. Bunu kaldırırsan
                        //answered = true; yapmak lazım
                        //katsayiHesapDB(currentQuestion.getSoru_id());                TODO: Buraya da konulabilir
                    }else{
                        Toast.makeText(QuizActivity.this, "Lutfen secim yapin", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    QuizDbHelper.getInstance(getApplicationContext()).katsayiHesapDB(currentQuestion.getSoru_id());   // TODO: Veya buraya da konulabilir. Soru cevaplandiktan sonra
                    showNextQuestion();
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
            answered = false;
            buttonConfirmNext.setText("Cevabi Onayla");
        }else {     //TODO: Bu noktada gruplar karşılaştırılır ve sonuc sayfasına basmak uzere hazırlanır
            finishQuiz();
        }
    }

    public void  checkAnswer(){                         //Secilen sikkin katsayiyla carpimi burada mı olacak?
        answered = true;

        RadioButton rbSelected = findViewById(rbGroup.getCheckedRadioButtonId()); //secilenin id'yi al
        answerNr = rbGroup.indexOfChild(rbSelected);        //secilen id'yi answerNr'de tut
        //youtube dk 9.30 link:(tlgrX3HF6AI)

        if (questionCounter < questionCountTotal){
            buttonConfirmNext.setText("Siradaki soru");
        }else {
            buttonConfirmNext.setText("Testi bitir");
        }

    }

    public static int cvp(){

        int cvp_katsayi = 0;
        switch (answerNr){
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

    public static void hesap1(int ks, int gId){
        int cevap_katsayisi = cvp();
        puan = new Puan();          //yukarida tanımladım gerekirse burada tanımla

        switch (gId){
            case 1: puan.setBilgisayar_puani(puan.getBilgisayar_puani() + (ks * cevap_katsayisi));
                break;
            case 2: puan.setEgitim_puani(puan.getEgitim_puani() + (ks * cevap_katsayisi));
                    egitim_puani += ks * cevap_katsayisi;
                break;
            case 3: puan.setElektronik_puani(puan.getElektronik_puani() + (ks * cevap_katsayisi));
                break;
            case 4: puan.setHarita(puan.getHarita() + (ks * cevap_katsayisi));
                break;
            case 5: puan.setUzay(puan.getUzay() + (ks * cevap_katsayisi));
                break;
            case 6: puan.setHukuk(puan.getHukuk() + (ks * cevap_katsayisi));
                break;
            case 7: puan.setToplum(puan.getToplum() + (ks * cevap_katsayisi));
                break;
            case 8: puan.setIsletme(puan.getIsletme() + (ks * cevap_katsayisi));
                break;
            case 9: puan.setKonser(puan.getKonser() + (ks * cevap_katsayisi));
                break;
            case 10: puan.setMakine(puan.getMakine() + (ks * cevap_katsayisi));
                break;
            case 11: puan.setGastronomi(puan.getGastronomi() + (ks * cevap_katsayisi));
                break;
            case 12: puan.setSaglik(puan.getSaglik() + (ks * cevap_katsayisi));
                break;
            case 13: puan.setSpor(puan.getSpor() + (ks * cevap_katsayisi));
                break;
            case 14: puan.setTasarım(puan.getTasarım() + (ks * cevap_katsayisi));
                break;
        }

        int deneme1 = 22;
    }

    public static void hesap(int ks, int gId){

        //int denden = checkAnswer().answerNr;
        int cevap_katsayisi = cvp();
        //int cevap_katsayisi = secim();

        switch(gId){
            case 1: bilgisayar_puani = bilgisayar_puani + (ks * cevap_katsayisi);

                break;
            case 2: egitim_puani += ks * cevap_katsayisi;
                break;
            case 3: elektronik_puani += ks * cevap_katsayisi;
                break;
            case 4: Harita += ks * cevap_katsayisi;
                break;
            case 5: Uzay += ks * cevap_katsayisi;
                break;
            case 6: Hukuk += ks * cevap_katsayisi;
                break;
            case 7: Toplum += ks * cevap_katsayisi;
                break;
            case 8: Isletme += ks * cevap_katsayisi;
                break;
            case 9: Konser += ks * cevap_katsayisi;
                break;
            case 10: Makine += ks * cevap_katsayisi;
                break;
            case 11: Gastronomi += ks * cevap_katsayisi;
                break;
            case 12: Saglik += ks * cevap_katsayisi;
                break;
            case 13: Spor += ks * cevap_katsayisi;
                break;
            case 14: Tasarim += ks * cevap_katsayisi;
                break;
        }

        int deneme = 22;

    }

    private void finishQuiz() { //TODO: finish metodu yerine intentle sonuc sayfasina gecis
                                //ayrica gecisten once grup puanlarını karsılastıran metodu calıstır
        grupPuani();
        Intent intent = new Intent(QuizActivity.this, SonucEkrani.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {           //2sn icinde iki defa geri basarsa cik yoksa teste devam
        if (backPressedTime + 2000 > System.currentTimeMillis()){
            finish();   //TODO: sonuc sayfasina gecis tamamlandiginda, bunu finish metoduna dondur
        }else {
            Toast.makeText(this, "Cikmak icin tekrar geriye basin", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();
    }

    public void grupPuani(){    //TODO: ilk 3 sec. Puan yüzde hesapla. DB'den o grupların meslegini cek.

        int liste[] = new int[14];

        liste[0] = bilgisayar_puani;
        liste[1] = egitim_puani;
        liste[2] = elektronik_puani;
        liste[3] = Harita;
        liste[4] = Uzay;
        liste[5] = Hukuk;
        liste[6] = Toplum;
        liste[7] = Isletme;
        liste[8] = Konser;
        liste[9] = Makine;
        liste[10] = Gastronomi;
        liste[11] = Saglik;
        liste[12] = Spor;
        liste[13] = Tasarim;

        int ilk = 0;
        int ikinci = 0;
        int ucuncu = 0;

        ilkGrupId = 0;
        ikinciGrupId = 0;
        ucuncuGrupId = 0;

        for(int i=0; i<liste.length; i++ ){
            if (liste[i]>ilk){
                ilk = liste[i];
                ilkGrupId = i+1;
            }
            else if (liste[i]> ikinci && liste[i]<ilk) {
                ikinci = liste[i];
                ikinciGrupId = (i+1);
                int yam = 1;
            }
            else if (liste[i]> ucuncu && liste[i]<ikinci){
                ucuncu=liste[i];
                ucuncuGrupId = i+1;
            }
        }

        ilkYuzde = ilk*100 / (ilk + ikinci + ucuncu);
        ikinciYuzde = ikinci*100 / (ilk + ikinci + ucuncu);
        ucuncuYuzde = 100 - (ilkYuzde + ikinciYuzde);

        int dembaba = 0;



    }

}
