package com.example.pethub_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnAdopt,btnDonate,btnTraining,btnDoctor,btnHostel,btnProducts,btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdopt=(Button) findViewById(R.id.buttonAdopt);
        btnDonate=(Button) findViewById(R.id.buttonDonate);
        btnTraining=(Button) findViewById(R.id.buttonTraining);
        btnDoctor=(Button) findViewById(R.id.buttonDoctor);
        btnHostel=(Button) findViewById(R.id.buttonHostel);
        btnProducts=(Button) findViewById(R.id.buttonProducts);
        btnSettings=(Button) findViewById(R.id.buttonSettings);

        btnAdopt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent adoptIntent=new Intent(MainActivity.this,AdoptActivity.class);
                startActivity(adoptIntent);
            }
        });

        btnDonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent donateIntent=new Intent(MainActivity.this,Donate2Activity.class);
                startActivity(donateIntent);
            }
        });

        btnTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(MainActivity.this, "Available Soon!", Toast.LENGTH_SHORT).show();
            }
        });

        btnDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(MainActivity.this, "Available Soon!", Toast.LENGTH_SHORT).show();
            }
        });

        btnHostel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(MainActivity.this, "Available Soon!", Toast.LENGTH_SHORT).show();
            }
        });

        btnProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(MainActivity.this, "Available Soon!", Toast.LENGTH_SHORT).show();
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(MainActivity.this, "Available Soon!", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
