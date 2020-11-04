package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    public void openNewActivity(){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("User");
        final String userId = myRef.push().getKey();
        Button registerBtn = findViewById(R.id.registerBtn);
        Button loginBtn = findViewById(R.id.loginBtn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity();
                //setContentView(R.layout.activity_register);
            }
        });


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText user = findViewById(R.id.user);
                String auser = user.getText().toString();
                EditText pass = findViewById(R.id.passwd);
                String password = pass.getText().toString();
                myRef.child(userId).child("username").setValue(auser);
                myRef.child(userId).child("password").setValue(password);
            }
        });
    }
}
