package com.kodlar.buhar.ui.iceceklerpcg;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
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
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kodlar.buhar.SepetController;
import com.kodlar.buhar.Urun;
import com.kodlar.buhar.R;
import com.kodlar.buhar.ui.Sepetimpcg.Sepet;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class Su extends Fragment  {

    private View SuView;
    private RecyclerView mySulist;
    private DatabaseReference SuRef,SepetRef,SepetTutarRef;
    private FirebaseAuth mAuth;
    private String currentUserID;

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

        LayoutInflater lf = getActivity().getLayoutInflater();


        TextView   text = (TextView)SuView.findViewById(R.id.denemetxt);


        text.setText("123123123");
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

                String userIDs = getRef(i).getKey();
                SepetController sepetislemleri = new SepetController();

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
                                SepetRef=FirebaseDatabase.getInstance().getReference().child("Kullanıcılar").child(currentUserID).child("Sepet").child("Urunlistesi");
                            if(  suViewHolder.miktar==0) {
                             suViewHolder.miktar++;

                                SepetRef.child(userIDs).setValue(new Urun(urunadi, urunagirlik, urunfiyat, urunfotografi, urunid));
                                SepetRef.child(userIDs).child("miktar").setValue(   suViewHolder.miktar);

                                   Snackbar.make(SuView, "Sepetinize ürün eklendi.", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();




                                SepetTutarRef.child("TUTAR").setValue(  sepetislemleri.getToplamFiyat());




                            }else {
                                suViewHolder.miktar++;



                                SepetRef.child(userIDs).child("miktar").setValue(suViewHolder.miktar);
                                suViewHolder.sepettext.setText("" + suViewHolder.miktar);
                                urun.setMiktar(suViewHolder.miktar);







                          int tutar=+ urunfiyat*suViewHolder.miktar;
                          sepetislemleri.setToplamFiyat(tutar);


                                SepetTutarRef.child("TUTAR").setValue(  sepetislemleri.getToplamFiyat());

                            }

                            }
                        });


                        suViewHolder.sepetEksi.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DatabaseReference SepetRef;
                                SepetRef=FirebaseDatabase.getInstance().getReference().child("Kullanıcılar").child(currentUserID).child("Sepet").child("Urunlistesi");
                                if(  suViewHolder.miktar<2) {

                                   SepetRef.child(userIDs).removeValue();



                                    Snackbar.make(SuView, "Sepetinizden ürün silindi.", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }else {

                                    suViewHolder.miktar--;

                                    SepetRef.child(userIDs).child("miktar").setValue(suViewHolder.miktar);
                                    suViewHolder.sepettext.setText("" + suViewHolder.miktar);
                                    urun.setMiktar(suViewHolder.miktar);

                                }
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

                    if(miktar==null){
                        miktar=0;
                        suViewHolder.sepettext.setText("" + miktar);

                        suViewHolder.miktar=miktar;
                    }
                        else {
                        suViewHolder.sepettext.setText("" + miktar);
                        suViewHolder.miktar=miktar;
                    }





                    }




                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Snackbar.make(SuView, "HATA!!!!!!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });
                SepetTutarRef.child(userIDs).addValueEventListener(new ValueEventListener(){
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {

                        Integer tutar = dataSnapshot.child("TUTAR").getValue(Integer.class);

                        if(tutar==null){
                            tutar=0;


                           sepetislemleri.setToplamFiyat(tutar);
                        }
                        else {

                            sepetislemleri.setToplamFiyat(tutar);
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

                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.urun,viewGroup,false);
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
        private ImageButton sepetEksi;
        private TextView sepettext;
        private int miktar ;
        private int tutar;
        ArrayList<Integer> tutarlist = new ArrayList<>();

        public SuViewHolder(@NonNull View itemView) {
            super(itemView);
            urunadi= itemView.findViewById(R.id.urunAdi);
            urunfiyat= itemView.findViewById(R.id.urunFiyat);
            urunagirlik= itemView.findViewById(R.id.urunAgirlik);
            urunfotografi=itemView.findViewById(R.id.urunfotografi);
            sepetArti = itemView.findViewById(R.id.sepetarti);
            sepettext=itemView.findViewById(R.id.sepettext);
            sepetEksi = itemView.findViewById(R.id.sepeteksi);

        }
    }

}