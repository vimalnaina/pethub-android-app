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

import com.example.pethub_app.adopt.PetData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Donate2Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText etBreed, etMaleHeight, etFemaleHeight, etMaleWeight, etFemaleWeight, etLifespan, etTemperament, etPetgroup, etPrice;
    Button upload, browse;
    Spinner spinner;
    ImageView img;
    private Uri imageUri;
    String petType="Dog", downUrl;

    FirebaseAuth mAuth;
    FirebaseStorage storage;
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
        storage = FirebaseStorage.getInstance();
        sRef = storage.getReference();
        pd = new ProgressDialog(this);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                browseImage();
            }
        });

        //Spinner Logic Start...
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
        //Spinner End...
    }

    private void uploadImage() {

        pd.setMessage("Image Uploading...");
        pd.show();

        StorageReference filePath = sRef.child("PetImages").child(imageUri.getLastPathSegment());
        filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while(!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                downUrl = urlImage.toString();
                pd.dismiss();
                uploadData();
            }
        });
    }

    private void uploadData() {

        //random key generator
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";
        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(15);
        for (int i = 0; i < 15; i++) {
            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());
            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }
        String petId = sb.toString();

        PetData petData = new PetData(
                petId,
                etBreed.getText().toString(),
                etMaleHeight.getText().toString(),
                etFemaleHeight.getText().toString(),
                etMaleWeight.getText().toString(),
                etFemaleWeight.getText().toString(),
                etLifespan.getText().toString(),
                etTemperament.getText().toString(),
                etPetgroup.getText().toString(),
                etPrice.getText().toString(),
                downUrl
        );

        pd.setMessage("Uploading...");
        pd.show();

        FirebaseDatabase.getInstance().getReference("PetData")
                .child(petType).child(petId).setValue(petData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Donate2Activity.this,"Details Uploaded Successfully!",Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Donate2Activity.this,"Failed To Upload!",Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });

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

    //Spinner selected item returns...
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
