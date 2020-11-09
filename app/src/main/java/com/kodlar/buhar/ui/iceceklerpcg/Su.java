package com.kodlar.buhar.ui.iceceklerpcg;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.kodlar.buhar.GirisEkrani;
import com.kodlar.buhar.Urun;
import com.kodlar.buhar.ui.iceceklerpcg.icecekler2;
import com.kodlar.buhar.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;


public class Su extends Fragment  {

    private View SuView;
    private RecyclerView mySulist;
    private DatabaseReference SuRef,ContacsRef;
    private FirebaseAuth mAuth;
    private String currentUserID;
    private TextView denemetext;
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


       // ContacsRef = FirebaseDatabase.getInstance().getReference().child("Kampus").child("icecekler").child("Su").child(currentUserID);
        SuRef = FirebaseDatabase.getInstance().getReference().child("Kampus").child("icecekler").child("Su");



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

                SuRef.child(userIDs).addValueEventListener(new ValueEventListener(){

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {

                            //    String urunfotografi = dataSnapshot.child("Su").child("image").getValue(String.class);
                            String urunadi = dataSnapshot.child("urunadi").getValue(String.class);
                            String urunagirlik = dataSnapshot.child("urunagirlik").getValue(String.class);
                            String urunfiyat = dataSnapshot.child("urunfiyati").getValue(String.class);

                            suViewHolder.urunadi.setText(urunadi);
                            suViewHolder.urunfiyat.setText(urunfiyat);
                            suViewHolder.urunagirlik.setText(urunagirlik);

                            // suViewHolder.urunfotografi.setImageURI(Uri.parse(urunfotografi));

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

      public SuViewHolder(@NonNull View itemView) {
        super(itemView);
          urunadi= itemView.findViewById(R.id.urunAdi);
          urunfiyat= itemView.findViewById(R.id.urunFiyat);
          urunagirlik= itemView.findViewById(R.id.urunAgirlik);
          urunfotografi=itemView.findViewById(R.id.urunfotografi);

    }
}

}