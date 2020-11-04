package com.kodlar.buhar;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Icecekler extends AppCompatActivity {


    private ImageButton Anasayfadonus;
    private Button Su;
    private TextView gor;
    FirebaseDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icecekler);
        db=FirebaseDatabase.getInstance();
        Anasayfadonus = (ImageButton) findViewById(R.id.Anasayfadonus);
        Su= (Button) findViewById(R.id.Su);
       gor =(TextView) findViewById(R.id.gosterurun);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

        });
        Su.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urunlisteleme();

            }
        });
        Anasayfadonus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Icecekler.this, AnaEkran.class);

                startActivity(intent);

            }
        });

}
    public void urunlisteleme() {
        DatabaseReference okuma = db.getReference("Kampus").child("icecekler").child("Su");
        okuma.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> keys = snapshot.getChildren();
                for (DataSnapshot key : keys) {

                    gor.append(key.getValue().toString() + " \n");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
