package com.kodlar.buhar.ui.TemelGidapcg;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.kodlar.buhar.Urun;
import com.squareup.picasso.Picasso;

public class TuzSekerBaharat extends Fragment {
    private View TSBView;
    private RecyclerView myTSBList;
    private DatabaseReference TSBRef,ContacsRef;
    private FirebaseAuth mAuth;
    private String currentUserID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        TSBView= inflater.inflate(R.layout.activity_tuz_seker_baharat,container,false);

        myTSBList=(RecyclerView) TSBView.findViewById(R.id.tuzsekerbaharat_list);
        myTSBList.setLayoutManager(new LinearLayoutManager(getContext()));
        mAuth= FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();



        TSBRef = FirebaseDatabase.getInstance().getReference().child("Kampus").child("TemelGida").child("TuzSekerBaharat");



        return TSBView;
    }
    @Override
    public void onStart(){
        super.onStart();
        FirebaseRecyclerOptions options=
                new FirebaseRecyclerOptions.Builder<Urun>()
                        .setQuery(TSBRef,Urun.class)
                        .build();

        final FirebaseRecyclerAdapter<Urun, TSBViewHolder> adapter = new FirebaseRecyclerAdapter<Urun, TSBViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final TSBViewHolder TSBViewHolder, int i, @NonNull Urun urun) {

                String userIDs = getRef(i).getKey();

                TSBRef.child(userIDs).addValueEventListener(new ValueEventListener(){

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {

                        String urunfotografi = dataSnapshot.child("image").getValue(String.class);
                        String urunadi = dataSnapshot.child("urunadi").getValue(String.class);
                        String urunagirlik = dataSnapshot.child("urunagirlik").getValue(String.class);
                        String urunfiyat = dataSnapshot.child("urunfiyati").getValue(String.class);

                        TSBViewHolder.urunadi.setText(urunadi);
                        TSBViewHolder.urunfiyat.setText(urunfiyat);
                        TSBViewHolder.urunagirlik.setText(urunagirlik);
                        Picasso.get().load(urunfotografi.toString()).into(TSBViewHolder.urunfotografi);


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Snackbar.make(TSBView, "HATA!!!!!!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });
            }

            @NonNull
            @Override
            public TSBViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.urun,viewGroup,false);
                TSBViewHolder viewHolder = new TSBViewHolder(view);
                return viewHolder;
            }
        };
        myTSBList.setAdapter(adapter);
        adapter.startListening();


    }

    public static class TSBViewHolder extends RecyclerView.ViewHolder{

        TextView urunadi,urunfiyat,urunagirlik;
        ImageFilterView urunfotografi;

        public TSBViewHolder(@NonNull View itemView) {
            super(itemView);
            urunadi= itemView.findViewById(R.id.urunAdi);
            urunfiyat= itemView.findViewById(R.id.urunFiyat);
            urunagirlik= itemView.findViewById(R.id.urunAgirlik);
            urunfotografi=itemView.findViewById(R.id.urunfotografi);

        }
    }

}

