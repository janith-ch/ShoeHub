package com.example.shoehub.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.shoehub.R;

public class admin_home extends AppCompatActivity {

    Button formal,casual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        casual = findViewById(R.id.admin_casual_shoe_btn);
        formal = findViewById(R.id.admin_formal_button);


        casual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(admin_home.this, casual_shoes_admin.class);
                startActivity(intent);
            }
        });


        formal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(admin_home.this, formal_shoes_admin.class);
                startActivity(intent);
            }
        });
    }
}