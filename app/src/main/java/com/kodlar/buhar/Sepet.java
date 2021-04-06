package com.kodlar.buhar;


import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;

public class Sepet extends Fragment {
    private View SepetView;
    private RecyclerView mySepetlist;

    private ImageView imageView;
    private DatabaseReference SepetRef;
    private String currentUserID;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        SepetView= inflater.inflate(R.layout.sepet_layout,container,false);

        mySepetlist=(RecyclerView) SepetView.findViewById(R.id.sepet_list);
        mySepetlist.setLayoutManager(new LinearLayoutManager(getContext()));
        mAuth = FirebaseAuth.getInstance();



        // ContacsRef = FirebaseDatabase.getInstance().getReference().child("Kampus").child("icecekler").child("Su").child(currentUserID);
        SepetRef = FirebaseDatabase.getInstance().getReference().child("Sepet").child("Urunlistesi");



        return SepetView;
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

                SepetRef.child(userIDs).addValueEventListener(new ValueEventListener(){

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {

                        String urunfotografi = dataSnapshot.child("image").getValue(String.class);
                        String urunadi = dataSnapshot.child("urunadi").getValue(String.class);
                        String urunagirlik = dataSnapshot.child("urunagirlik").getValue(String.class);
                        String urunfiyat = dataSnapshot.child("urunfiyati").getValue(String.class);
                        String miktar = dataSnapshot.child("miktar").getValue(String.class);
                        SepetViewHolder.urunadi.setText(urunadi);
                        SepetViewHolder.urunfiyat.setText(urunfiyat);
                        SepetViewHolder.urunagirlik.setText(urunagirlik);
                        SepetViewHolder.sepettext.setText(miktar);
                        Picasso.get().load(urunfotografi.toString()).into(SepetViewHolder.urunfotografi);
                        Urun  urunleri= dataSnapshot.getValue(Urun.class);


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

        private String miktar = "0";
        private int i=0;
        TextView urunadi,urunfiyat,urunagirlik;
        ImageFilterView urunfotografi;

        private DatabaseReference GenelRef,SepetRef;

        public SepetViewHolder(@NonNull View itemView) {
            super(itemView);

            sepetArti = itemView.findViewById(R.id.sepetarti);
            sepetEksi = itemView.findViewById(R.id.sepeteksi);
            sepettext =itemView.findViewById(R.id.sepettext);
            urunadi= itemView.findViewById(R.id.urunAdi);
            urunfiyat= itemView.findViewById(R.id.urunFiyat);
            urunagirlik= itemView.findViewById(R.id.urunAgirlik);
            urunfotografi=itemView.findViewById(R.id.urunfotografi);

            Urun urun = new Urun();
            sepettext.setText(miktar);








/*
        sepetArti.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  i++;
             urunMiktari.setText(""+i);


                    StorageReference reference = FirebaseStorage.getInstance().getReference();
                    DatabaseReference GenelRef,SepetRef;
                    View SepetView = null;
                    Uri imageUri;
                    GenelRef=FirebaseDatabase.getInstance().getReference().child("Kampus");
                    SepetRef=FirebaseDatabase.getInstance().getReference().child("Sepet");


                    String urununid="12312";

                    SepetRef.child(urununid).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Urun  urunleri= dataSnapshot.getValue(Urun.class);

                            String urunid = dataSnapshot.child("urunid").getValue(String.class);
                            String urunfotografi = dataSnapshot.child("image").getValue(String.class);
                            String urunadi = dataSnapshot.child("urunadi").getValue(String.class);
                            String urunagirlik = dataSnapshot.child("urunagirlik").getValue(String.class);
                            String urunfiyat = dataSnapshot.child("urunfiyati").getValue(String.class);

                            Urun urunler = new Urun(urunadi,urunfiyat,urunagirlik,urunfotografi,urunid);

                            SepetRef.push().setValue(urunler);
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {


                        }
                    });



                }
            });*/

            sepetArti.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    i++;
                    sepettext.setText(""+i);



                }
            });
        }






    }





}
