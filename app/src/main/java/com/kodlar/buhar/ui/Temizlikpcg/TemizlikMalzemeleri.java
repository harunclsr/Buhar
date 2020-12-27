package com.kodlar.buhar.ui.Temizlikpcg;

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
import androidx.appcompat.app.AppCompatActivity;
import com.kodlar.buhar.R;
import android.os.Bundle;

public class TemizlikMalzemeleri extends Fragment {
    private View TemizlikMalzemeleriView;
    private RecyclerView myTemizlikMalzemeleriList;
    private DatabaseReference TemizlikMalzemeleriRef,ContacsRef;
    private FirebaseAuth mAuth;
    private String currentUserID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        TemizlikMalzemeleriView= inflater.inflate(R.layout.activity_temizlik_malzemeleri,container,false);

        myTemizlikMalzemeleriList=(RecyclerView) TemizlikMalzemeleriView.findViewById(R.id.temizlikmalzemeleri_list);
        myTemizlikMalzemeleriList.setLayoutManager(new LinearLayoutManager(getContext()));
        mAuth= FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();


        // ContacsRef = FirebaseDatabase.getInstance().getReference().child("Kampus").child("icecekler").child("Su").child(currentUserID);
        TemizlikMalzemeleriRef = FirebaseDatabase.getInstance().getReference().child("Kampus").child("Temizlik").child("TemizlikMalzemeleri");



        return TemizlikMalzemeleriView;
    }
    @Override
    public void onStart(){
        super.onStart();
        FirebaseRecyclerOptions options=
                new FirebaseRecyclerOptions.Builder<Urun>()
                        .setQuery(TemizlikMalzemeleriRef,Urun.class)
                        .build();

        final FirebaseRecyclerAdapter<Urun, TemizlikMalzemeleriViewHolder> adapter = new FirebaseRecyclerAdapter<Urun, TemizlikMalzemeleriViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final TemizlikMalzemeleriViewHolder TemizlikMalzemeleriViewHolder, int i, @NonNull Urun urun) {

                String userIDs = getRef(i).getKey();

                TemizlikMalzemeleriRef.child(userIDs).addValueEventListener(new ValueEventListener(){

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {

                        String urunfotografi = dataSnapshot.child("image").getValue(String.class);
                        String urunadi = dataSnapshot.child("urunadi").getValue(String.class);
                        String urunagirlik = dataSnapshot.child("urunagirlik").getValue(String.class);
                        String urunfiyat = dataSnapshot.child("urunfiyati").getValue(String.class);

                        TemizlikMalzemeleriViewHolder.urunadi.setText(urunadi);
                        TemizlikMalzemeleriViewHolder.urunfiyat.setText(urunfiyat);
                        TemizlikMalzemeleriViewHolder.urunagirlik.setText(urunagirlik);
                        Picasso.get().load(urunfotografi.toString()).into(TemizlikMalzemeleriViewHolder.urunfotografi);


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Snackbar.make(TemizlikMalzemeleriView, "HATA!!!!!!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });
            }

            @NonNull
            @Override
            public TemizlikMalzemeleriViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.urun,viewGroup,false);
                TemizlikMalzemeleriViewHolder viewHolder = new TemizlikMalzemeleriViewHolder(view);
                return viewHolder;
            }
        };
        myTemizlikMalzemeleriList.setAdapter(adapter);
        adapter.startListening();


    }

    public static class TemizlikMalzemeleriViewHolder extends RecyclerView.ViewHolder{

        TextView urunadi,urunfiyat,urunagirlik;
        ImageFilterView urunfotografi;

        public TemizlikMalzemeleriViewHolder(@NonNull View itemView) {
            super(itemView);
            urunadi= itemView.findViewById(R.id.urunAdi);
            urunfiyat= itemView.findViewById(R.id.urunFiyat);
            urunagirlik= itemView.findViewById(R.id.urunAgirlik);
            urunfotografi=itemView.findViewById(R.id.urunfotografi);

        }
    }

}
