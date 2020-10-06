package com.example.shoehub.casualshoes;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shoehub.R;
import com.example.shoehub.admin.casual_shoes_admin;
import com.example.shoehub.models.addshoes;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class editCashualShoes extends AppCompatActivity {
    EditText updatex, updateb;
    Button buttonup;

    addshoes addshoes;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cashual_shoes);

        updatex = findViewById(R.id.update_price);
        buttonup = findViewById(R.id.update_upload);
        updateb = findViewById(R.id.casuala_edit_brand);

        this.addshoes = casual_shoes_admin.addshoes.get(0);
        casual_shoes_admin.addshoes.remove(0);

        final String price = getIntent().getStringExtra("test");
        final String brand = getIntent().getStringExtra("test1");
        updatex.setHint(price);
        updateb.setHint(brand);

        buttonup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("member");

                String pricex = updatex.getText().toString().trim();
                addshoes.setPrice(pricex);
                String brandb = updateb.getText().toString().trim();
                addshoes.setBrand(brandb);

                databaseReference.child(addshoes.getDescription()).setValue(addshoes);

                Toast.makeText(editCashualShoes.this, " updated successfully", Toast.LENGTH_LONG).show();
            }
        });


    }
}