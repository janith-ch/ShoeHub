package com.example.shoehub.admin;

//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//
//import com.example.shoehub.R;
//
//public class formal_shoes_admin extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_formal_shoes_admin);
//    }
//}



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import com.example.shoehub.R;
import com.example.shoehub.casualshoes.editCashualShoes;
import com.example.shoehub.models.addshoes;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class formal_shoes_admin extends AppCompatActivity {

    DatabaseReference ref;
    ImageView text1;
    Button delete,update;
    private FirebaseRecyclerOptions<addshoes> options;
    private FirebaseRecyclerAdapter<addshoes, MyViewHolder2>adapter;
    private RecyclerView recyclerView;
    public static List<addshoes> addshoes,keylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formal_shoes_admin);
        text1 = findViewById(R.id.formal_user_button);
        delete =findViewById(R.id.Delete_button);
        update = findViewById(R.id.update_button);
        recyclerView = findViewById(R.id.recyclerView_formal);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        ref = FirebaseDatabase.getInstance().getReference("formal");

        options = new FirebaseRecyclerOptions.Builder<addshoes>().setQuery(ref,addshoes.class).build();
        adapter= new FirebaseRecyclerAdapter<addshoes, MyViewHolder2>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder2 holder, int position, @NonNull final addshoes model) {
                holder.textViewID.setText(""+model.getPrice());
                holder.getTextViewName.setText(""+model.getBrand());
                final String price1= model.getPrice();
                final String brand1= model.getBrand();
                holder.update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addshoes = new ArrayList<>();
                        addshoes.add(model);

                        Intent intent = new Intent(formal_shoes_admin.this, editCashualShoes.class);
                        intent.putExtra("test",price1);
                        intent.putExtra("test1",brand1);
                        startActivity(intent);
                    }
                });

                Picasso.get().load(model.getImage()).into(holder.profilePic);
            }

            @NonNull
            @Override
            public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_single_view_layout,parent,false);
                return new MyViewHolder2(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);


    }



}