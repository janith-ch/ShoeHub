package com.example.shoehub.casualshoes;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoehub.R;
import com.squareup.picasso.Picasso;

public class MyViewHolder extends RecyclerView.ViewHolder {
   TextView textViewID,getTextViewName;
   ImageView profilePic;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewID=itemView.findViewById(R.id.textView1ID);
        getTextViewName=itemView.findViewById(R.id.textView2ID);
        profilePic = itemView.findViewById(R.id.card_image);


    }
}
