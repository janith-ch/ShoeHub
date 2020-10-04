package com.example.shoehub.auth;

import androidx.appcompat.app.AppCompatActivity;


import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.shoehub.R;
import com.example.shoehub.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Activity_Register extends AppCompatActivity {

    Button rloginbtn, submitbtn;
    EditText rname, remail, rphone, raddress, rpassword;
    FirebaseAuth fauth;
    ProgressBar progressBar;
    DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__register);
        rloginbtn = findViewById(R.id.reg_login_button);
        submitbtn = findViewById(R.id.admin_casual_shoe_btn);
        rname = findViewById(R.id.username);
        remail = findViewById(R.id.email);
        rphone = findViewById(R.id.phone);
        raddress = findViewById(R.id.address);
        rpassword = findViewById(R.id.password);

        ref = FirebaseDatabase.getInstance().getReference("user");

        fauth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar2);
        if (fauth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }

        rloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_Register.this, LoginActivity.class);
                startActivity(intent);

            }

        });
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = remail.getText().toString().trim();
                final String password = rpassword.getText().toString().trim();
                final String phone = rphone.getText().toString().trim();
                final String address = raddress.getText().toString().trim();
                final String username = rname.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    remail.setError("email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    rpassword.setError("password is required");
                    return;
                }
                if (password.length() < 6) {
                    rpassword.setError("password must be 6 characters ");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                //register user in fire base
                fauth.createUserWithEmailAndPassword(email, password).addOnCompleteListener((new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Activity_Register.this, "user Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            //
                            String uid = fauth.getCurrentUser().getUid();
                            User user = new User();
                            user.setEmail(email);
                            user.setPassword(password);
                            user.setPhone(phone);
                            user.setAddress(address);
                            user.setUsername(username);
                            user.setRole("user");
                            ref.child(uid.toString()).setValue(user);


                        } else {
                            Toast.makeText(Activity_Register.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }));
            }
        });


    }
}