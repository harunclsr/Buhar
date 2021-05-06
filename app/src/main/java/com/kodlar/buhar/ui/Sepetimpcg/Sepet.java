package com.kodlar.buhar.ui.Sepetimpcg;


import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kodlar.buhar.AnaEkran;
import com.kodlar.buhar.ProfilEkrani;
import com.kodlar.buhar.R;
import com.kodlar.buhar.SepetController;
import com.kodlar.buhar.Urun;
import com.kodlar.buhar.ui.iceceklerpcg.Su;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;

public class Sepet extends Fragment {
    private View SepetView;
    private RecyclerView mySepetlist;
    private TextView uruntutarmiktari;
    private ImageView imageView;
    private DatabaseReference SepetRef,SepetTutarRef;
    private String currentUserID;
    private FirebaseAuth mAuth;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        SepetView= inflater.inflate(R.layout.sepet_layout,container,false);

        mySepetlist=(RecyclerView) SepetView.findViewById(R.id.sepet_list);
        mySepetlist.setLayoutManager(new LinearLayoutManager(getContext()));
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        uruntutarmiktari=(TextView) SepetView.findViewById(R.id.sepettutarucreti);



         SepetRef = FirebaseDatabase.getInstance().getReference().child("Kullanıcılar").child(currentUserID).child("Sepet").child("Urunlistesi");
        SepetTutarRef = FirebaseDatabase.getInstance().getReference().child("Kullanıcılar").child(currentUserID).child("Sepet").child("SepetTutari");


Tutarhesaplama();
        return SepetView;
    }

    private void Tutarhesaplama() {

        SepetTutarRef.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {

                Integer TUTAR = dataSnapshot.child("TUTAR").getValue(Integer.class);

                uruntutarmiktari.setText(""+TUTAR);





            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public void onStart(){
        super.onStart();
        FirebaseRecyclerOptions options=
                new FirebaseRecyclerOptions.Builder<Urun>()
                        .setQuery(SepetRef,Urun.class)
                        .build();




        final FirebaseRecyclerAdapter<Urun, SepetViewHolder> adapter = new FirebaseRecyclerAdapter<Urun, SepetViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final SepetViewHolder SepetViewHolder, int i, @NonNull Urun urun) {

                String userIDs = getRef(i).getKey();

                SepetController sepetislemleri = new SepetController();
                SepetRef.child(userIDs).addValueEventListener(new ValueEventListener(){

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        String urunfotografi = dataSnapshot.child("image").getValue(String.class);
                        String urunadi = dataSnapshot.child("urunadi").getValue(String.class);
                        String urunagirlik = dataSnapshot.child("urunagirlik").getValue(String.class);
                        Integer urunfiyat = dataSnapshot.child("urunfiyati").getValue(Integer.class);
                        Integer miktar = dataSnapshot.child("miktar").getValue(Integer.class);
                        String urunid = dataSnapshot.child("urunid").getValue(String.class);

                        urun.miktar=miktar;
                        urun.urunfiyati=urunfiyat;
                        if (urunfotografi == null || urunadi == null || urunagirlik == null || urunfiyat == null || miktar == null || urunid == null||SepetViewHolder.urunfotografi==null) {

                            Snackbar.make(SepetView, "Sepetinizde ürün bulunmamaktadır!", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        } else {

                            SepetViewHolder.urunadi.setText(urunadi);
                            SepetViewHolder.urunfiyat.setText("" + urunfiyat);
                            SepetViewHolder.urunagirlik.setText(urunagirlik);
                            SepetViewHolder.sepettext.setText(miktar.toString());
                            Picasso.get().load(urunfotografi.toString()).into(SepetViewHolder.urunfotografi);

                            SepetViewHolder.sepetArti.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    DatabaseReference SepetRef;
                                    SepetRef = FirebaseDatabase.getInstance().getReference().child("Kullanıcılar").child(currentUserID).child("Sepet").child("Urunlistesi");
                                    if (miktar == 0) {

                                        urun.miktar++;
                                        urun.setMiktar(miktar);


                                        Tutarhesaplama();
                                        SepetRef.child(userIDs).child("miktar").setValue(urun.miktar);

                                        Snackbar.make(SepetView, "Sepetinize ürün eklendi.", Snackbar.LENGTH_LONG)
                                                .setAction("Action", null).show();
                                        int tutar = +urunfiyat * urun.miktar;
                                        sepetislemleri.setToplamFiyat(tutar);
                                        urun.setUruntutari(tutar);
                                        SepetTutarRef.child("TUTAR").setValue(  sepetislemleri.getToplamFiyat());

                                    } else {
                                        urun.miktar++;

                                        Tutarhesaplama();
                                        SepetRef.child(userIDs).child("miktar").setValue(urun.miktar);

                                        int tutar=+ urunfiyat* urun.miktar;
                                        urun.setUruntutari(tutar);
                                        sepetislemleri.setToplamFiyat(tutar);
                                        SepetTutarRef.child("TUTAR").setValue(  sepetislemleri.getToplamFiyat());
                                    }

                                }
                            });


                            SepetViewHolder.sepetEksi.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    DatabaseReference SepetRef;
                                    SepetRef = FirebaseDatabase.getInstance().getReference().child("Kullanıcılar").child(currentUserID).child("Sepet").child("Urunlistesi");
                                    if (urun.miktar < 2) {

                                        SepetRef.child(userIDs).removeValue();
                                        int tutar=+ urunfiyat* urun.miktar;
                                        sepetislemleri.setToplamFiyat(tutar);
                                        SepetTutarRef.child("TUTAR").setValue(  sepetislemleri.getToplamFiyat());
                                    } else {

                                        urun.miktar--;
                                        int tutar=+ urunfiyat* urun.miktar;
                                        sepetislemleri.setToplamFiyat(tutar);
                                        SepetRef.child(userIDs).child("miktar").setValue(urun.miktar);
                                        SepetViewHolder.sepettext.setText("" + urun.miktar);
                                        SepetTutarRef.child("TUTAR").setValue(  sepetislemleri.getToplamFiyat());

                                    }

                                }
                            });

                        }


                    }




                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Snackbar.make(SepetView, "HATA!!!!!!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });



            }

            @NonNull
            @Override
            public SepetViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.urun,viewGroup,false);
                SepetViewHolder viewHolder = new SepetViewHolder(view);
                return viewHolder;
            }

        };
        mySepetlist.setAdapter(adapter);
        adapter.startListening();

    }

    public static class SepetViewHolder extends RecyclerView.ViewHolder{
        private ImageButton sepetArti;
        private ImageButton sepetEksi;
        private TextView sepettext;
        private TextView UcretToplami;

        private int miktar ;
        TextView urunadi,urunfiyat,urunagirlik;
        ImageFilterView urunfotografi;

        public SepetViewHolder(@NonNull View itemView) {
            super(itemView);

            sepetArti = itemView.findViewById(R.id.sepetarti);
            sepetEksi = itemView.findViewById(R.id.sepeteksi);
            sepettext =itemView.findViewById(R.id.sepettext);
            urunadi= itemView.findViewById(R.id.urunAdi);
            urunfiyat= itemView.findViewById(R.id.urunFiyat);
            urunagirlik= itemView.findViewById(R.id.urunAgirlik);
            urunfotografi=itemView.findViewById(R.id.urunfotografi);
            sepettext=itemView.findViewById(R.id.sepettext);


        }






    }





}
