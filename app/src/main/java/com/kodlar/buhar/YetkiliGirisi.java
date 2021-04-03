package com.kodlar.buhar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class YetkiliGirisi extends AppCompatActivity {
    EditText Yadi, Ysifre;
    Button YetkiliGirisi;
    RadioButton Bilgiislem, SubeGorevlisi;
    TextView denemeyazisi;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yetkili_girisi);
        Yadi = findViewById(R.id.y_adi);
        Ysifre = findViewById(R.id.y_sifre);
        denemeyazisi=findViewById(R.id.deneme44);
        YetkiliGirisi = findViewById(R.id.Yetkiligirisi);
        Bilgiislem = (RadioButton) findViewById(R.id.Bilgiislemradio);
        SubeGorevlisi = (RadioButton) findViewById(R.id.Subegorevlisiradio);
        ref = FirebaseDatabase.getInstance().getReference().child("YetkiliGirisi").child("Bilgiislem");



        YetkiliGirisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Bilgiislemid = Yadi.getText().toString();
                String YetkiliSifre = Ysifre.getText().toString();


                if (Bilgiislem.isChecked()) {


                    ref.child(Bilgiislemid).addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            YetkiliKullanıcılar kullanici= snapshot.getValue(YetkiliKullanıcılar.class);


                            if (YetkiliSifre.equals(kullanici.getBilgiislemSifre())) {

                                startActivity(new Intent(YetkiliGirisi.this, BilgiislemEkrani.class));
                                Toast.makeText(YetkiliGirisi.this, "Giris Başarılı", Toast.LENGTH_SHORT).show();

                            }
                            else {
                                Toast.makeText(YetkiliGirisi.this, "Giris Hatalı!!", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(YetkiliGirisi.this, "ELSEE", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });

    }
}

