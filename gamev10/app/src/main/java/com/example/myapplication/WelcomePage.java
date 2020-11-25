package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class WelcomePage extends AppCompatActivity {
    MediaPlayer mySong;
    boolean soundOn = true;

    public void openGameActivity() {
        Intent intent = new Intent(this, Game.class);
        startActivity(intent);
    }

    public int getRandomNumber() {
        Random random = new Random();
        return random.nextInt(8);
    }

    public boolean isPlaying = false;

    private void startMusic(){
        if(!isPlaying) {
            try {
                mySong.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        isPlaying = false;
                        mediaPlayer.reset();
                        String name = "song" + getRandomNumber();
                        System.out.println(name);
                        int resource = getResources().getIdentifier(name, "raw", "com.example.myapplication");
                        mySong = MediaPlayer.create(WelcomePage.this, resource);
                        mySong.start();
                        startMusic();
                    }

                });
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (Exception e) {
                System.out.println("Exception of type : " + e.toString());
                e.printStackTrace();
            }

            isPlaying = true;
        }
    }
//    String name = "song" + getRandomNumber();
//    int resource = getResources().getIdentifier(name, "raw", "com.example.myapplication");
//    mySong = MediaPlayer.create(WelcomePage.this, resource);
//        mySong.start();


    public void openProfileActivity(Intent i) {
        startActivity(i);
    }

    public void toggleSound(){
        if(soundOn) {
            mySong.pause();
            soundOn = false;
        }
        else {
            mySong.start();
            soundOn = true;
        }

    }


    public void playSong(){
        if(isPlaying)
            mySong.reset();
        isPlaying = false;

        String name = "song" + getRandomNumber();
        int resource = getResources().getIdentifier(name, "raw", "com.example.myapplication");
        mySong = MediaPlayer.create(WelcomePage.this, resource);
        mySong.start();
        System.out.println(name);
        startMusic();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        Intent intent = getIntent();
        final String username = intent.getStringExtra("USER_NAME");
        Toast.makeText(WelcomePage.this, "Welcome " + username, Toast.LENGTH_SHORT).show();

        playSong();

        Button startBtn = findViewById(R.id.startBtn);
        Switch soundSwitch = findViewById(R.id.soundSwitch);
        Button shuffleBtn = findViewById(R.id.shuffleBtn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGameActivity();
            }
        });
        shuffleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSong();
            }
        });
        soundSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSound();
            }
        });

        Button profileBtn = findViewById(R.id.profileBtn);
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), ProfileActivity.class);
                i.putExtra("USER_NAME", username);
                openProfileActivity(i);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mySong.release();
    }

}