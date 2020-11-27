package com.example.myapplication;

import androidx.annotation.NonNull;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class WelcomePage extends AppCompatActivity {
    MediaPlayer mySong;
    boolean soundOn = true;
    Timer timer;

    public void openGameActivity() {
        Intent i = new Intent(this, Game.class);
        i.putExtra("USER_NAME", getIntent().getStringExtra("USER_NAME"));
        startActivity(i);
    }

    public int getRandomNumber() {
        Random random = new Random();
        return random.nextInt(8);
    }

    public boolean isPlaying = false;

    private void startMusic() {
        if (!isPlaying) {
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

    public void toggleSound() {
        if (soundOn) {
            mySong.pause();
            soundOn = false;
        } else {
            mySong.start();
            soundOn = true;
        }

    }


    public void playSong() {
        if (isPlaying)
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

        final Intent intent = getIntent();
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

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference myRef = database.getReference("User");
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        try {
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                String username = ds.child("username").getValue(String.class);



                                if (intent.getStringExtra("USER_NAME").equals(username)) {
                                    Integer totalTime = ds.child("total_time").getValue(Integer.class);

                                    ds.child("total_time").getRef().setValue(++totalTime);
                                }
                            }
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }

        }, 60000, 60000);
    }

    @Override
    protected void onDestroy() {
        mySong.stop();
        mySong.release();

        timer.cancel();

        super.onDestroy();
    }

}