package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;


public class WelcomePage extends AppCompatActivity {

    public int getRandomNumber() {
        Random random = new Random();
        return random.nextInt(9);
    }

    public void setCardImage(ImageView image){
        Karten card = Karten.values()[getRandomNumber()];
        Log.d("TAG", card.getPath());
        switch (card.getPath()){
            case "cerberus10.jpg":
                image.setImageResource(R.drawable.cerberus10);
                break;
            case "charmander7.jpg":
                image.setImageResource(R.drawable.charmander7);
                break;
            case "deathwing12.jpg":
                image.setImageResource(R.drawable.deathwing12);
                break;
            case "delfinu10.jpg":
                image.setImageResource(R.drawable.delfinu10);
                break;
            case "fox5.jpg":
                image.setImageResource(R.drawable.fox5);
                break;
            case "gorilla12.jpg":
                image.setImageResource(R.drawable.gorilla12);
                break;
            case "penguin6.jpg":
                image.setImageResource(R.drawable.penguin6);
                break;
            case "rechin12.jpg":
                image.setImageResource(R.drawable.rechin12);
                break;
            case "tiger10.jpg":
                image.setImageResource(R.drawable.tiger10);
                break;
        }

    }

    public ArrayList<ImageView> getPlayerDeck(){
        ArrayList<ImageView> playerCards = new ArrayList<ImageView>();

        playerCards.add((ImageView) findViewById(R.id.lowercard3));
        playerCards.add((ImageView) findViewById(R.id.lowercard4));
        playerCards.add((ImageView) findViewById(R.id.lowercard5));
        playerCards.add((ImageView) findViewById(R.id.lowercard6));
        playerCards.add((ImageView) findViewById(R.id.lowercard7));

        return playerCards;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        Intent intent = getIntent();
        String username = intent.getStringExtra("USER_NAME");
        Toast.makeText(WelcomePage.this, "Welcome " + username, Toast.LENGTH_SHORT).show();

        ArrayList<ImageView> playerCards = getPlayerDeck();
        System.out.println("---------------------------------------------------------------------");
        for(int i = 0; i < playerCards.size(); i++){
            ImageView card = playerCards.get(i);
            setCardImage(card);
        }

    }
}