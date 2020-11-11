package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class WelcomePage extends AppCompatActivity {
    ImageView first_card;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        Intent intent = getIntent();
        String username = intent.getStringExtra("USER_NAME");
        Toast.makeText(WelcomePage.this, "Welcome " + username, Toast.LENGTH_SHORT).show();

        first_card = findViewById(R.id.first_card);

        // Points to the root reference
        //var storageRef = firebase.storage().ref();

        // Points to 'images'
        StorageReference imagesRef = storageReference.child("CardImages");

        // Points to 'images/space.jpg'
        // Note that you can use variables to create child values
        String fileName = "BasicCardSprite.jpeg";
        StorageReference spaceRef = imagesRef.child(fileName);

        // File path is 'images/space.jpg'
        String path = spaceRef.getPath();

        // File name is 'space.jpg'
        String name = spaceRef.getName();

        // Points to 'images'
        //StorageReference imagesRef2 = spaceRef.getParent();

// Download directly from StorageReference using Glide
// (See MyAppGlideModule for Loader registration)
        try{
        Glide.with(this /* context */)
                .load(spaceRef.getDownloadUrl())
                .into(first_card);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}