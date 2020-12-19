package com.kodlar.buhar;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Sepet extends AppCompatActivity {

    private ImageButton sepetArti;
    private ImageButton sepetEksi;
    private TextView urunMiktari;
    private int miktar = 0;
    private DatabaseReference SepetRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Urun urun = new Urun();
        sepetArti = (ImageButton) findViewById(R.id.sepetarti);
        sepetEksi = (ImageButton) findViewById(R.id.sepeteksi);
        urunMiktari = (TextView) findViewById(R.id.sepettext);
    urunMiktari.setText("deneme");
    }
}
