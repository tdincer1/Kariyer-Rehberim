package com.squad.testdeneme;


public class Question {     //Kisilik ve Meslek testimize, veritabanindan sorulari cekerken
                            //bu nesnemizi kullanıyoruz.

    private String question;
    private int soru_id;

    public Question (){}

    public Question(String question, int soru_id) {
        this.question = question;
        this.soru_id = soru_id;
    }


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }


    public int getSoru_id() {
        return soru_id;
    }

    public void setSoru_id(int soru_id) {
        this.soru_id = soru_id;
    }
}
