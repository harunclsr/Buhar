package com.kodlar.buhar;

public class SubeKullanicilar {
    private String SubeGorevlisiAdi;
    private String SubeGorevlisiSifre;
    private String SubeGorevlisiid;

    public SubeKullanicilar() {
    }

    public SubeKullanicilar(String subeGorevlisiAdi, String subeGorevlisiSifre, String subeGorevlisiid) {
        SubeGorevlisiAdi = subeGorevlisiAdi;
        SubeGorevlisiSifre = subeGorevlisiSifre;
        SubeGorevlisiid = subeGorevlisiid;
    }

    public String getSubeGorevlisiAdi() {
        return SubeGorevlisiAdi;
    }

    public void setSubeGorevlisiAdi(String subeGorevlisiAdi) {
        SubeGorevlisiAdi = subeGorevlisiAdi;
    }

    public String getSubeGorevlisiSifre() {
        return SubeGorevlisiSifre;
    }

    public void setSubeGorevlisiSifre(String subeGorevlisiSifre) {
        SubeGorevlisiSifre = subeGorevlisiSifre;
    }

    public String getSubeGorevlisiid() {
        return SubeGorevlisiid;
    }

    public void setSubeGorevlisiid(String subeGorevlisiid) {
        SubeGorevlisiid = subeGorevlisiid;
    }
}
