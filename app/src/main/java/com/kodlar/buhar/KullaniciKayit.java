package com.kodlar.buhar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class KullaniciKayit extends AppCompatActivity {
    private ImageButton B_Geri;
    private Button Kaydet;
    private EditText Email;
    private EditText Password;

    FirebaseAuth mAuth;




    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kullanici_kayit);
        B_Geri = (ImageButton) findViewById(R.id.B_Geri);
        Kaydet = (Button) findViewById(R.id.Kaydet);
        mAuth = FirebaseAuth.getInstance();



        Email = (EditText)findViewById(R.id.userEmail);
        Password = (EditText)findViewById(R.id.userSifre);







       mAuthStateListener= new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mAuth.getCurrentUser();
                if(mFirebaseUser !=  null){
                    Toast.makeText(KullaniciKayit.this,"Kayıt Oluşturuldu.",Toast.LENGTH_LONG).show();

                }
                else {
                    Toast.makeText(KullaniciKayit.this, "Lütfen Giris yapın", Toast.LENGTH_SHORT).show();
                 /*   Intent i = new Intent( KullaniciKayit.this,GirisEkrani.class);
                    startActivity(i);*/
                }
            }
        };


        B_Geri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent( KullaniciKayit.this, GirisEkrani.class);

                startActivity(intent);

            }
        });
        Kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = Email.getText().toString();
                String userSifre = Password.getText().toString();




                if(userEmail.isEmpty()){
                    Email.setError("Lütfen mail adresinizi girin");
                    Email.requestFocus();
                }
                else if(userSifre.isEmpty()){
                    Password.setError("Lütfen şifrenizi girin");
                    Password.requestFocus();
                }
                else if(userEmail.isEmpty() && userSifre.isEmpty()){
                    Toast.makeText(KullaniciKayit.this,"Alanlar boş",Toast.LENGTH_SHORT).show();
                }
                else if(!(userEmail.isEmpty() && userSifre.isEmpty())){
                    mAuth.createUserWithEmailAndPassword(userEmail,userSifre).addOnCompleteListener(KullaniciKayit.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(KullaniciKayit.this, "Hatalı Kayıt,Tekrar deneyiniz", Toast.LENGTH_SHORT).show();
                            } else {

                                Intent intToHome = new Intent(KullaniciKayit.this, GirisEkrani.class);
                                startActivity(intToHome);
                            }
                        }
                    });


                }
                else {
                    Toast.makeText(KullaniciKayit.this,"Hata Oluştu",Toast.LENGTH_SHORT).show();

                }

            }
        });
            }
    @Override
    protected void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

}