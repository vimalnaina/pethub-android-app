package com.example.pethub_app;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    TextView txtName = this.<TextView>findViewById(R.id.proName), txtEmail, txtAddress, txtContact;
    ImageView image;
    Button btnLogout;
    FirebaseAuth mAuth;
    DatabaseReference dRef;
    ValueEventListener eventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txtEmail = (TextView) findViewById(R.id.proEmail);
        txtAddress = (TextView) findViewById(R.id.proAddress);
        txtContact = (TextView) findViewById(R.id.proContact);
        image = (ImageView) findViewById(R.id.proImage);
        btnLogout = (Button) findViewById(R.id.btnlogout);

        mAuth = FirebaseAuth.getInstance();

        final String uid = mAuth.getCurrentUser().getUid();

        if(!uid.equals("")){
            dRef = FirebaseDatabase.getInstance().getReference("Users").child(uid);
            eventListener = dRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    HashMap proData = new HashMap();
                    String key = dataSnapshot.getKey();
                    String value = dataSnapshot.getValue(String.class);
                    proData.put(key, value);
                    txtName.setText(proData.get("Name").toString());
                    txtEmail.setText(proData.get("Email").toString());
                    txtContact.setText(proData.get("Email").toString());
                    if (proData.get("Address") != null) {
                        txtAddress.setText(proData.get("Address").toString());
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else {
            Toast.makeText(ProfileActivity.this, "You Are Not Logged In!", Toast.LENGTH_SHORT).show();
        }
    }
}
