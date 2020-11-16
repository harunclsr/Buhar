package com.kodlar.buhar;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class GirisEkrani extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private  EditText Email;
    private EditText Password;
    private TextView Info;
    private Button Login;
    private Button Kayit;
    private int counter=3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        Email = (EditText)findViewById(R.id.userEmail);
        Password = (EditText)findViewById(R.id.userSifre);
        Info = (TextView) findViewById(R.id.tvInfo);
        Login = (Button) findViewById(R.id.userGiris);
        Info.setText("Giris Hakkı=3");
        Kayit = (Button) findViewById(R.id.B_Kayit);

        Kayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent( GirisEkrani.this, KullaniciKayit.class);
                startActivity(intent);
            }
        });

        Login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
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
                    Toast.makeText(GirisEkrani.this,"Alanlar boş",Toast.LENGTH_SHORT).show();
                }
                else if(!(userEmail.isEmpty() && userSifre.isEmpty())){
                    mAuth.signInWithEmailAndPassword(userEmail,userSifre).addOnCompleteListener(GirisEkrani.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(GirisEkrani.this,"Giriş Başarısız,Tekrar Deneyin",Toast.LENGTH_SHORT).show();
                            }
                            else{

                                startActivity(new Intent(GirisEkrani.this,AnaEkran.class));
                            }
                        }
                    });

                }
                else {
                    Toast.makeText(GirisEkrani.this,"Hata Oluştu",Toast.LENGTH_SHORT).show();
                }

                }
            });

        }

    }


