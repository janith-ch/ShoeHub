package com.example.shoehub.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.shoehub.R;
import com.example.shoehub.admin.adminprofile;
import com.example.shoehub.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    Button registerbtn, loginbtn;
    EditText lemail, lpassword;
    ProgressBar progressBar;
    FirebaseAuth fauth;
    ImageView backbutton;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseUser currentUser;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        registerbtn = findViewById(R.id.regbutton);
        loginbtn = findViewById(R.id.casual_shoe_btn);
        lemail = findViewById(R.id.username);
        lpassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        backbutton = findViewById(R.id.backbtn);
        fauth = FirebaseAuth.getInstance();

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, Activity_Register.class);
                startActivity(intent);

            }
        });
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = lemail.getText().toString().trim();
                String password = lpassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    lemail.setError("email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    lpassword.setError("password is required");
                    return;
                }
                if (password.length() < 6) {
                    lpassword.setError("password must be 6 characters ");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                Log.d("auth", "auth " + fauth.getUid());
                // authenticate user
                fauth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            DatabaseReference myref = database.getReference("user").child(fauth.getUid());
                            myref.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    User u = snapshot.getValue(User.class);
                                    String userRole = u.getRole();
                                    Log.d("TAG", "Value is:" + userRole);
                                    if (userRole.equals("admin")) {
                                        Log.d("TAG", "have user:" + userRole);
                                        Intent intent = new Intent(LoginActivity.this, adminprofile.class);
                                        startActivity(intent);
                                        //  this.finish();

                                    } else if (userRole.equals("user")) {
                                        Intent intent = new Intent(LoginActivity.this, userprofile.class);
                                        startActivity(intent);
                                        //  this.finish();

                                    } else {
                                        Log.d("auth", "Something Wrong");

                                    }

                                }


                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            //  Toast.makeText(LoginActivity.this,"log in successfully", Toast.LENGTH_SHORT).show();
                            //startActivity(new Intent(getApplicationContext(), userprofile.class));
                        } else {

                            Toast.makeText(LoginActivity.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }


                    }


                });
            }
        });


    }

    public void onDataChange(DataSnapshot dataSnapshot) {

        User u = dataSnapshot.getValue(User.class);
        String userRole = u.getRole();
        Log.d("TAG", "Value is:" + userRole);
        if (userRole.equals("admin")) {
            Log.d("TAG", "have user:" + userRole);
            Intent intent = new Intent(LoginActivity.this, adminprofile.class);
            startActivity(intent);
            this.finish();

        } else {
            Intent intent = new Intent(LoginActivity.this, userprofile.class);
            startActivity(intent);
            this.finish();

        }

    }

}
