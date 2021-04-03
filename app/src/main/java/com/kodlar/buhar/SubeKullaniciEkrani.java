package com.kodlar.buhar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class SubeKullaniciEkrani extends AppCompatActivity {
    private Button Urunekleme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sube_kullanici_ekrani);
        Urunekleme=(Button)findViewById(R.id.eklemeEkrani);
        Urunekleme.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(SubeKullaniciEkrani.this,UrunEkleme.class);
                startActivity(intToMain);
            }

        });
    }

}