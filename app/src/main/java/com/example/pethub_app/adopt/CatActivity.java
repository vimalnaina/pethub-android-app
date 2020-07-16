package com.example.pethub_app.adopt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import com.example.pethub_app.R;
import com.example.pethub_app.adapter.MyAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CatActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    List<PetData> myPetList;
    SwipeRefreshLayout swipeRefreshLayout;

    private DatabaseReference dRef;
    private ValueEventListener eventListener;

    ProgressDialog pd;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet);

        //Refresh Layout Logic...
        swipeRefreshLayout = findViewById(R.id.refreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                //your code on swipe refresh
                //we are checking networking connectivity
                boolean connection=isNetworkAvailable();
                if(connection){
                }
                else{
                    Toast.makeText(CatActivity.this,"Internet Connection Error!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        swipeRefreshLayout.setColorSchemeColors(R.color.colorBlack);
        //Refresh Layout End...

        mRecyclerView = (RecyclerView) findViewById(R.id.petRecyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(CatActivity.this,1);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        pd = new ProgressDialog(this);
        pd.setMessage("Loading...");

        myPetList = new ArrayList<>();

        final MyAdapter myAdapter = new MyAdapter(CatActivity.this, myPetList);
        mRecyclerView.setAdapter(myAdapter);

        dRef = FirebaseDatabase.getInstance().getReference("PetData").child("Cat");
        pd.show();
        eventListener = dRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myPetList.clear();
                for(DataSnapshot itemSnapshot : dataSnapshot.getChildren()){
                    PetData petData = itemSnapshot.getValue(PetData.class);
                    myPetList.add(petData);
                }

                myAdapter.notifyDataSetChanged();
                pd.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                pd.dismiss();
            }
        });

    }
    public boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager=(ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        return networkInfo !=null;
    }
}