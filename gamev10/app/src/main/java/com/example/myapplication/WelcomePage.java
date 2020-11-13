package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;


public class WelcomePage extends AppCompatActivity {

    public void openGameActivity() {
        Intent intent = new Intent(this, Game.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);


        Intent intent = getIntent();
        String username = intent.getStringExtra("USER_NAME");
        Toast.makeText(WelcomePage.this, "Welcome " + username, Toast.LENGTH_SHORT).show();

        Button startBtn = findViewById(R.id.startBtn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGameActivity();
            }
        });

    }
}