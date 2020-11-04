package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("User");
        final String userId = myRef.push().getKey();
        Button registerBtn = findViewById(R.id.registerBtn);


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText user = findViewById(R.id.user);
                String auser = user.getText().toString();
                EditText passwd = findViewById(R.id.password);
                String password = passwd.getText().toString();
                EditText passwd2 = findViewById(R.id.password2);
                String password2 = passwd2.getText().toString();
                if(password.equals(password2)) {
                    myRef.child(userId).child("username").setValue(auser);
                    myRef.child(userId).child("password").setValue(password);
                }
                else
                    System.out.println("---------------------------------Not ok-------------------------------------------");
            }
        });
    }
}
