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
import com.kodlar.buhar.ui.manavpckg.Meyveler;
import com.squareup.picasso.Picasso;

public class MakarnaBakliyat extends Fragment {
    private View MakarnaBakliyatView;
    private RecyclerView myMakarnaBakliyatList;
    private DatabaseReference MakarnaBakliyatRef,ContacsRef;
    private FirebaseAuth mAuth;
    private String currentUserID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        MakarnaBakliyatView= inflater.inflate(R.layout.activity_makarna_bakliyat,container,false);

        myMakarnaBakliyatList=(RecyclerView) MakarnaBakliyatView.findViewById(R.id.makarnabakliyat_list);
        myMakarnaBakliyatList.setLayoutManager(new LinearLayoutManager(getContext()));
        mAuth= FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();


        // ContacsRef = FirebaseDatabase.getInstance().getReference().child("Kampus").child("icecekler").child("Su").child(currentUserID);
        MakarnaBakliyatRef = FirebaseDatabase.getInstance().getReference().child("Kampus").child("TemelGida").child("MakarnaBakliyat");



        return MakarnaBakliyatView;
    }
    @Override
    public void onStart(){
        super.onStart();
        FirebaseRecyclerOptions options=
                new FirebaseRecyclerOptions.Builder<Urun>()
                        .setQuery(MakarnaBakliyatRef,Urun.class)
                        .build();

        final FirebaseRecyclerAdapter<Urun, MakarnaBakliyatViewHolder> adapter = new FirebaseRecyclerAdapter<Urun, MakarnaBakliyatViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final MakarnaBakliyatViewHolder MakarnaBakliyatViewHolder, int i, @NonNull Urun urun) {

                String userIDs = getRef(i).getKey();

                MakarnaBakliyatRef.child(userIDs).addValueEventListener(new ValueEventListener(){

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {

                        String urunfotografi = dataSnapshot.child("image").getValue(String.class);
                        String urunadi = dataSnapshot.child("urunadi").getValue(String.class);
                        String urunagirlik = dataSnapshot.child("urunagirlik").getValue(String.class);
                        String urunfiyat = dataSnapshot.child("urunfiyati").getValue(String.class);

                        MakarnaBakliyatViewHolder.urunadi.setText(urunadi);
                        MakarnaBakliyatViewHolder.urunfiyat.setText(urunfiyat);
                        MakarnaBakliyatViewHolder.urunagirlik.setText(urunagirlik);
                        Picasso.get().load(urunfotografi.toString()).into(MakarnaBakliyatViewHolder.urunfotografi);


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Snackbar.make(MakarnaBakliyatView, "HATA!!!!!!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });
            }

            @NonNull
            @Override
            public MakarnaBakliyatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.urun,viewGroup,false);
                MakarnaBakliyatViewHolder viewHolder = new MakarnaBakliyatViewHolder(view);
                return viewHolder;
            }
        };
        myMakarnaBakliyatList.setAdapter(adapter);
        adapter.startListening();


    }

    public static class MakarnaBakliyatViewHolder extends RecyclerView.ViewHolder{

        TextView urunadi,urunfiyat,urunagirlik;
        ImageFilterView urunfotografi;

        public MakarnaBakliyatViewHolder(@NonNull View itemView) {
            super(itemView);
            urunadi= itemView.findViewById(R.id.urunAdi);
            urunfiyat= itemView.findViewById(R.id.urunFiyat);
            urunagirlik= itemView.findViewById(R.id.urunAgirlik);
            urunfotografi=itemView.findViewById(R.id.urunfotografi);

        }
    }

}


