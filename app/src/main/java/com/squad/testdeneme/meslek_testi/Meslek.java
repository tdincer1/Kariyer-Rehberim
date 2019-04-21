package com.squad.testdeneme.meslek_testi;

public class Meslek {

    private int meslek_id;
    private String meslek_adi;
    private String meslek_detay;
    private int grup_id;

    public Meslek(){}

    public Meslek(int meslek_id, String meslek_adi, String meslek_detay, int grup_id) {
        this.meslek_id = meslek_id;
        this.meslek_adi = meslek_adi;
        this.meslek_detay = meslek_detay;
        this.grup_id = grup_id;
    }

    public int getMeslek_id() {
        return meslek_id;
    }

    public void setMeslek_id(int meslek_id) {
        this.meslek_id = meslek_id;
    }

    public String getMeslek_adi() {
        return meslek_adi;
    }

    public void setMeslek_adi(String meslek_adi) {
        this.meslek_adi = meslek_adi;
    }

    public String getMeslek_detay() {
        return meslek_detay;
    }

    public void setMeslek_detay(String meslek_detay) {
        this.meslek_detay = meslek_detay;
    }

    public int getGrup_id() {
        return grup_id;
    }

    public void setGrup_id(int grup_id) {
        this.grup_id = grup_id;
    }
}
