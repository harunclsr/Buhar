package com.kodlar.buhar;

public class Urun {
    public String urunadi,urunfiyati,urunagirlik,image;
    public Urun(){

    }
    public Urun(String urunadi,String urunfiyati,String urunagirlik,String image){
        this.urunadi=urunadi;
        this.urunfiyati=urunfiyati;
        this.urunagirlik=urunagirlik;
        this.image=image;

    }

    public String getUrunadi(){return urunadi;}
    public String urunfiyati(){return urunfiyati;}
    public String urunagirlik(){return urunagirlik;}
    public String image(){return image;}

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
