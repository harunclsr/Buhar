package com.kodlar.buhar;

import android.widget.ImageButton;
import android.widget.TextView;

public class Urun {
    public String urunadi, urunfiyati, urunagirlik, image,urunid;
public String miktar;

    public String getUrunid() {
        return urunid;
    }

    public void setUrunid(String urunid) {
        this.urunid = urunid;
    }

    public Urun(String image) {
        this.image = image;
    }

    public String getMiktar() {
        return miktar;
    }

    public Urun(String urunadi, String urunfiyati, String urunagirlik, String image, String urunid, String miktar) {
        this.urunadi = urunadi;
        this.urunfiyati = urunfiyati;
        this.urunagirlik = urunagirlik;
        this.image = image;
        this.urunid = urunid;
        this.miktar = miktar;
    }

    public void setMiktar(String miktar) {
        this.miktar = miktar;
    }

    public Urun() {

    }

    public Urun(String urunadi, String urunfiyati, String urunagirlik, String image, String urunid) {
        this.urunadi = urunadi;
        this.urunfiyati = urunfiyati;
        this.urunagirlik = urunagirlik;
        this.image = image;
        this.urunid = urunid;
    }

    public Urun(String urunadi, String urunfiyati, String urunagirlik, String image) {
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

    public String getUrunfiyati() {
        return urunfiyati;
    }

    public void setUrunfiyati(String urunfiyati) {
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