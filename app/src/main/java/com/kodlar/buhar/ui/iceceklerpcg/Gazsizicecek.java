package com.kodlar.buhar.ui.iceceklerpcg;

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

public class Gazsizicecek extends Fragment {
    private View GazsizView;
    private RecyclerView myGazsizList;
    private DatabaseReference GazsizRef,ContacsRef;
    private FirebaseAuth mAuth;
    private String currentUserID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        GazsizView= inflater.inflate(R.layout.gazsizicecek_layout,container,false);

        myGazsizList=(RecyclerView) GazsizView.findViewById(R.id.gazsiz_list);
        myGazsizList.setLayoutManager(new LinearLayoutManager(getContext()));
        mAuth= FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();


        // ContacsRef = FirebaseDatabase.getInstance().getReference().child("Kampus").child("icecekler").child("Su").child(currentUserID);
        GazsizRef = FirebaseDatabase.getInstance().getReference().child("Kampus").child("icecekler").child("Gazsizicecekler");



        return GazsizView;
    }
    @Override
    public void onStart(){
        super.onStart();
        FirebaseRecyclerOptions options=
                new FirebaseRecyclerOptions.Builder<Urun>()
                        .setQuery(GazsizRef,Urun.class)
                        .build();

        final FirebaseRecyclerAdapter<Urun,GazsizViewHolder> adapter = new FirebaseRecyclerAdapter<Urun, GazsizViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final GazsizViewHolder GazsizViewHolder, int i, @NonNull Urun urun) {

                String userIDs = getRef(i).getKey();

                GazsizRef.child(userIDs).addValueEventListener(new ValueEventListener(){

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {

                    String urunfotografi = dataSnapshot.child("image").getValue(String.class);
                        String urunadi = dataSnapshot.child("urunadi").getValue(String.class);
                        String urunagirlik = dataSnapshot.child("urunagirlik").getValue(String.class);
                        String urunfiyat = dataSnapshot.child("urunfiyati").getValue(String.class);

                        GazsizViewHolder.urunadi.setText(urunadi);
                        GazsizViewHolder.urunfiyat.setText(urunfiyat);
                        GazsizViewHolder.urunagirlik.setText(urunagirlik);

                        Picasso.get().load(urunfotografi.toString()).into(GazsizViewHolder.urunfotografi);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Snackbar.make(GazsizView, "HATA!!!!!!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });
            }

            @NonNull
            @Override
            public GazsizViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.urun,viewGroup,false);
                GazsizViewHolder viewHolder = new GazsizViewHolder(view);
                return viewHolder;
            }
        };
        myGazsizList.setAdapter(adapter);
        adapter.startListening();


    }

    public static class GazsizViewHolder extends RecyclerView.ViewHolder{

        TextView urunadi,urunfiyat,urunagirlik;
        ImageFilterView urunfotografi;

        public GazsizViewHolder(@NonNull View itemView) {
            super(itemView);
            urunadi= itemView.findViewById(R.id.urunAdi);
            urunfiyat= itemView.findViewById(R.id.urunFiyat);
            urunagirlik= itemView.findViewById(R.id.urunAgirlik);
            urunfotografi=itemView.findViewById(R.id.urunfotografi);

        }
    }

}