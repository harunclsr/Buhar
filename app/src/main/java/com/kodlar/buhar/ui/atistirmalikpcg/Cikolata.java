package com.kodlar.buhar.ui.atistirmalikpcg;

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
import com.kodlar.buhar.ui.iceceklerpcg.Gazliicecek;
import com.squareup.picasso.Picasso;

public class Cikolata extends Fragment {
    private View CikolataView;
    private RecyclerView myCikolataList;
    private DatabaseReference CikolataRef,ContacsRef;
    private FirebaseAuth mAuth;
    private String currentUserID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        CikolataView= inflater.inflate(R.layout.cikolata_layout,container,false);

        myCikolataList=(RecyclerView) CikolataView.findViewById(R.id.cikolata_list);
        myCikolataList.setLayoutManager(new LinearLayoutManager(getContext()));
        mAuth= FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();


        // ContacsRef = FirebaseDatabase.getInstance().getReference().child("Kampus").child("icecekler").child("Su").child(currentUserID);
        CikolataRef = FirebaseDatabase.getInstance().getReference().child("Kampus").child("Atistirmalik").child("Cikolata");



        return CikolataView;
    }
    @Override
    public void onStart(){
        super.onStart();
        FirebaseRecyclerOptions options=
                new FirebaseRecyclerOptions.Builder<Urun>()
                        .setQuery(CikolataRef,Urun.class)
                        .build();

        final FirebaseRecyclerAdapter<Urun, CikolataViewHolder> adapter = new FirebaseRecyclerAdapter<Urun, CikolataViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final CikolataViewHolder CikolataViewHolder, int i, @NonNull Urun urun) {

                String userIDs = getRef(i).getKey();

                CikolataRef.child(userIDs).addValueEventListener(new ValueEventListener(){

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {

                        String urunfotografi = dataSnapshot.child("image").getValue(String.class);
                        String urunadi = dataSnapshot.child("urunadi").getValue(String.class);
                        String urunagirlik = dataSnapshot.child("urunagirlik").getValue(String.class);
                        String urunfiyat = dataSnapshot.child("urunfiyati").getValue(String.class);

                        CikolataViewHolder.urunadi.setText(urunadi);
                        CikolataViewHolder.urunfiyat.setText(urunfiyat);
                        CikolataViewHolder.urunagirlik.setText(urunagirlik);
                        Picasso.get().load(urunfotografi.toString()).into(CikolataViewHolder.urunfotografi);


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Snackbar.make(CikolataView, "HATA!!!!!!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });
            }

            @NonNull
            @Override
            public CikolataViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.urun,viewGroup,false);
                CikolataViewHolder viewHolder = new CikolataViewHolder(view);
                return viewHolder;
            }
        };
        myCikolataList.setAdapter(adapter);
        adapter.startListening();


    }

    public static class CikolataViewHolder extends RecyclerView.ViewHolder{

        TextView urunadi,urunfiyat,urunagirlik;
        ImageFilterView urunfotografi;

        public CikolataViewHolder(@NonNull View itemView) {
            super(itemView);
            urunadi= itemView.findViewById(R.id.urunAdi);
            urunfiyat= itemView.findViewById(R.id.urunFiyat);
            urunagirlik= itemView.findViewById(R.id.urunAgirlik);
            urunfotografi=itemView.findViewById(R.id.urunfotografi);

        }
    }

}
