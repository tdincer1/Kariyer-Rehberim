package com.squad.testdeneme;


public class Question {
    private String question;
    private int katsayi;
    private int soru_id;

    public Question (){}

    public Question(String question, int katsayi, int soru_id) {
        this.question = question;
        this.katsayi = katsayi;
        this.soru_id = soru_id;
    }


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getKatsayi() {
        return katsayi;
    }

    public void setKatsayi(int katsayi) {
        this.katsayi = katsayi;
    }

    public int getSoru_id() {
        return soru_id;
    }

    public void setSoru_id(int soru_id) {
        this.soru_id = soru_id;
    }
}
