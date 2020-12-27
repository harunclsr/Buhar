package com.kodlar.buhar.ui.KisiselBakimpcg;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageButton;

import com.kodlar.buhar.AnaEkran;
import com.kodlar.buhar.R;
import com.kodlar.buhar.ui.atistirmalikpcg.Atistirmalik2;

public class KisiselBakim extends AppCompatActivity {
    private ImageButton Anasayfadonuskisisel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kisisel_bakim);

        Anasayfadonuskisisel = (ImageButton) findViewById(R.id.AnasayfadonusK);
        KisiselSectionsPagerAdapter kisiselSectionsPagerAdapter = new KisiselSectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(kisiselSectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Anasayfadonuskisisel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KisiselBakim.this, AnaEkran.class);
                startActivity(intent);

            }
        });
    }
}