package com.kodlar.buhar;

public class Adres {
    private String Subeadi,kategori,altkategori;

    public String getSubeadi() {
        return Subeadi;
    }

    public Adres() {
    }

    public Adres(String subeadi, String kategori, String altkategori) {
        Subeadi = subeadi;
        this.kategori = kategori;
        this.altkategori = altkategori;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getAltkategori() {
        return altkategori;
    }

    public void setAltkategori(String altkategori) {
        this.altkategori = altkategori;
    }

    public void setSubeadi(String subeadi) {
        Subeadi = subeadi;
    }
}
