package com.kodlar.buhar;

import com.google.common.collect.ArrayListMultimap;

import java.util.ArrayList;

public class SepetController {

    private int miktar,fiyat,ToplamFiyat;

    public int getMiktar() {
        return miktar;
    }



    public SepetController() {
    }

    public SepetController(int toplamFiyat) {
        ToplamFiyat = toplamFiyat;
    }

    public void setMiktar(int miktar) {
        this.miktar = miktar;
    }



    public int getFiyat() {
        return fiyat;
    }

    public void setFiyat(int fiyat) {
        this.fiyat = fiyat;
    }

    public int getToplamFiyat() {
        return ToplamFiyat;
    }

    public void setToplamFiyat(int toplamFiyat) {
        ToplamFiyat = toplamFiyat;
    }


}
