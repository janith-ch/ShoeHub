package com.example.shoehub.socks;



import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shoehub.R;
import com.example.shoehub.admin.sock_admin;
import com.example.shoehub.models.add_socks;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class editSocks extends AppCompatActivity {
    EditText updatex, updateb;
    Button buttonup;

    add_socks addsocks;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_socks);

        updatex = findViewById(R.id.update_price_sock);
        buttonup = findViewById(R.id.sock_update_upload);
        updateb = findViewById(R.id.sock_edit_brand);

        this.addsocks = sock_admin.Add_socks.get(0);
        sock_admin.Add_socks.remove(0);

        final String price = getIntent().getStringExtra("test");
        final String brand = getIntent().getStringExtra("test1");
        updatex.setHint(price);
        updateb.setHint(brand);

        buttonup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("socks");

                String pricex = updatex.getText().toString().trim();
                addsocks.setPrice(pricex);
                String brandb = updateb.getText().toString().trim();
                addsocks.setBrand(brandb);

                databaseReference.child(addsocks.getId()).setValue(addsocks);
                Toast.makeText(editSocks.this," updated successfully",Toast.LENGTH_LONG).show();
                Log.d("auth", "updated");

            }
        });


    }
}