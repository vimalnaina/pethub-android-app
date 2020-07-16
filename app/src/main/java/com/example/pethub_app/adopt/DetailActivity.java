package com.example.pethub_app.adopt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pethub_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailActivity extends AppCompatActivity {

    private DatabaseReference dRef;
    private ValueEventListener eventListener;

    String petId;
    TextView title, mHeight, fHeight, mWeight, fWeight, lifeSpan, temperament, petGroup, price;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        title = (TextView) findViewById(R.id.descTitle);
        mHeight = (TextView) findViewById(R.id.descMHeight);
        fHeight = (TextView) findViewById(R.id.descFHeight);
        mWeight = (TextView) findViewById(R.id.descMWeight);
        fWeight = (TextView) findViewById(R.id.descFWeight);
        lifeSpan = (TextView) findViewById(R.id.descLifespan);
        temperament = (TextView) findViewById(R.id.descTemp);
        petGroup = (TextView) findViewById(R.id.descPetGroup);
        price = (TextView) findViewById(R.id.descPrice);
        image = (ImageView) findViewById(R.id.descImg);

        Bundle mBundle = getIntent().getExtras();
        if(mBundle!=null){
            petId = mBundle.getString("Id");
            mHeight.setText(mBundle.getString("MaleHeight"));
            fHeight.setText(mBundle.getString("FemaleHeight"));
            mWeight.setText(mBundle.getString("MaleWeight"));
            fWeight.setText(mBundle.getString("FemaleWeight"));
            lifeSpan.setText(mBundle.getString("Lifespan"));
            temperament.setText(mBundle.getString("Temperament"));
            petGroup.setText(mBundle.getString("PetGroup"));
            price.setText(mBundle.getString("Price"));
            title.setText(mBundle.getString("Breed"));
            Glide.with(this)
                    .load(mBundle.getString("Image"))
                    .into(image);
        }
    }
}