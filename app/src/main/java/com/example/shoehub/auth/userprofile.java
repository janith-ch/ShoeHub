//package com.example.shoehub;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//
//public class userprofile extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_userprofile);
//    }
//}

package com.example.shoehub.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.shoehub.R;
import com.example.shoehub.home;
import com.example.shoehub.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class userprofile extends AppCompatActivity {


    DatabaseReference ref;
    FirebaseAuth firebaseAuth;
    User userData;
    TextView email, username, phone, address;
    Button homebtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);
        email = findViewById(R.id.user_email);
        username = findViewById(R.id.user_username);
        phone = findViewById(R.id.user_phone);
        address = findViewById(R.id.user_address);
        homebtn = findViewById(R.id.profile_home_button);

        firebaseAuth = FirebaseAuth.getInstance();
        String user = firebaseAuth.getCurrentUser().getUid();
        ref = FirebaseDatabase.getInstance().getReference("user").child(user);
        userData = new User();

        email.setText("test");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                userData = snapshot.getValue(User.class);
                email.setText(userData.getEmail().toString());
                Log.d("testmail", "mail : " + userData.getEmail());
                username.setText(userData.getUsername().toString());
                phone.setText(userData.getPhone().toString());
                address.setText(userData.getAddress().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(userprofile.this, home.class);
                startActivity(intent);
            }
        });


    }
}