package com.example.shoehub.socks;
//package com.example.shoehub.socks;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//
//import com.example.shoehub.R;
//
//public class socks extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_socks);
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
import android.widget.ImageView;
import com.example.shoehub.R;
import com.example.shoehub.auth.userprofile;
import com.example.shoehub.models.add_socks;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class socks extends AppCompatActivity {

    DatabaseReference ref;
    ImageView text1;
    private FirebaseRecyclerOptions<add_socks> options;
    private FirebaseRecyclerAdapter<add_socks, SockViewHolder>adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socks);
        text1 = findViewById(R.id.formal_user_button);
        recyclerView = findViewById(R.id.recyclerView_Sock);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ref = FirebaseDatabase.getInstance().getReference("socks");

        options = new FirebaseRecyclerOptions.Builder<add_socks>().setQuery(ref,add_socks.class).build();
        adapter= new FirebaseRecyclerAdapter<add_socks, SockViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull SockViewHolder holder, int position, @NonNull add_socks model) {
                holder.textViewID.setText(""+model.getPrice());
                holder.getTextViewName.setText(""+model.getBrand());
                Picasso.get().load(model.getImage()).into(holder.profilePic);
            }

            @NonNull
            @Override
            public SockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view_layout,parent,false);
                return new SockViewHolder(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);

        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(socks.this, userprofile.class);
                startActivity(intent);
            }
        });
    }
}