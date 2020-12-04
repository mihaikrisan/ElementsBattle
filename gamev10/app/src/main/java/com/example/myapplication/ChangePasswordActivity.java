package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangePasswordActivity extends AppCompatActivity {

    public void openProfileActivity(Intent i) {
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        //Firebase reference
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("User");
        final String userId = myRef.push().getKey();

        //get user from Toast
        final Intent intent = getIntent();
        final String username = intent.getStringExtra("USER_NAME");

        final EditText oldPassword = findViewById(R.id.oldPassword);
        final EditText newPassword = findViewById(R.id.newPassword);
        final EditText newPassword2 = findViewById(R.id.newPassword2);
        Button submitBtn = findViewById(R.id.changeBtn);

        //clicklistener
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPasswd = oldPassword.getText().toString();
                final String newPasswd = newPassword.getText().toString();
                String newPasswd2 = newPassword2.getText().toString();
                final String[] crtPasswd = new String[1];
                MD5 md5Hasher = new MD5(oldPasswd);
                final String oldPasswdHash = md5Hasher.getMD5();

                if (newPasswd.equals(newPasswd2)) {

                    boolean checkPass1 = newPasswd.matches("[a-zA-Z0-9]{6,20}");
                    boolean checkPass2 = newPasswd2.matches("[a-zA-Z0-9]{6,20}");
                    if (!(checkPass1 && checkPass2)) {
                        Toast.makeText(ChangePasswordActivity.this, "Passwords should have between 6 and 20 alfanumeric chars!", Toast.LENGTH_SHORT).show();
                        return;

                    }
                }
                else {
                    Toast.makeText(ChangePasswordActivity.this, "Passwords are not the same!", Toast.LENGTH_SHORT).show();
                    return;
                }

                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        try {
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                String username = ds.child("username").getValue(String.class);

                                if (getIntent().getStringExtra("USER_NAME").equals(username)) {
                                    crtPasswd[0] = ds.child("password").getValue(String.class);
                                    if(oldPasswdHash.equals(crtPasswd[0])){
                                        MD5 md5Hasher = new MD5(newPasswd);
                                        String newPasswdHash = md5Hasher.getMD5();
                                        ds.child("password").getRef().setValue(newPasswdHash);
                                        Intent i = new Intent(getBaseContext(), ProfileActivity.class);
                                        i.putExtra("USER_NAME", username);
                                        openProfileActivity(i);
                                    }
                                    else{
                                        Toast.makeText(ChangePasswordActivity.this, "Old Password does not match", Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                    //ds.child("nr_wins").getRef().setValue(++nrWins);
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
        });
    }
}