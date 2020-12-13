package com.kodlar.buhar.ui.manavpckg;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import com.kodlar.buhar.Urun;
import com.squareup.picasso.Picasso;

public class Sebzeler extends Fragment {
    private View SebzelerView;
    private RecyclerView mySebzelerList;
    private DatabaseReference SebzelerRef,ContacsRef;
    private FirebaseAuth mAuth;
    private String currentUserID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        SebzelerView= inflater.inflate(R.layout.sebzeler_layout,container,false);

        mySebzelerList=(RecyclerView) SebzelerView.findViewById(R.id.sebzeler_list);
        mySebzelerList.setLayoutManager(new LinearLayoutManager(getContext()));
        mAuth= FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();


        // ContacsRef = FirebaseDatabase.getInstance().getReference().child("Kampus").child("icecekler").child("Su").child(currentUserID);
        SebzelerRef = FirebaseDatabase.getInstance().getReference().child("Kampus").child("Manav").child("Sebzeler");



        return SebzelerView;
    }
    @Override
    public void onStart(){
        super.onStart();
        FirebaseRecyclerOptions options=
                new FirebaseRecyclerOptions.Builder<Urun>()
                        .setQuery(SebzelerRef,Urun.class)
                        .build();

        final FirebaseRecyclerAdapter<Urun, SebzelerViewHolder> adapter = new FirebaseRecyclerAdapter<Urun, SebzelerViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final SebzelerViewHolder SebzelerViewHolder, int i, @NonNull Urun urun) {

                String userIDs = getRef(i).getKey();

                SebzelerRef.child(userIDs).addValueEventListener(new ValueEventListener(){

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {

                        String urunfotografi = dataSnapshot.child("image").getValue(String.class);
                        String urunadi = dataSnapshot.child("urunadi").getValue(String.class);
                        String urunagirlik = dataSnapshot.child("urunagirlik").getValue(String.class);
                        String urunfiyat = dataSnapshot.child("urunfiyati").getValue(String.class);

                        SebzelerViewHolder.urunadi.setText(urunadi);
                        SebzelerViewHolder.urunfiyat.setText(urunfiyat);
                        SebzelerViewHolder.urunagirlik.setText(urunagirlik);
                        Picasso.get().load(urunfotografi.toString()).into(SebzelerViewHolder.urunfotografi);


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Snackbar.make(SebzelerView, "HATA!!!!!!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });
            }

            @NonNull
            @Override
            public SebzelerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.urun,viewGroup,false);
                SebzelerViewHolder viewHolder = new SebzelerViewHolder(view);
                return viewHolder;
            }
        };
        mySebzelerList.setAdapter(adapter);
        adapter.startListening();


    }

    public static class SebzelerViewHolder extends RecyclerView.ViewHolder{

        TextView urunadi,urunfiyat,urunagirlik;
        ImageFilterView urunfotografi;

        public SebzelerViewHolder(@NonNull View itemView) {
            super(itemView);
            urunadi= itemView.findViewById(R.id.urunAdi);
            urunfiyat= itemView.findViewById(R.id.urunFiyat);
            urunagirlik= itemView.findViewById(R.id.urunAgirlik);
            urunfotografi=itemView.findViewById(R.id.urunfotografi);

        }
    }

}


