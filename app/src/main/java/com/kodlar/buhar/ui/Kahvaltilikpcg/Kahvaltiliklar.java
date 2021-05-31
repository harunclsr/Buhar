package com.kodlar.buhar.ui.Kahvaltilikpcg;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.utils.widget.ImageFilterView;

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
import com.kodlar.buhar.Urun1;
import com.kodlar.buhar.R;
import com.squareup.picasso.Picasso;
public class Kahvaltiliklar extends Fragment {
    private View KahvaltiliklarView;
    private RecyclerView myKahvaltilirlarList;
    private DatabaseReference KahvaltiliklarRef;
    private FirebaseAuth mAuth;
    private String currentUserID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        KahvaltiliklarView= inflater.inflate(R.layout.activity_kahvaltiliklar,container,false);

        myKahvaltilirlarList=(RecyclerView) KahvaltiliklarView.findViewById(R.id.kahvaltiliklar_list);
        myKahvaltilirlarList.setLayoutManager(new LinearLayoutManager(getContext()));
        mAuth= FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();


        // ContacsRef = FirebaseDatabase.getInstance().getReference().child("Kampus").child("icecekler").child("Su").child(currentUserID);
        KahvaltiliklarRef = FirebaseDatabase.getInstance().getReference().child("Kampus").child("Kahvaltilik").child("Kahvaltiliklar");



        return KahvaltiliklarView;
    }
    @Override
    public void onStart(){
        super.onStart();
        FirebaseRecyclerOptions options=
                new FirebaseRecyclerOptions.Builder<Urun1>()
                        .setQuery(KahvaltiliklarRef, Urun1.class)
                        .build();

        final FirebaseRecyclerAdapter<Urun1, KahvaltiliklarViewHolder> adapter = new FirebaseRecyclerAdapter<Urun1, KahvaltiliklarViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final KahvaltiliklarViewHolder KahvaltiliklarViewHolder, int i, @NonNull Urun1 urun) {

                String userIDs = getRef(i).getKey();

                KahvaltiliklarRef.child(userIDs).addValueEventListener(new ValueEventListener(){

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {

                        String urunfotografi = dataSnapshot.child("image").getValue(String.class);
                        String urunadi = dataSnapshot.child("urunadi").getValue(String.class);
                        String urunagirlik = dataSnapshot.child("urunagirlik").getValue(String.class);
                        Integer urunfiyat = dataSnapshot.child("urunfiyati").getValue(Integer.class);
                        String urunid = dataSnapshot.child("urunid").getValue(String.class);

                        KahvaltiliklarViewHolder.urunadi.setText(urunadi);
                        KahvaltiliklarViewHolder.urunfiyat.setText(""+urunfiyat);
                        KahvaltiliklarViewHolder.urunagirlik.setText(urunagirlik);

                        Picasso.get().load(urunfotografi.toString()).into(KahvaltiliklarViewHolder.urunfotografi);

                        KahvaltiliklarViewHolder.sepetArti.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {


                                DatabaseReference SepetRef;
                                SepetRef = FirebaseDatabase.getInstance().getReference().child("Kullanıcılar").child(currentUserID).child("Sepet").child("Urunlistesi");
                                KahvaltiliklarViewHolder.miktar++;

                                SepetRef.child(userIDs).setValue(new Urun1(urunadi, urunagirlik, urunfiyat, urunfotografi, urunid));
                                SepetRef.child(userIDs).child("miktar").setValue(KahvaltiliklarViewHolder.miktar);
                                SepetRef.child(userIDs).child("uruntutari").setValue(urun.getUruntutari());
                                Snackbar.make(KahvaltiliklarView, "Sepetinize ürün eklendi.", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();


                                urun.setUruntutari(KahvaltiliklarViewHolder.miktar * urunfiyat);

                                SepetRef.child(userIDs).child("uruntutari").setValue(urun.getUruntutari());


                                Snackbar.make(KahvaltiliklarView, "Sepetinize ürün eklendi.", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }


                        });




                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Snackbar.make(KahvaltiliklarView, "HATA!!!!!!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });
            }

            @NonNull
            @Override
            public KahvaltiliklarViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.urun1,viewGroup,false);
                KahvaltiliklarViewHolder viewHolder = new KahvaltiliklarViewHolder(view);
                return viewHolder;
            }
        };
        myKahvaltilirlarList.setAdapter(adapter);
        adapter.startListening();


    }

    public static class KahvaltiliklarViewHolder extends RecyclerView.ViewHolder{

        TextView urunadi,urunfiyat,urunagirlik;
        ImageFilterView urunfotografi;
        private ImageButton sepetArti;


        private int miktar ;
        private int tutar;


        public KahvaltiliklarViewHolder(@NonNull View itemView) {
            super(itemView);
            urunadi= itemView.findViewById(R.id.urunAdi);
            urunfiyat= itemView.findViewById(R.id.urunFiyat);
            urunagirlik= itemView.findViewById(R.id.urunAgirlik);
            urunfotografi=itemView.findViewById(R.id.urunfotografi);
            sepetArti = itemView.findViewById(R.id.sepetarti);


        }
    }

}
