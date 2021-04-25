package com.kodlar.buhar;

import android.widget.ImageButton;
import android.widget.TextView;

public class Urun {
    public String urunadi, urunagirlik, image,urunid;
public int miktar, urunfiyati;

    public String getUrunid() {
        return urunid;
    }

    public void setUrunid(String urunid) {
        this.urunid = urunid;
    }

    public Urun(String image) {
        this.image = image;
    }



    public Urun(String urunadi, int urunfiyati, String urunagirlik, String image, String urunid, int miktar) {
        this.urunadi = urunadi;
        this.urunfiyati = urunfiyati;
        this.urunagirlik = urunagirlik;
        this.image = image;
        this.urunid = urunid;
        this.miktar = miktar;
    }

    public int getMiktar() {
        return miktar;
    }

    public void setMiktar(int miktar) {
        this.miktar = miktar;
    }

    public Urun() {

    }

    public Urun(String urunadi, String urunagirlik, int urunfiyati, String image, String urunid) {
        this.urunadi = urunadi;
        this.urunfiyati = urunfiyati;
        this.urunagirlik = urunagirlik;
        this.image = image;
        this.urunid = urunid;
    }

    public Urun(String urunadi, int urunfiyati, String urunagirlik, String image) {
        this.urunadi = urunadi;
        this.urunfiyati = urunfiyati;
        this.urunagirlik = urunagirlik;
        this.image = image;
    }

    public String getUrunadi() {
        return urunadi;
    }



    public void setUrunadi(String urunadi) {
        this.urunadi = urunadi;
    }

    public int getUrunfiyati() {
        return urunfiyati;
    }

    public void setUrunfiyati(int urunfiyati) {
        this.urunfiyati = urunfiyati;
    }

    public String getUrunagirlik() {
        return urunagirlik;
    }

    public void setUrunagirlik(String urunagirlik) {
        this.urunagirlik = urunagirlik;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}