package com.kodlar.buhar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfilEkrani extends AppCompatActivity {

 private TextView Profilid;
 private Button BilgileriGuncelle;
    private FirebaseAuth mAuth;
    private DatabaseReference ProfilBilgileri;
    private String currentUserID;
    private EditText K_Adi;
    private EditText K_Numara;
    private EditText K_Adres;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_ekrani);

        Profilid = findViewById(R.id.Profilid);
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        mAuth=FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        BilgileriGuncelle=(Button)findViewById(R.id.BilgileriGuncelle);
        K_Adi = (EditText)findViewById(R.id.ProfilAd);
        K_Numara = (EditText)findViewById(R.id.ProfilTelefonno);
        K_Adres = (EditText)findViewById(R.id.ProfilAdres);

        ProfilBilgileri = FirebaseDatabase.getInstance().getReference().child("Kullanıcılar").child(currentUserID).child("Profil");


        FirebaseUser user = auth.getCurrentUser();
        Profilid.setText(user.getUid());
      //  ProfilAd.setText(user.getEmail());
     //   ProfilTelefonno.setText(user.getPhoneNumber());
     //   ProfilAdres.setText(user.getTenantId());

BilgileriGuncelle.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        String  Adi= K_Adi.getText().toString();
        String  Numara= K_Numara.getText().toString();
        String  Adres= K_Adres.getText().toString();
        String UserID=currentUserID;


        ProfilBilgileri.setValue(new User(Adi,Adres,Numara));



    }
});
                String userIDs = user.getUid();

                ProfilBilgileri.addValueEventListener(new ValueEventListener(){

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {

                        String Ad = dataSnapshot.child("kadsoyad").getValue(String.class);

                        String Numara = dataSnapshot.child("telefonNo").getValue(String.class);
                        String Adres = dataSnapshot.child("adres").getValue(String.class);




                        K_Adi.setText(Ad);
                        K_Numara.setText(Numara);
                        K_Adres.setText(Adres);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(ProfilEkrani.this, "HATA", Toast.LENGTH_SHORT).show();

                    }
                });




        }




}