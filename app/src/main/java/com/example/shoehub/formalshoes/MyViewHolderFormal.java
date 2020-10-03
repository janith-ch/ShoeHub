package com.example.shoehub.formalshoes;

//public class MyViewHolderFormal {
//}
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoehub.R;

public class MyViewHolderFormal extends RecyclerView.ViewHolder {
    TextView textViewID,getTextViewName;
    ImageView profilePic;
    public MyViewHolderFormal(@NonNull View itemView) {
        super(itemView);
        textViewID=itemView.findViewById(R.id.AtextView1ID_formal);
        getTextViewName=itemView.findViewById(R.id.AtextView2ID_formal);
        profilePic = itemView.findViewById(R.id.Acard_image_formal);


    }
}
