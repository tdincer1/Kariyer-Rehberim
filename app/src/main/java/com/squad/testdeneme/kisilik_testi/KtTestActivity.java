package com.squad.testdeneme.kisilik_testi;

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

public class KtTestActivity extends AppCompatActivity {

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

    static int cevapNo;

    static int Arastirmaci;
    static int Barisci;
    static int BasariOdakli;
    static int Maceraci;
    static int MeydanOkuyan;
    static int Mukemmelmeliyetci;
    static int Ozgun;
    static int Sorgulayici;
    static int Yardimsever;

    static int ilkKisilikId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kt_test);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        textViewQuestion = findViewById(R.id.kt_text_view_question);
        textViewQuestionCount = findViewById(R.id.kt_text_view_question_count);
        rbGroup = findViewById(R.id.kt_radio_group);
        rb1 = findViewById(R.id.kt_radio_button1);
        rb2 = findViewById(R.id.kt_radio_button2);
        rb3 = findViewById(R.id.kt_radio_button3);
        rb4 = findViewById(R.id.kt_radio_button4);
        rb5 = findViewById(R.id.kt_radio_button5);
        buttonConfirmNext = findViewById(R.id.kt_button_confirm_next);

        questionList = KisilikDB.getINSTANCE(getApplicationContext()).getAllQuestions();     //listeye yerlestirme
        questionCountTotal = questionList.size();           //toplam soru bulma
        Collections.shuffle(questionList);                  //soru listesini karisik listeleme

        showNextQuestion();                                 //siradaki soruya gec

        buttonConfirmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answered)
                {
                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked() || rb5.isChecked()){
                        checkAnswer();
                    }else{
                        Toast.makeText(KtTestActivity.this, "Lütfen seçim yapın", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    KisilikDB.getINSTANCE(getApplicationContext()).katsayiHesapDB(currentQuestion.getSoru_id());
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
            buttonConfirmNext.setText("Cevabı Onayla");
        }else {     //Bu noktada kisilikler karşılaştırılır ve sonuc sayfasına basmak uzere hazırlanır
            finishQuiz();
        }
    }

    public void  checkAnswer(){                         //Secilen sikkin katsayiyla carpimi burada mı olacak?
        answered = true;

        RadioButton rbSelected = findViewById(rbGroup.getCheckedRadioButtonId()); //secilenin id'yi al
        cevapNo = rbGroup.indexOfChild(rbSelected);        //secilen id'yi cevapNo'de tut
        //youtube dk 9.30 link:(tlgrX3HF6AI)

        if (questionCounter < questionCountTotal){
            buttonConfirmNext.setText("Sıradaki soru");
        }else {
            buttonConfirmNext.setText("Testi bitir");
        }
    }

    public static int cvp(){

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

    public static void hesap(int ks, int kisilikId){

        int cevap_katsayisi = cvp();

        switch(kisilikId){
            case 1: Arastirmaci = Arastirmaci + (ks * cevap_katsayisi);
                break;
            case 2: Barisci += ks * cevap_katsayisi;
                break;
            case 3: BasariOdakli += ks * cevap_katsayisi;
                break;
            case 4: Maceraci += ks * cevap_katsayisi;
                break;
            case 5: MeydanOkuyan += ks * cevap_katsayisi;
                break;
            case 6: Mukemmelmeliyetci += ks * cevap_katsayisi;
                break;
            case 7: Ozgun += ks * cevap_katsayisi;
                break;
            case 8: Sorgulayici += ks * cevap_katsayisi;
                break;
            case 9: Yardimsever += ks * cevap_katsayisi;
                break;
        }

        int deneme = 22;
    }

    private void finishQuiz() {       //gecisten once kisilik puanlarını karsılastıran metodu calıstırıyor
        kisilikPuani();
        Intent intent = new Intent(KtTestActivity.this, KtSonucEkrani.class);
        startActivity(intent);
    }

    public void kisilikPuani(){
        int liste[] = new int[9];

        liste[0] = Arastirmaci;
        liste[1] = Barisci;
        liste[2] = BasariOdakli;
        liste[3] = Maceraci;
        liste[4] = MeydanOkuyan;
        liste[5] = Mukemmelmeliyetci;
        liste[6] = Ozgun;
        liste[7] = Sorgulayici;
        liste[8] = Yardimsever;

        int ilk = 0;
        ilkKisilikId = 0;

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
    public void onBackPressed() {           //2sn icinde iki defa geri basarsa cik yoksa teste devam
        if (backPressedTime + 2000 > System.currentTimeMillis()){
            finish();
        }else {
            Toast.makeText(this, "Çıkmak için tekrar geriye basın", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();
    }
}
