package com.kodlar.buhar.ui.Sepetimpcg;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kodlar.buhar.AnaEkran;
import com.kodlar.buhar.R;
import com.kodlar.buhar.Urun1;

public class Sepetim extends AppCompatActivity {
    private ImageButton Anasayfadonuskisisel;
    private DatabaseReference SepetRef;
    private String currentUserID;
    private FirebaseAuth mAuth;
    private TextView UrunTutar;
    private int ucret;
    int sayac=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sepetim_activity);

        Anasayfadonuskisisel = (ImageButton) findViewById(R.id.AnasayfadonusK);
        SepetimSectionsPagerAdapter sepetimSectionsPagerAdapter = new SepetimSectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sepetimSectionsPagerAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UrunTutar = (TextView) findViewById(R.id.deneme123);
        SepetRef = FirebaseDatabase.getInstance().getReference().child("Kullanıcılar").child(currentUserID).child("Sepet").child("Urunlistesi");



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Anasayfadonuskisisel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sepetim.this, AnaEkran.class);
                startActivity(intent);

            }
        });



}
    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions options =
                new FirebaseRecyclerOptions.Builder<Urun1>()
                        .setQuery(SepetRef, Urun1.class)
                        .build();

        final FirebaseRecyclerAdapter<Urun1,SepetimViewHolder> adapter = new FirebaseRecyclerAdapter<Urun1, SepetimViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final SepetimViewHolder SepetimViewHolder, int i, @NonNull Urun1 urun) {

                String userIDs = getRef(i).getKey();

                SepetRef.child(userIDs).addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        Integer urunfiyat = dataSnapshot.child("urunfiyati").getValue(Integer.class);
                        Integer miktar = dataSnapshot.child("miktar").getValue(Integer.class);




                    }



                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
              });
                   }




            @NonNull
            @Override
            public SepetimViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.urun1, viewGroup, false);
                SepetimViewHolder viewHolder = new SepetimViewHolder(view);
                return viewHolder;
            }
        };



    }




    public static class SepetimViewHolder extends RecyclerView.ViewHolder{

        TextView urunadi,urunfiyat,urunagirlik;
        ImageFilterView urunfotografi;
        private ImageButton sepetArti;
        private ImageButton sepetEksi;
        private TextView sepettext;
        private int miktar ;

        public SepetimViewHolder(@NonNull View itemView) {
            super(itemView);
            urunadi= itemView.findViewById(R.id.urunAdi);
            urunfiyat= itemView.findViewById(R.id.urunFiyat);
            urunagirlik= itemView.findViewById(R.id.urunAgirlik);
            urunfotografi=itemView.findViewById(R.id.urunfotografi);
            sepetArti = itemView.findViewById(R.id.sepetarti);
            sepettext=itemView.findViewById(R.id.sepettext);
            sepetEksi = itemView.findViewById(R.id.sepeteksi);

        }
    }
}
