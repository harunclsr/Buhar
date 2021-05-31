package com.kodlar.buhar.ui.Etpcg;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kodlar.buhar.R;
import com.kodlar.buhar.Urun1;
import com.squareup.picasso.Picasso;

public class KirmiziEt extends Fragment {

    private View KirmiziEtView;
    private RecyclerView myKirmiziEtList;
    private DatabaseReference KirmiziEtRef,SepetRef,SepetTutarRef;
    private FirebaseAuth mAuth;
    private String currentUserID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        KirmiziEtView= inflater.inflate(R.layout.activity_kirmizi_et,container,false);

        myKirmiziEtList=(RecyclerView) KirmiziEtView.findViewById(R.id.kirmiziEt_list);
        myKirmiziEtList.setLayoutManager(new LinearLayoutManager(getContext()));
        mAuth= FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();


        // ContacsRef = FirebaseDatabase.getInstance().getReference().child("Kampus").child("icecekler").child("Su").child(currentUserID);
        KirmiziEtRef = FirebaseDatabase.getInstance().getReference().child("Kampus").child("Et").child("Kırmızı Et");
        SepetRef = FirebaseDatabase.getInstance().getReference().child("Kullanıcılar").child(currentUserID).child("Sepet").child("Urunlistesi");
        SepetTutarRef = FirebaseDatabase.getInstance().getReference().child("Kullanıcılar").child(currentUserID).child("Sepet").child("SepetTutari");



        return KirmiziEtView;
    }
    @Override
    public void onStart(){
        super.onStart();
        FirebaseRecyclerOptions options=
                new FirebaseRecyclerOptions.Builder<Urun1>()
                        .setQuery(KirmiziEtRef, Urun1.class)
                        .build();

        final FirebaseRecyclerAdapter<Urun1, KirmiziEtViewHolder> adapter = new FirebaseRecyclerAdapter<Urun1, KirmiziEtViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final KirmiziEtViewHolder KirmiziEtViewHolder, int i, @NonNull Urun1 urun) {

                String userIDs = getRef(i).getKey();

                KirmiziEtRef.child(userIDs).addValueEventListener(new ValueEventListener(){

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {

                        String urunfotografi = dataSnapshot.child("image").getValue(String.class);
                        String urunadi = dataSnapshot.child("urunadi").getValue(String.class);
                        String urunagirlik = dataSnapshot.child("urunagirlik").getValue(String.class);
                        Integer urunfiyat = dataSnapshot.child("urunfiyati").getValue(Integer.class);
                        String urunid = dataSnapshot.child("urunid").getValue(String.class);

                        KirmiziEtViewHolder.urunadi.setText(urunadi);
                        KirmiziEtViewHolder.urunfiyat.setText(""+urunfiyat);
                        KirmiziEtViewHolder.urunagirlik.setText(urunagirlik);

                        Picasso.get().load(urunfotografi.toString()).into(KirmiziEtViewHolder.urunfotografi);

                        KirmiziEtViewHolder.sepetArti.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {


                                DatabaseReference SepetRef;
                                SepetRef = FirebaseDatabase.getInstance().getReference().child("Kullanıcılar").child(currentUserID).child("Sepet").child("Urunlistesi");
                                KirmiziEtViewHolder.miktar++;

                                SepetRef.child(userIDs).setValue(new Urun1(urunadi, urunagirlik, urunfiyat, urunfotografi, urunid));
                                SepetRef.child(userIDs).child("miktar").setValue(KirmiziEtViewHolder.miktar);
                                SepetRef.child(userIDs).child("uruntutari").setValue(urun.getUruntutari());
                                Snackbar.make(KirmiziEtView, "Sepetinize ürün eklendi.", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();


                                urun.setUruntutari(KirmiziEtViewHolder.miktar * urunfiyat);

                                SepetRef.child(userIDs).child("uruntutari").setValue(urun.getUruntutari());


                                Snackbar.make(KirmiziEtView, "Sepetinize ürün eklendi.", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }


                        });




                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Snackbar.make(KirmiziEtView, "HATA!!!!!!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });
            }

            @NonNull
            @Override
            public KirmiziEtViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.urun1,viewGroup,false);
                KirmiziEtViewHolder viewHolder = new KirmiziEtViewHolder(view);
                return viewHolder;
            }
        };
        myKirmiziEtList.setAdapter(adapter);
        adapter.startListening();


    }

    public static class KirmiziEtViewHolder extends RecyclerView.ViewHolder{

        TextView urunadi,urunfiyat,urunagirlik;
        ImageFilterView urunfotografi;
        private ImageButton sepetArti;


        private int miktar ;
        private int tutar;


        public KirmiziEtViewHolder(@NonNull View itemView) {
            super(itemView);
            urunadi= itemView.findViewById(R.id.urunAdi);
            urunfiyat= itemView.findViewById(R.id.urunFiyat);
            urunagirlik= itemView.findViewById(R.id.urunAgirlik);
            urunfotografi=itemView.findViewById(R.id.urunfotografi);
            sepetArti = itemView.findViewById(R.id.sepetarti);

        }
    }



}