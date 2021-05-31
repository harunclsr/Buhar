package com.kodlar.buhar;

public class User {
    private String KAdsoyad;
    private String Adres;
    private String TelefonNo;
    private String image;
    private String Userid;

    public String getUserid() {
        return Userid;
    }

    public void setUserid(String userid) {
        Userid = userid;
    }

    public String getKAdsoyad() {
        return KAdsoyad;
    }

    public User(String KAdsoyad, String adres, String telefonNo) {
        this.KAdsoyad = KAdsoyad;
        Adres = adres;
        TelefonNo = telefonNo;

    }

    public User() {
    }

    public void setKAdsoyad(String KAdsoyad) {
        this.KAdsoyad = KAdsoyad;
    }

    public String getAdres() {
        return Adres;
    }

    public void setAdres(String adres) {
        Adres = adres;
    }

    public String getTelefonNo() {
        return TelefonNo;
    }

    public void setTelefonNo(String telefonNo) {
        TelefonNo = telefonNo;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
