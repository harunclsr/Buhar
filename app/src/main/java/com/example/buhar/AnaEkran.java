package com.example.buhar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.content.Intent;

public class AnaEkran extends AppCompatActivity {

    private ImageButton Arama;
    private ImageButton Icecekler;
    private ImageButton Manav;
    private ImageButton Kisisel;
    private ImageButton Temizlik;
    private ImageButton Et;
    private ImageButton Ekmek;
    private ImageButton Atistirmalik;
    private ImageButton Kahvaltilik;
    private ImageButton Temel_Gida;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ana_ekran);

        Arama = (ImageButton)findViewById(R.id.arama);
        Icecekler = (ImageButton)findViewById(R.id.icecekler);
        Manav= (ImageButton)findViewById(R.id.manav);
        Kisisel=(ImageButton)findViewById(R.id.k_bakim);
        Temizlik=(ImageButton)findViewById(R.id.T_urunleri);
        Et=(ImageButton)findViewById(R.id.et);
        Ekmek=(ImageButton)findViewById(R.id.ekmek);
        Atistirmalik=(ImageButton)findViewById(R.id.atistirmalik);
        Kahvaltilik=(ImageButton)findViewById(R.id.kahvaltilik);
        Temel_Gida=(ImageButton)findViewById(R.id.temel_gida);

        Arama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent( AnaEkran.this, AramaEkrani.class);

                startActivity(intent);

            }
        });
        Icecekler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent( AnaEkran.this, Icecekler.class);

                startActivity(intent);

            }
        });
        Manav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent( AnaEkran.this, Manav.class);

                startActivity(intent);

            }
        });
        Kisisel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent( AnaEkran.this, Kisisel.class);

                startActivity(intent);

            }
        });  Temizlik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent( AnaEkran.this, Temizlik.class);

                startActivity(intent);

            }
        });  Et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent( AnaEkran.this, Et.class);

                startActivity(intent);

            }
        });  Ekmek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent( AnaEkran.this, Ekmek.class);

                startActivity(intent);

            }
        });  Atistirmalik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent( AnaEkran.this, Atistirmalik.class);

                startActivity(intent);

            }
        });  Kahvaltilik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent( AnaEkran.this, Kahvaltilik.class);

                startActivity(intent);

            }
        });  Temel_Gida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent( AnaEkran.this, Temel_Gida.class);

                startActivity(intent);

            }
        });
    }
}