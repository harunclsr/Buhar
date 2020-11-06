package com.kodlar.buhar.ui.iceceklerpcg;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kodlar.buhar.Urun;
import com.kodlar.buhar.ui.iceceklerpcg.icecekler2;
import com.kodlar.buhar.R;
import com.squareup.picasso.Picasso;


public class Su extends Fragment  {

    private View SuView;
    private RecyclerView mySulist;
    private DatabaseReference SuRef;
    private FirebaseAuth mAuth;

public Su(){

}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        SuView= inflater.inflate(R.layout.su_layout,container,false);

        mySulist=(RecyclerView) SuView.findViewById(R.id.su_list);
        mySulist.setLayoutManager(new LinearLayoutManager(getContext()));
        mAuth=FirebaseAuth.getInstance();

        SuRef = FirebaseDatabase.getInstance().getReference("Kampus").child("icecekler").child("Su");



    return SuView;
    }
    @Override
    public void onStart(){
    super.onStart();
    FirebaseRecyclerOptions options=
            new FirebaseRecyclerOptions.Builder<Urun>()
            .setQuery(SuRef,Urun.class)
                .build();

     final  FirebaseRecyclerAdapter<Urun,SuViewHolder> adapter = new FirebaseRecyclerAdapter<Urun, SuViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final SuViewHolder suViewHolder, int i, @NonNull Urun urun) {

                final String userIDs = getRef(i).getKey();

                SuRef.child(userIDs).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        if (dataSnapshot.exists())
                        {
                            if (dataSnapshot.child("icecekler").hasChild("Su"))
                            {

                                String urunfotografi = dataSnapshot.child("image").getValue().toString();
                                String urunAdi = dataSnapshot.child("Marka").getValue().toString();
                                String urunfiyat = dataSnapshot.child("Fiyat").getValue().toString();
                                String urunagirlik = dataSnapshot.child("Agirlik").getValue().toString();

                                suViewHolder.urunAdi.setText(urunAdi );
                                suViewHolder.urunfiyat.setText(urunfiyat);
                                suViewHolder.urunAgirlik.setText(urunagirlik);
                            }

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

         @NonNull
         @Override
         public SuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

             View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.urun,viewGroup,false);
             SuViewHolder viewHolder = new SuViewHolder(view);
             return viewHolder;
         }
     };
        mySulist.setAdapter(adapter);
        adapter.startListening();


}

    public static class SuViewHolder extends RecyclerView.ViewHolder{

    TextView urunAdi,urunfiyat,urunAgirlik;
    ImageFilterView urunfotografi;

      public SuViewHolder(@NonNull View itemView) {
        super(itemView);
        urunAdi= itemView.findViewById(R.id.urunAdi);
        urunfiyat= itemView.findViewById(R.id.urunFiyat);
        urunAgirlik= itemView.findViewById(R.id.urunAgirlik);
        urunfotografi=itemView.findViewById(R.id.urunfotografi);
    }
}

}