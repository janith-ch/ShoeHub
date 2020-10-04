//package com.example.shoehub;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//
//public class userprofile extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_userprofile);
//    }
//}

package com.example.shoehub.auth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;



import com.example.shoehub.R;
import com.example.shoehub.feedback_u.feedback;
import com.example.shoehub.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class userprofile extends AppCompatActivity {


    DatabaseReference ref;
    FirebaseAuth fAuth;
    User userData;

    TextView email, username, phone, address,password;
    Button homebtn ,fbtn,changeproimage,logout;//3

    //zz
    ImageView profileimage;//1
    StorageReference storageReference;
    FirebaseStorage fStore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);
        email = findViewById(R.id.user_email);
        username = findViewById(R.id.user_username);
        phone = findViewById(R.id.user_phone);
        address = findViewById(R.id.user_address);
        //password
        password = findViewById(R.id.password);
        homebtn = findViewById(R.id.profile_home_button);
        fbtn = findViewById(R.id.feedback_button);
        logout = findViewById(R.id.user_logout);

        //zz
        profileimage = findViewById(R.id.profileimage);//2
        changeproimage = findViewById(R.id.editprobtn);//4

        fAuth = FirebaseAuth.getInstance();
        String user = fAuth.getCurrentUser().getUid();
        ref = FirebaseDatabase.getInstance().getReference("user").child(user);
        userData = new User();
        //zz
        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference profilRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"profile.jpg");
        profilRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileimage);
            }
        });

        email.setText("test");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                userData = snapshot.getValue(User.class);
                email.setText(userData.getEmail().toString());
                Log.d("testmail", "mail : " + userData.getEmail());
                username.setText(userData.getUsername().toString());
                phone.setText(userData.getPhone().toString());
                address.setText(userData.getAddress().toString());
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(userprofile.this, home.class);
                startActivity(intent);
            }
        });
        fbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(userprofile.this, feedback.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(userprofile.this, LoginActivity.class);
                startActivity(intent);
            }
        });


       //4
        changeproimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open galarry
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent,1000);

                //EDITPROFILE INTENT
                    //Intent i = new Intent(v.getContext(),editprofile.class);
                    //i.putExtra("fullName",username.getText().toString());
                    //i.putExtra("email",email.getText().toString());
                    //i.putExtra("phone",phone.getText().toString());
                    //i.putExtra("address",address.getText().toString());
                    //i.putExtra("password",password.getText().toString());
                    //startActivity(i);

            }
        });



    }

    //z

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            if(resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();

                profileimage.setImageURI(imageUri);

                uploadImageToFirebase(imageUri);


            }
        }

    }





    private void uploadImageToFirebase(Uri imageuri) {
        //upload image to firebase

        final StorageReference fileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"profile.jpg");
        fileRef.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(userprofile.this,"Image Upload",Toast.LENGTH_SHORT).show();
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profileimage);
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(userprofile.this,"Image Upload fail",Toast.LENGTH_SHORT).show();
            }
        });

    }


}