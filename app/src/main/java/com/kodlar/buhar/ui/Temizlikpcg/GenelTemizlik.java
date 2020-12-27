package com.kodlar.buhar.ui.Temizlikpcg;

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
import com.kodlar.buhar.ui.atistirmalikpcg.Cips;
import com.squareup.picasso.Picasso;

public class GenelTemizlik extends Fragment {
    private View GenelTemizlikView;
    private RecyclerView myGenelTemizlikList;
    private DatabaseReference GenelTemizlikRef,ContacsRef;
    private FirebaseAuth mAuth;
    private String currentUserID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        GenelTemizlikView= inflater.inflate(R.layout.activity_genel_temizlik,container,false);

        myGenelTemizlikList=(RecyclerView) GenelTemizlikView.findViewById(R.id.geneltemizlik_list);
        myGenelTemizlikList.setLayoutManager(new LinearLayoutManager(getContext()));
        mAuth= FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();


        // ContacsRef = FirebaseDatabase.getInstance().getReference().child("Kampus").child("icecekler").child("Su").child(currentUserID);
        GenelTemizlikRef = FirebaseDatabase.getInstance().getReference().child("Kampus").child("Temizlik").child("GenelTemizlik");



        return GenelTemizlikView;
    }
    @Override
    public void onStart(){
        super.onStart();
        FirebaseRecyclerOptions options=
                new FirebaseRecyclerOptions.Builder<Urun>()
                        .setQuery(GenelTemizlikRef,Urun.class)
                        .build();

        final FirebaseRecyclerAdapter<Urun, GenelTemizlikViewHolder> adapter = new FirebaseRecyclerAdapter<Urun, GenelTemizlikViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final GenelTemizlikViewHolder GenelTemizlikViewHolder, int i, @NonNull Urun urun) {

                String userIDs = getRef(i).getKey();

                GenelTemizlikRef.child(userIDs).addValueEventListener(new ValueEventListener(){

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {

                        String urunfotografi = dataSnapshot.child("image").getValue(String.class);
                        String urunadi = dataSnapshot.child("urunadi").getValue(String.class);
                        String urunagirlik = dataSnapshot.child("urunagirlik").getValue(String.class);
                        String urunfiyat = dataSnapshot.child("urunfiyati").getValue(String.class);

                        GenelTemizlikViewHolder.urunadi.setText(urunadi);
                        GenelTemizlikViewHolder.urunfiyat.setText(urunfiyat);
                        GenelTemizlikViewHolder.urunagirlik.setText(urunagirlik);
                        Picasso.get().load(urunfotografi.toString()).into(GenelTemizlikViewHolder.urunfotografi);


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Snackbar.make(GenelTemizlikView, "HATA!!!!!!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });
            }

            @NonNull
            @Override
            public GenelTemizlikViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.urun,viewGroup,false);
                GenelTemizlikViewHolder viewHolder = new GenelTemizlikViewHolder(view);
                return viewHolder;
            }
        };
        myGenelTemizlikList.setAdapter(adapter);
        adapter.startListening();


    }

    public static class GenelTemizlikViewHolder extends RecyclerView.ViewHolder{

        TextView urunadi,urunfiyat,urunagirlik;
        ImageFilterView urunfotografi;

        public GenelTemizlikViewHolder(@NonNull View itemView) {
            super(itemView);
            urunadi= itemView.findViewById(R.id.urunAdi);
            urunfiyat= itemView.findViewById(R.id.urunFiyat);
            urunagirlik= itemView.findViewById(R.id.urunAgirlik);
            urunfotografi=itemView.findViewById(R.id.urunfotografi);

        }
    }

}
