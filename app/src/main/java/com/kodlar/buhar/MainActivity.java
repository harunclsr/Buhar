package com.kodlar.buhar;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText Email;
    private EditText Password;
    private TextView Info;
    private Button Login;
    private Button Kayit;
    private int counter=3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Email = (EditText)findViewById(R.id.userEmail);
        Password = (EditText)findViewById(R.id.userSifre);
        Info = (TextView) findViewById(R.id.tvInfo);
        Login = (Button) findViewById(R.id.userGiris);
        Info.setText("Giris Hakkı=3");
        Kayit = (Button) findViewById(R.id.B_Kayit);

        Kayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent( MainActivity.this, KullaniciKayit.class);

                startActivity(intent);

            }
        });
        Login.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view){
                validate (Email.getText().toString(),Password.getText().toString());

            }
        });

    }

    private void validate( String userEmail, String userSifre){

        if((userEmail.equals("Admin")) && (userSifre.equals("1234"))){
            Intent intent= new Intent(MainActivity.this, AnaEkran.class);

            startActivity(intent);
        }else{
            counter--;

            Info.setText("Giriş Hakkı="+String.valueOf(counter));
            if(counter== 0){
                Login.setEnabled(false);
            }
        }
    }

}