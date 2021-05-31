package com.kodlar.buhar.ui.Kahvaltilikpcg;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

public class SutveSuturunleri extends Fragment {

    private View SutveSuturunleriView;
    private RecyclerView mySutveSuturunlerilist;
    private DatabaseReference SutveSuturunleriRef;
    private FirebaseAuth mAuth;
    private String currentUserID;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        SutveSuturunleriView= inflater.inflate(R.layout.activity_sutve_suturunleri,container,false);

        mySutveSuturunlerilist=(RecyclerView) SutveSuturunleriView.findViewById(R.id.SutveSuturunleri_list);
        mySutveSuturunlerilist.setLayoutManager(new LinearLayoutManager(getContext()));
        mAuth=FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();



        SutveSuturunleriRef = FirebaseDatabase.getInstance().getReference().child("Kampus").child("Kahvaltilik").child("Sut Urunleri");



        return SutveSuturunleriView;
    }
    @Override
    public void onStart(){
        super.onStart();
        FirebaseRecyclerOptions options=
                new FirebaseRecyclerOptions.Builder<Urun1>()
                        .setQuery(SutveSuturunleriRef, Urun1.class)
                        .build();

        final FirebaseRecyclerAdapter<Urun1, SutveSuturunleriViewHolder> adapter = new FirebaseRecyclerAdapter<Urun1, SutveSuturunleriViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final SutveSuturunleriViewHolder SutveSuturunleriViewHolder, int i, @NonNull Urun1 urun) {

                String userIDs = getRef(i).getKey();

                SutveSuturunleriRef.child(userIDs).addValueEventListener(new ValueEventListener(){

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {

                        String urunfotografi = dataSnapshot.child("image").getValue(String.class);
                        String urunadi = dataSnapshot.child("urunadi").getValue(String.class);
                        String urunagirlik = dataSnapshot.child("urunagirlik").getValue(String.class);
                        Integer urunfiyat = dataSnapshot.child("urunfiyati").getValue(Integer.class);
                        String urunid = dataSnapshot.child("urunid").getValue(String.class);

                        SutveSuturunleriViewHolder.urunadi.setText(urunadi);
                        SutveSuturunleriViewHolder.urunfiyat.setText(""+urunfiyat);
                        SutveSuturunleriViewHolder.urunagirlik.setText(urunagirlik);

                        Picasso.get().load(urunfotografi.toString()).into(SutveSuturunleriViewHolder.urunfotografi);

                        SutveSuturunleriViewHolder.sepetArti.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {


                                DatabaseReference SepetRef;
                                SepetRef = FirebaseDatabase.getInstance().getReference().child("Kullanıcılar").child(currentUserID).child("Sepet").child("Urunlistesi");
                                SutveSuturunleriViewHolder.miktar++;

                                SepetRef.child(userIDs).setValue(new Urun1(urunadi, urunagirlik, urunfiyat, urunfotografi, urunid));
                                SepetRef.child(userIDs).child("miktar").setValue(SutveSuturunleriViewHolder.miktar);
                                SepetRef.child(userIDs).child("uruntutari").setValue(urun.getUruntutari());
                                Snackbar.make(SutveSuturunleriView, "Sepetinize ürün eklendi.", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();


                                urun.setUruntutari(SutveSuturunleriViewHolder.miktar * urunfiyat);

                                SepetRef.child(userIDs).child("uruntutari").setValue(urun.getUruntutari());


                                Snackbar.make(SutveSuturunleriView, "Sepetinize ürün eklendi.", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }


                        });




                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Snackbar.make(SutveSuturunleriView, "HATA!!!!!!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });
            }

            @NonNull
            @Override
            public SutveSuturunleriViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.urun1,viewGroup,false);
                SutveSuturunleriViewHolder viewHolder = new SutveSuturunleriViewHolder(view);
                return viewHolder;
            }
        };
        mySutveSuturunlerilist.setAdapter(adapter);
        adapter.startListening();


    }

    public static class SutveSuturunleriViewHolder extends RecyclerView.ViewHolder{

        TextView urunadi,urunfiyat,urunagirlik;
        ImageFilterView urunfotografi;
        private ImageButton sepetArti;


        private int miktar ;
        private int tutar;


        public SutveSuturunleriViewHolder(@NonNull View itemView) {
            super(itemView);
            urunadi= itemView.findViewById(R.id.urunAdi);
            urunfiyat= itemView.findViewById(R.id.urunFiyat);
            urunagirlik= itemView.findViewById(R.id.urunAgirlik);
            urunfotografi=itemView.findViewById(R.id.urunfotografi);
            sepetArti = itemView.findViewById(R.id.sepetarti);

        }
    }

}