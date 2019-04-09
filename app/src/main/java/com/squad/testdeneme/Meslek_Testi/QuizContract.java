package com.squad.testdeneme.Meslek_Testi;

import android.provider.BaseColumns;

public final class QuizContract {

    private QuizContract(){}        //istenmeden obje olusturmayi engellemek adina yazdik

    public static class QuestionsTable implements BaseColumns {
        public static final String TABLE_NAME = "mt_soru";
        public static final String COLUMN_QUESTION = "soru_detay";
    }
}
