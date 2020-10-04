package com.example.shoehub.admin;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoehub.R;


public class MyViewHolder2 extends RecyclerView.ViewHolder {
    TextView textViewID,getTextViewName;
    ImageView profilePic;
    Button update;
    public MyViewHolder2(@NonNull View itemView) {
        super(itemView);
        textViewID=itemView.findViewById(R.id.AtextView1ID_formal);
        getTextViewName=itemView.findViewById(R.id.update_brand_text);
        profilePic = itemView.findViewById(R.id.Acard_image_formal);
        update = itemView.findViewById(R.id.update_button);




    }
}
