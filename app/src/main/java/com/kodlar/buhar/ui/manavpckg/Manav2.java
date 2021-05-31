package com.kodlar.buhar.ui.manavpckg;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.kodlar.buhar.AnaEkran;
import com.kodlar.buhar.AramaEkrani;
import com.kodlar.buhar.R;
import com.kodlar.buhar.ui.Sepetimpcg.Sepetim;

import android.view.View;
import android.widget.ImageButton;

public class Manav2 extends AppCompatActivity {
    private ImageButton Anasayfadonus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manav2);
        Anasayfadonus = (ImageButton) findViewById(R.id.Anasayfadonusmanav);
        manavSectionsPagerAdapter manavSectionsPagerAdapter = new manavSectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(manavSectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.SepetButonu);

        Anasayfadonus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Manav2.this, AnaEkran.class);

                startActivity(intent);

            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent( Manav2.this, Sepetim.class);

                startActivity(intent);
            }
        });
    }
}