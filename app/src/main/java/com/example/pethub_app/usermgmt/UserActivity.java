package com.example.pethub_app.usermgmt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pethub_app.MainActivity;
import com.example.pethub_app.R;

import androidx.appcompat.app.AppCompatActivity;

public class UserActivity extends AppCompatActivity {

    Button btn_signin,btn_signup,btn_skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        btn_signin=(Button) findViewById(R.id.button_login_page);
        btn_signup=(Button) findViewById(R.id.button_register_page);
        btn_skip=(Button) findViewById(R.id.button_skip_page);

        btn_signin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(UserActivity.this,LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(UserActivity.this,RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        btn_skip.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent skipIntent = new Intent(UserActivity.this, MainActivity.class);
                startActivity(skipIntent);
            }
        });
    }
}
