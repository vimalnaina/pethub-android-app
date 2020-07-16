package com.example.pethub_app.usermgmt;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.annotation.NonNull;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pethub_app.MainActivity;
import com.example.pethub_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etPass;
    Button btnLogin;

    FirebaseAuth mAuth;
    DatabaseReference DR;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = (EditText) findViewById(R.id.letemail);
        etPass = (EditText) findViewById(R.id.letpassword);
        btnLogin = (Button) findViewById(R.id.btnlogin);

        mAuth = FirebaseAuth.getInstance();
        DR = FirebaseDatabase.getInstance().getReference().child("Users");
        pd = new ProgressDialog(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLogin();
            }
        });
    }

    private void startLogin() {
        String email, pass;
        email = etEmail.getText().toString();
        pass = etPass.getText().toString();

        pd.setMessage("Logging In...");
        pd.show();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(LoginActivity.this,"Please Enter Email!",Toast.LENGTH_SHORT).show();
            pd.dismiss();
        }
        else if(TextUtils.isEmpty(pass)){
            Toast.makeText(LoginActivity.this,"Please Enter Password!",Toast.LENGTH_SHORT).show();
            pd.dismiss();
        }
        else {
            mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        checkUser();
                        pd.dismiss();
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid Email Or Password...!", Toast.LENGTH_SHORT).show();
                    }
                    pd.dismiss();

                }
            });
        }
    }

    private void checkUser() {
        final String userId = mAuth.getCurrentUser().getUid();
        DR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(userId)){
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }
                else{
                    Toast.makeText(LoginActivity.this,"User Not Found...!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
