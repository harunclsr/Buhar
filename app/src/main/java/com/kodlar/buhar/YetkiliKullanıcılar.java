package com.kodlar.buhar;

public class YetkiliKullanıcılar {
    private String BilgiislemAdi;
    private String BilgiislemSifre;
    private String Bilgiislemid;

    public YetkiliKullanıcılar() {
    }

    public YetkiliKullanıcılar(String bilgiislemAdi, String bilgiislemSifre, String bilgiislemid) {
        BilgiislemAdi = bilgiislemAdi;
        BilgiislemSifre = bilgiislemSifre;
        Bilgiislemid = bilgiislemid;
    }

    public String BilgiislemAdi() {
        return BilgiislemAdi;
    }

    public String getBilgiislemAdi() {
        return BilgiislemAdi;
    }

    public String getBilgiislemid() {
        return Bilgiislemid;
    }

    public void setBilgiislemid(String bilgiislemid) {
        Bilgiislemid = bilgiislemid;
    }

    public void setBilgiislemAdi(String BilgiislemAdi) {
        BilgiislemAdi = BilgiislemAdi;
    }

    public String getBilgiislemSifre() {
        return BilgiislemSifre;
    }

    public void setBilgiislemSifre(String BilgiislemSifre) {
        BilgiislemSifre = BilgiislemSifre;
    }
}
