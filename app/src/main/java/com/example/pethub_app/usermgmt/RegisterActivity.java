package com.example.pethub_app.usermgmt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pethub_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText etName, etEmail, etContact, etPass;
    Button btnReg;

    FirebaseAuth mAuth;
    DatabaseReference ref;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = (EditText) findViewById(R.id.etname);
        etEmail = (EditText) findViewById(R.id.etemail);
        etContact = (EditText) findViewById(R.id.etcontact);
        etPass = (EditText) findViewById(R.id.etpassword);
        btnReg = (Button) findViewById(R.id.btnreg);

        mAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference().child("Users");
        pd = new ProgressDialog(this);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startReg();
            }
        });
    }

    private void startReg() {
        final String name, email, pass, contact;
        name = etName.getText().toString();
        email = etEmail.getText().toString();
        pass = etPass.getText().toString();
        contact = etContact.getText().toString();

        pd.setMessage("Signing Up...");
        pd.show();

        int flag_count = 0;
        int flag_validate = 0;
        if(TextUtils.isEmpty(name)) {
            Toast.makeText(RegisterActivity.this, "Please Enter First Name!", Toast.LENGTH_SHORT).show();
            flag_count++;
            pd.dismiss();
        }
        if(TextUtils.isEmpty(email)) {
            Toast.makeText(RegisterActivity.this, "Please Enter Email!", Toast.LENGTH_SHORT).show();
            flag_count++;
            pd.dismiss();
        }else{
            final String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
            // static Pattern object, since pattern is fixed
            Pattern pattern;
            // non-static Matcher object because it's created from the input String
            Matcher matcher;
            pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(email);
            if(!matcher.matches()){
                flag_validate++;
                Toast.makeText(RegisterActivity.this, "Please Enter Valid Email!", Toast.LENGTH_SHORT).show();
            }
        }
        if(TextUtils.isEmpty(pass)) {
            Toast.makeText(RegisterActivity.this, "Please Enter Password!", Toast.LENGTH_SHORT).show();
            flag_count++;
            pd.dismiss();
        }else{
            final String PASS_REGEX = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$).{8,20}$";
            Pattern pattern;
            Matcher matcher;
            pattern = Pattern.compile(PASS_REGEX);
            matcher = pattern.matcher(pass);
            if (!matcher.matches()) {
                flag_validate++;
                String passerrmsg = "It should contain 8-20 characters, at least one digit, at least one upper case alphabet, at least one lower case alphabet, at least one special character, doesn’t contain any white space";
                Toast.makeText(RegisterActivity.this, passerrmsg, Toast.LENGTH_SHORT).show();
            }
            pd.dismiss();
        }
        if(TextUtils.isEmpty(contact)) {
            Toast.makeText(RegisterActivity.this, "Please Enter Contact No!", Toast.LENGTH_SHORT).show();
            flag_count++;
            pd.dismiss();
        }
        if(flag_count==0 && flag_validate==0) {
            mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    String userId = mAuth.getCurrentUser().getUid();
                    DatabaseReference current_user = ref.child(userId);
                    current_user.child("Name").setValue(name);
                    current_user.child("Email").setValue(email);
                    current_user.child("Password").setValue(pass);
                    current_user.child("Contact").setValue(contact);
                    Toast.makeText(RegisterActivity.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                    pd.dismiss();
                }
            });
        }
    }
}