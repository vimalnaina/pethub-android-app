package com.example.pethub_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.pethub_app.usermgmt.UserActivity;

import androidx.appcompat.app.AppCompatActivity;
import static java.lang.Thread.sleep;

public class SplashActivity extends AppCompatActivity {

    ImageView splashImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splashImage=(ImageView) findViewById(R.id.image_splash);

        Animation myanim= AnimationUtils.loadAnimation(this,R.anim.myanimation);
        splashImage.startAnimation(myanim);

        Thread myThread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    sleep(4000);

                    Intent i=new Intent(SplashActivity.this, UserActivity.class);
                    startActivity(i);
                    finish();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        myThread.start();
    }
}