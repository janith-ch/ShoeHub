package com.example.shoehub.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.shoehub.R;
import com.example.shoehub.auth.LoginActivity;
import com.example.shoehub.casualshoes.add_casual_shoes;
import com.example.shoehub.formalshoes.add_formal_shoes;
import com.example.shoehub.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class adminprofile extends AppCompatActivity {

    DatabaseReference ref;
    FirebaseAuth firebaseAuth;
    User userData;
    TextView email, username;
    Button addcasual,stockbtn,logout,addformal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminprofile);
        email = findViewById(R.id.admin_email);
        username = findViewById(R.id.admin_username);
        addcasual = findViewById(R.id.admin_casual_button);
        stockbtn = findViewById(R.id.admin_View_button);
        logout = findViewById(R.id.admin_logout);
        addformal=findViewById(R.id.add_formal_admin);


        firebaseAuth = FirebaseAuth.getInstance();
        String user = firebaseAuth.getCurrentUser().getUid();
        ref = FirebaseDatabase.getInstance().getReference("user").child(user);
        userData = new User();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                userData = snapshot.getValue(User.class);
                email.setText(userData.getEmail().toString());
                Log.d("testmail", "mail : " + userData.getEmail());
                username.setText(userData.getUsername().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        addcasual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(adminprofile.this, add_casual_shoes.class);
                startActivity(intent);
            }
        });

        stockbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(adminprofile.this, admin_home.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(adminprofile.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        addformal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(adminprofile.this, add_formal_shoes.class);
                startActivity(intent);
            }
        });

    }
}