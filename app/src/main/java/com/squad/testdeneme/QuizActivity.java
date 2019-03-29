package com.squad.testdeneme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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
    static int isletme;
    static int Konser;
    static int Makine;
    static int Gastronomi;
    static int Saglik;
    static int Spor;
    static int Tasarım;

    static int answerNr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        textViewQuestion = findViewById(R.id.text_view_question);
        textViewQuestionCount = findViewById(R.id.text_view_question_count);
        rbGroup = findViewById(R.id.radio_group);
        rb1 = findViewById(R.id.radio_button1);
        rb2 = findViewById(R.id.radio_button2);
        rb3 = findViewById(R.id.radio_button3);
        rb4 = findViewById(R.id.radio_button4);
        rb5 = findViewById(R.id.radio_button5);
        buttonConfirmNext = findViewById(R.id.button_confirm_next);

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

    public static void hesap(int ks, int gId){

        //int denden = checkAnswer().answerNr;
        int cevap_katsayısı = cvp();
        //int cevap_katsayısı = secim();

        switch(gId){
            case 1: bilgisayar_puani = bilgisayar_puani + (ks * cevap_katsayısı);
                break;
            case 2: egitim_puani += ks * cevap_katsayısı;
                break;
            case 3: elektronik_puani += ks * cevap_katsayısı;
                break;
            case 4: Harita += ks * cevap_katsayısı;
                break;
            case 5: Uzay += ks * cevap_katsayısı;
                break;
            case 6: Hukuk += ks * cevap_katsayısı;
                break;
            case 7: Toplum += ks * cevap_katsayısı;
                break;
            case 8: isletme += ks * cevap_katsayısı;
                break;
            case 9: Konser += ks * cevap_katsayısı;
                break;
            case 10: Makine += ks * cevap_katsayısı;
                break;
            case 11: Gastronomi += ks * cevap_katsayısı;
                break;
            case 12: Saglik += ks * cevap_katsayısı;
                break;
            case 13: Spor += ks * cevap_katsayısı;
                break;
            case 14: Tasarım += ks * cevap_katsayısı;
                break;
        }

        int deneme = 22;

    }

    public void showNextQuestion() {                       //siradaki soruya gec
        rbGroup.clearCheck();                               //secimi sifirla

        if (questionCounter < questionCountTotal)
        {
            currentQuestion = questionList.get(questionCounter);

            textViewQuestion.setText(currentQuestion.getQuestion());
            //hesapla(currentQuestion.getSoru_id());                   //TODO: burada cok mantıklı degil cunku şık secimi henuz yapılmadı

            questionCounter++;
            textViewQuestionCount.setText("Soru: " + questionCounter + "/" + questionCountTotal);   //Mevcut soruyu bastirma
            answered = false;
            buttonConfirmNext.setText("Cevabi Onayla");
        }else {
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

    private void finishQuiz() {
        finish();
    }

    @Override
    public void onBackPressed() {           //2sn icinde iki defa geri basarsa cik yoksa teste devam
        if (backPressedTime + 2000 > System.currentTimeMillis()){
            finishQuiz();
        }else {
            Toast.makeText(this, "Cikmak icin tekrar geriye basin", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();
    }


}
