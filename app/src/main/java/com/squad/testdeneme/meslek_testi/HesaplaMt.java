package com.squad.testdeneme.meslek_testi;

public class HesaplaMt {

    private int katsayi;
    private int soruId;
    private int grupId;

    public HesaplaMt(){}

    public HesaplaMt(int katsayi, int soruId, int grupId) {
        this.katsayi = katsayi;
        this.soruId = soruId;
        this.grupId = grupId;
    }

    public int getKatsayi() {
        return katsayi;
    }

    public void setKatsayi(int katsayi) {
        this.katsayi = katsayi;
    }

    public int getSoruId() {
        return soruId;
    }

    public void setSoruId(int soruId) {
        this.soruId = soruId;
    }

    public int getGrupId() {
        return grupId;
    }

    public void setGrupId(int grupId) {
        this.grupId = grupId;
    }
}
