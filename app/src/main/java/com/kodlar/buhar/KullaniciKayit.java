package com.kodlar.buhar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class KullaniciKayit extends AppCompatActivity {
    private ImageButton B_Geri;
    private Button Kaydet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kullanici_kayit);
        B_Geri = (ImageButton) findViewById(R.id.B_Geri);
        Kaydet = (Button) findViewById(R.id.Kaydet);
        B_Geri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent( KullaniciKayit.this, MainActivity.class);

                startActivity(intent);

            }
        });
        Kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent( KullaniciKayit.this, MainActivity.class);

                startActivity(intent);

            }
        });
    }
}