package com.kodlar.buhar.ui.iceceklerpcg;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.database.FirebaseDatabase;
import com.kodlar.buhar.AnaEkran;

import com.kodlar.buhar.R;

public class icecekler2 extends AppCompatActivity {


    FirebaseDatabase db;

    private ImageButton Anasayfadonus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icecekler2);
        Anasayfadonus = (ImageButton) findViewById(R.id.Anasayfadonus);
        db= FirebaseDatabase.getInstance();

        iceceklerSectionsPagerAdapter iceceklerSectionsPagerAdapter = new iceceklerSectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(iceceklerSectionsPagerAdapter);
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
        Anasayfadonus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(icecekler2.this, AnaEkran.class);

                startActivity(intent);

            }
        });

    }
   /* public void urunlisteleme() {

        DatabaseReference okuma = db.getReference("Kampus").child("icecekler").child("Su");
        okuma.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> keys = snapshot.getChildren();
                for (DataSnapshot key : keys) {

                    gor.append(key.getValue().toString() + " \n");

                    Picasso.with(icecekler2.this).load(gorsel.toString()).into(gorsel);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }*/

}