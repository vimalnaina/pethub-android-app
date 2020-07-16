package com.example.pethub_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pethub_app.adopt.BirdActivity;
import com.example.pethub_app.adopt.CatActivity;
import com.example.pethub_app.adopt.DogActivity;
import com.example.pethub_app.adopt.RabbitActivity;
import com.example.pethub_app.adopt.TurtleActivity;

import androidx.appcompat.app.AppCompatActivity;

public class AdoptActivity extends AppCompatActivity {

    Button btnDog, btnCat, btnBird, btnRabbit, btnTurtle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adopt);

        btnRabbit = (Button) findViewById(R.id.adopt_ButtonRabbits);
        btnDog = (Button) findViewById(R.id.adopt_ButtonDogs);
        btnCat = (Button) findViewById(R.id.adopt_ButtonCats);
        btnBird = (Button) findViewById(R.id.adopt_ButtonBirds);
        btnTurtle = (Button) findViewById(R.id.adopt_ButtonTurtles);

        btnDog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdoptActivity.this, DogActivity.class));
            }
        });

        btnCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdoptActivity.this, CatActivity.class));
            }
        });

        btnBird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdoptActivity.this, BirdActivity.class));
            }
        });

        btnRabbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdoptActivity.this, RabbitActivity.class));
            }
        });

        btnTurtle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdoptActivity.this, TurtleActivity.class));
            }
        });
    }
}
