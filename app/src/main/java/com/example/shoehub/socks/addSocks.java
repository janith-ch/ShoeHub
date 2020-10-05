package com.example.shoehub.socks;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.shoehub.R;
import com.example.shoehub.models.add_socks;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class addSocks extends AppCompatActivity {
    EditText price,brand,id;
    Button upload,chooes;
    DatabaseReference reff;
    add_socks sockMember;
    ImageView img;
    StorageReference StorageRef;
    long maxid;
    public Uri imguri;
    private StorageTask uploadTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_socks);
        StorageRef = FirebaseStorage.getInstance().getReference("socks");
        price = findViewById(R.id.sock_price);
        brand = findViewById(R.id.sock_brand);
        id = findViewById(R.id.sock_id);
        upload = findViewById(R.id.upload_sock);
        sockMember=new add_socks();
        chooes = findViewById(R.id.chooes_sock_image);
        img = findViewById(R.id.add_sock_image);
        reff = FirebaseDatabase.getInstance().getReference().child("socks");

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                    maxid=(dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sockMember.setPrice(price.getText().toString());
                sockMember.setBrand(brand.getText().toString());
                sockMember.setId(id.getText().toString());
                reff.child(id.getText().toString()).setValue(sockMember);
                Toast.makeText(addSocks.this,"add Socks successfully",Toast.LENGTH_LONG).show();
                Fileupload();
            }
        });

        chooes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Filechooser();
            }
        });

    }

    private String getExtention(Uri uri){

        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    private void Fileupload(){

        StorageReference Ref = StorageRef.child(System.currentTimeMillis()+"."+getExtention(imguri));
        Ref.putFile(imguri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(addSocks.this,"Image upload successfully",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {

                    }
                });

    }
    private void Filechooser(){

        Intent intent =new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null){

            imguri = data.getData();
            img.setImageURI(imguri);
        }
    }
}