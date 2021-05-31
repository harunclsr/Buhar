package com.kodlar.buhar.ui.iceceklerpcg;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
import com.kodlar.buhar.SepetController;
import com.kodlar.buhar.Urun1;
import com.kodlar.buhar.R;
import com.kodlar.buhar.ui.Sepetimpcg.Sepet;
import com.squareup.picasso.Picasso;


public class Su extends Fragment  {

    private View SuView;
    private RecyclerView mySulist;
    private DatabaseReference SuRef,SepetRef,SepetTutarRef;
    private FirebaseAuth mAuth;
    private String currentUserID;
    int count=0;
    private TextView uruntutar;



    public Su(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        SuView= inflater.inflate(R.layout.su_layout,container,false);

        mySulist=(RecyclerView) SuView.findViewById(R.id.su_list);
        mySulist.setLayoutManager(new LinearLayoutManager(getContext()));
        mAuth=FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();


         SuRef = FirebaseDatabase.getInstance().getReference().child("Kampus").child("icecekler").child("Su");
        SepetRef = FirebaseDatabase.getInstance().getReference().child("Kullanıcılar").child(currentUserID).child("Sepet").child("Urunlistesi");
        SepetTutarRef = FirebaseDatabase.getInstance().getReference().child("Kullanıcılar").child(currentUserID).child("Sepet").child("SepetTutari");


        return SuView;
    }
    @Override
    public void onStart(){
        super.onStart();
        FirebaseRecyclerOptions options=
                new FirebaseRecyclerOptions.Builder<Urun1>()
                        .setQuery(SuRef, Urun1.class)
                        .build();

        final  FirebaseRecyclerAdapter<Urun1,SuViewHolder> adapter = new FirebaseRecyclerAdapter<Urun1, SuViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final SuViewHolder suViewHolder, int i, @NonNull Urun1 urun) {

                String userIDs = getRef(i).getKey();

                SuRef.child(userIDs).addValueEventListener(new ValueEventListener(){

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {

                        String urunfotografi = dataSnapshot.child("image").getValue(String.class);
                        String urunadi = dataSnapshot.child("urunadi").getValue(String.class);
                        String urunagirlik = dataSnapshot.child("urunagirlik").getValue(String.class);
                        Integer urunfiyat = dataSnapshot.child("urunfiyati").getValue(Integer.class);
                        String urunid = dataSnapshot.child("urunid").getValue(String.class);

                        suViewHolder.urunadi.setText(urunadi);
                        suViewHolder.urunfiyat.setText(""+urunfiyat);
                        suViewHolder.urunagirlik.setText(urunagirlik);

                        Picasso.get().load(urunfotografi.toString()).into(suViewHolder.urunfotografi);

                suViewHolder.sepetArti.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {


                        DatabaseReference SepetRef;
                        SepetRef = FirebaseDatabase.getInstance().getReference().child("Kullanıcılar").child(currentUserID).child("Sepet").child("Urunlistesi");
                        suViewHolder.miktar++;

                        SepetRef.child(userIDs).setValue(new Urun1(urunadi, urunagirlik, urunfiyat, urunfotografi, urunid));
                        SepetRef.child(userIDs).child("miktar").setValue(suViewHolder.miktar);
                        SepetRef.child(userIDs).child("uruntutari").setValue(urun.getUruntutari());
                        Snackbar.make(SuView, "Sepetinize ürün eklendi.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();


                        urun.setUruntutari(suViewHolder.miktar * urunfiyat);

                        SepetRef.child(userIDs).child("uruntutari").setValue(urun.getUruntutari());


                        Snackbar.make(SuView, "Sepetinize ürün eklendi.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }


                        });




                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Snackbar.make(SuView, "HATA!!!!!!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });




                SepetRef.child(userIDs).addValueEventListener(new ValueEventListener(){
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {

                      Integer miktar = dataSnapshot.child("miktar").getValue(Integer.class);
                        Integer UrunTekTutar = dataSnapshot.child("uruntutari").getValue(Integer.class);
                    if(miktar==null){
                        miktar=0;


                        suViewHolder.miktar=miktar;
                    }
                        else {

                        suViewHolder.miktar=miktar;
                    }

                        if(UrunTekTutar==null){
                            UrunTekTutar=0;

                            suViewHolder.tutar=UrunTekTutar;
                        }
                        else {
                            suViewHolder.tutar=UrunTekTutar;
                        }



                    }




                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Snackbar.make(SuView, "HATA!!!!!!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });


            }



            @NonNull
            @Override
            public SuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.urun1,viewGroup,false);
                SuViewHolder viewHolder = new SuViewHolder(view);
                return viewHolder;
            }
        };

        mySulist.setAdapter(adapter);
        adapter.startListening();


    }



    public static class SuViewHolder extends RecyclerView.ViewHolder{

        TextView urunadi,urunfiyat,urunagirlik;
        ImageFilterView urunfotografi;
        private ImageButton sepetArti;


        private int miktar ;
        private int tutar;


        public SuViewHolder(@NonNull View itemView) {
            super(itemView);
            urunadi= itemView.findViewById(R.id.urunAdi);
            urunfiyat= itemView.findViewById(R.id.urunFiyat);
            urunagirlik= itemView.findViewById(R.id.urunAgirlik);
            urunfotografi=itemView.findViewById(R.id.urunfotografi);
            sepetArti = itemView.findViewById(R.id.sepetarti);



        }
    }

}