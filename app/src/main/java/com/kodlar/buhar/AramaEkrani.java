package com.kodlar.buhar;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.kodlar.buhar.ui.Sepetimpcg.Sepet;
import com.kodlar.buhar.ui.Sepetimpcg.Sepetim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageButton;

public class AramaEkrani extends AppCompatActivity {

    private ImageButton Anasayfadonus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arama_ekrani);
        Anasayfadonus= (ImageButton) findViewById(R.id.anasayfadonus2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.SepetButonu);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent( AramaEkrani.this, Sepetim.class);

                startActivity(intent);
            }
        });
        Anasayfadonus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent( AramaEkrani.this, AnaEkran.class);

                startActivity(intent);

            }
        });
    }
}