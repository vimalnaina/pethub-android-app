package com.example.pethub_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class DonateActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText etBreed, etMaleHeight, etFemaleHeight, etMaleWeight, etFemaleWeight, etLifespan, etTemperament, etPetgroup, etPrice;
    Button upload, browse;
    Spinner spinner;
    ImageView img;
    private Uri imageUri;
    String petType="Dog";

    FirebaseAuth mAuth;
    DatabaseReference dRef;
    StorageReference sRef;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        etBreed = (EditText) findViewById(R.id.dntbreed);
        etMaleHeight = (EditText) findViewById(R.id.dntmaleheight);
        etFemaleHeight = (EditText) findViewById(R.id.dntfemaleheight);
        etMaleWeight = (EditText) findViewById(R.id.dntmaleweight);
        etFemaleWeight = (EditText) findViewById(R.id.dntfemaleweight);
        etLifespan = (EditText) findViewById(R.id.dntlifespan);
        etTemperament = (EditText) findViewById(R.id.dnttemperament);
        etPetgroup = (EditText) findViewById(R.id.dntpetgroup);
        etPrice = (EditText) findViewById(R.id.dntprice);
        upload = (Button) findViewById(R.id.btnupload);
        browse = (Button) findViewById(R.id.btnBrowse);
        img = (ImageView) findViewById(R.id.dntimg);
        spinner = (Spinner) findViewById(R.id.dnttype);

        mAuth = FirebaseAuth.getInstance();
        dRef = FirebaseDatabase.getInstance().getReference().child("PetData");
        sRef = FirebaseStorage.getInstance().getReference();

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadData();
            }
        });

        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                browseImage();
            }
        });

        spinner.setOnItemSelectedListener(this);

        List<String> categories = new ArrayList<String>();
        categories.add("Dog");
        categories.add("Cat");
        categories.add("Bird");
        categories.add("Rabbit");
        categories.add("Turtle");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    private void browseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"select image"),1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() !=null){
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                img.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void uploadData() {
        final String breed, maleHeight, femaleHeight, maleWeight, femaleWeight, lifespan, temperament, petgroup, price;
        breed = etBreed.getText().toString();
        maleHeight = etMaleHeight.getText().toString();
        femaleHeight = etFemaleHeight.getText().toString();
        maleWeight = etMaleWeight.getText().toString();
        femaleWeight = etFemaleWeight.getText().toString();
        lifespan = etLifespan.getText().toString();
        temperament = etTemperament.getText().toString();
        petgroup = etPetgroup.getText().toString();
        price = etPrice.getText().toString();

        pd.setMessage("Uploading...");
        pd.show();

        StorageReference filePath = sRef.child("PetImages").child(imageUri.getLastPathSegment());
        filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String downloadUrl = taskSnapshot.getStorage().getDownloadUrl().toString();
                DatabaseReference pet_upl = dRef.push();
                pet_upl.child(petType).child("Breed").setValue(breed);
                pet_upl.child(petType).child("MaleHeight").setValue(maleHeight);
                pet_upl.child(petType).child("FemaleHeight").setValue(femaleHeight);
                pet_upl.child(petType).child("MaleWeight").setValue(maleWeight);
                pet_upl.child(petType).child("FemaleWeight").setValue(femaleWeight);
                pet_upl.child(petType).child("Lifespan").setValue(lifespan);
                pet_upl.child(petType).child("Temperament").setValue(temperament);
                pet_upl.child(petType).child("Workgroup").setValue(petgroup);
                pet_upl.child(petType).child("Price").setValue(price);
                pet_upl.child(petType).child("Image").setValue(downloadUrl);
                pd.dismiss();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        petType = item;
        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + petType, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> args0) {

    }
}
