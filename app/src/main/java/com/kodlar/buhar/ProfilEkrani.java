package com.kodlar.buhar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kodlar.buhar.ui.Etpcg.BeyazEt;
import com.squareup.picasso.Picasso;

import java.util.concurrent.Future;

public class ProfilEkrani extends AppCompatActivity {
 private TextView ProfilAd,ProfilTelefonno,ProfilAdres,Profilid;
    private FirebaseAuth mAuth;
    private DatabaseReference ProfilBilgileri;
    private String currentUserID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_ekrani);

        ProfilAd = findViewById(R.id.ProfilAd);
        ProfilTelefonno = findViewById(R.id.ProfilTelefonno);
        ProfilAdres = findViewById(R.id.ProfilAdres);
        Profilid = findViewById(R.id.Profilid);
        final FirebaseAuth auth = FirebaseAuth.getInstance();

        ProfilBilgileri = FirebaseDatabase.getInstance().getReference().child("kullanıcılar");


        FirebaseUser user = auth.getCurrentUser();
        Profilid.setText(user.getUid());
      //  ProfilAd.setText(user.getEmail());
        ProfilTelefonno.setText(user.getPhoneNumber());
        ProfilAdres.setText(user.getTenantId());


                String userIDs = user.getUid();

                ProfilBilgileri.child(userIDs).addValueEventListener(new ValueEventListener(){

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        User kullanici=new User();


                       kullanici.setKAdsoyad(dataSnapshot.child("KAdsoyad").getValue(String.class));
                        kullanici.setTelefonNo(dataSnapshot.child("TelefonNo").getValue(String.class));
                        kullanici.setAdres(dataSnapshot.child("Adres").getValue(String.class));


                        ProfilAd.setText(kullanici.getKAdsoyad());
                        ProfilTelefonno.setText(kullanici.getTelefonNo());
                        ProfilAdres.setText(kullanici.getAdres());
                       // Picasso.get().load(image.toString()).into(.urunfotografi);


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(ProfilEkrani.this, "HATA", Toast.LENGTH_SHORT).show();

                    }
                });




        }




}