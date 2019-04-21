package com.squad.testdeneme.kisilik_testi;

public class HesaplaKt {

    private int katsayi;
    private int soruId;
    private int kisilikId;

    public HesaplaKt(){}

    public HesaplaKt(int katsayi, int soruId, int kisilikId) {
        this.katsayi = katsayi;
        this.soruId = soruId;
        this.kisilikId = kisilikId;
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

    public int getKisilikId() {
        return kisilikId;
    }

    public void setKisilikId(int kisilikId) {
        this.kisilikId = kisilikId;
    }
}
