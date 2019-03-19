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
                        //hesapla(currentQuestion.getSoru_id());                TODO: Buraya da konulabilir
                    }else{
                        Toast.makeText(QuizActivity.this, "Lutfen secim yapin", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    //hesapla(currentQuestion.getSoru_id());                TODO: Veya buraya da konulabilir. Soru cevaplandiktan sonra
                    showNextQuestion();
                }

            }
        });
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
        int answerNr = rbGroup.indexOfChild(rbSelected);        //secilen id'yi answerNr'de tut
        //youtube dk 9.30 link:(tlgrX3HF6AI)

        if (questionCounter < questionCountTotal){
            buttonConfirmNext.setText("Siradaki soru");
        }else {
            buttonConfirmNext.setText("Testi bitir");
        }

    }


/*
    public void hesapla(int soru_id) {
        //QuizDbHelper.getHesap();

        int deneme = checkAnswer();





        String query = "SELECT * FROM mt_sorumeslek where soru_id="+ soru_id ;
        //answerNr
    }
*/
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
