package com.squad.testdeneme;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.squad.testdeneme.Meslek_Testi.QuizActivity;
import com.squad.testdeneme.Tercih_Robotu.TercihRobotu;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_QUIZ = 1;
    Button buttonStartQuiz;
    Button buttonTercihRobotu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        buttonStartQuiz = findViewById(R.id.button_start_quiz);
        buttonTercihRobotu = findViewById(R.id.button_tercih);

        buttonStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz();
            }
        });

        buttonTercihRobotu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TercihRobotu.class);
                startActivity(intent);
            }
        });
    }

    private void startQuiz() {
        Intent intent = new Intent(MainActivity.this, QuizActivity.class);
        startActivity(intent);
        //startActivityForResult(intent, REQUEST_CODE_QUIZ);
    }
}
