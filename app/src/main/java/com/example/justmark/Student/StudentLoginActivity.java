package com.example.justmark.Student ;




import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.justmark.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentLoginActivity extends AppCompatActivity {

    EditText loginUsername, loginPassword;
    Button loginButton;
    TextView signupRedirectText;TextView forgot;
    SharedPreferences sh1;SharedPreferences sh,sh2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        sh=getSharedPreferences("College", Context.MODE_PRIVATE);
        sh1=getSharedPreferences("StudentLogin",Context.MODE_PRIVATE);
        sh2=getSharedPreferences("StudentUser",Context.MODE_PRIVATE);
        loginUsername = findViewById(R.id.admin_login_username);
        loginPassword = findViewById(R.id.admin_login_password);
        loginButton = findViewById(R.id.admin_login);
        signupRedirectText = findViewById(R.id.signupRedirectText);
        forgot=findViewById(R.id.forgot);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateUsername() | !validatePassword()) {

                } else {
                    checkUser();
                }
            }
        });

        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(com.example.justmark.Student.StudentLoginActivity.this, StudentSignUpActivity.class);
                startActivity(intent);
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(com.example.justmark.Student.StudentLoginActivity.this, StudentChangePassword.class);
                startActivity(intent);
            }
        });

    }

    public Boolean validateUsername() {
        String val = loginUsername.getText().toString();
        if (val.isEmpty()) {
            loginUsername.setError("Username cannot be empty");
            return false;
        } else {
            loginUsername.setError(null);
            return true;
        }
    }

    public Boolean validatePassword(){
        String val = loginPassword.getText().toString();
        if (val.isEmpty()) {
            loginPassword.setError("Password cannot be empty");
            return false;
        } else {
            loginPassword.setError(null);
            return true;
        }
    }


    public void checkUser(){
        String userUsername = loginUsername.getText().toString().trim();
        String userPassword = loginPassword.getText().toString().trim();
        String college=sh.getString("CName",null);

        String dept=sh.getString("DName",null);
        String bat=sh.getString("BName",null);
        DatabaseReference reference = FirebaseDatabase.getInstance("https://justmark-38096-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("attendance-register");


        reference.child(college).child(dept).child(bat).child("Students").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.hasChild(userUsername)){


                    String passwordFromDB = snapshot.child(userUsername).child("pass").getValue(String.class);
                    String userFromDB = snapshot.child(userUsername).child("id").getValue(String.class);
                    if(userFromDB.equals(userUsername)) {
                        loginUsername.setError(null);
                        if (passwordFromDB.equals(userPassword)) {
                            loginUsername.setError(null);

                            String nameFromDB = snapshot.child("name").getValue(String.class);

                            Intent i = new Intent(com.example.justmark.Student.StudentLoginActivity.this, StudentHomeActivity.class);
                            sh1.edit().putInt("Status",1).apply();
                            sh2.edit().putString("user",userFromDB).apply();
                            startActivity(i);
                            finish();
                        } else {
                            loginPassword.setError("Invalid Credentials");
                            loginPassword.requestFocus();
                        }
                    }else {
                        loginUsername.setError("User does not exist");
                        loginUsername.requestFocus();
                    }
                } else {
                    loginUsername.setError("User does not exist");
                    loginUsername.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(com.example.justmark.Student.StudentLoginActivity.this, "Login error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}