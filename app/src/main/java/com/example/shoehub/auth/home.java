package com.example.shoehub.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.shoehub.R;
import com.example.shoehub.casualshoes.Casualshoes;
import com.example.shoehub.formalshoes.Formalshoes;

public class home extends AppCompatActivity {
    ImageView img1;
    Button casual_btn,formal_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        img1 = findViewById(R.id.home_user_btn);
        casual_btn = findViewById(R.id.admin_casual_shoe_btn);
        formal_btn = findViewById(R.id.admin_formal_button);
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(home.this, userprofile.class);
                startActivity(intent);
            }
        });

        casual_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(home.this, Casualshoes.class);
                startActivity(intent);
            }
        });

        formal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(home.this, Formalshoes.class);
                startActivity(intent);
            }
        });


    }
}