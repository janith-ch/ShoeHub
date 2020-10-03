package com.example.shoehub.formalshoes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.shoehub.R;
import com.example.shoehub.auth.userprofile;
import com.example.shoehub.models.addshoes;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Formalshoes extends AppCompatActivity {

    DatabaseReference ref;
    ImageView text1;
    private FirebaseRecyclerOptions<addshoes> options;
    private FirebaseRecyclerAdapter<addshoes, MyViewHolderFormal>adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formalshoe);
        text1 = findViewById(R.id.formal_user_button);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ref = FirebaseDatabase.getInstance().getReference("formal");

        options = new FirebaseRecyclerOptions.Builder<addshoes>().setQuery(ref,addshoes.class).build();
        adapter= new FirebaseRecyclerAdapter<addshoes, MyViewHolderFormal>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolderFormal holder, int position, @NonNull addshoes model) {
                holder.textViewID.setText(""+model.getPrice());
                holder.getTextViewName.setText(""+model.getBrand());
                Picasso.get().load(model.getImage()).into(holder.profilePic);
            }

            @NonNull
            @Override
            public MyViewHolderFormal onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view_layout_formal,parent,false);
                return new MyViewHolderFormal(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);

        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(com.example.shoehub.formalshoes.Formalshoes.this, userprofile.class);
                startActivity(intent);
            }
        });
    }
}
