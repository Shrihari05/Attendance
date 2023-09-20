package com.example.justmark.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.justmark.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminChangePassword extends AppCompatActivity   {
EditText user,pass;
Button set;SharedPreferences sh,sh1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        set=findViewById(R.id.set);

        pass=findViewById(R.id.admin_password);
        sh=getSharedPreferences("College", Context.MODE_PRIVATE);
        sh1=getSharedPreferences("Login",Context.MODE_PRIVATE);
        String college=sh.getString("CName",null);

        EditText et=findViewById(R.id.user);
             String us=et.getText().toString();
        String dept=sh.getString("DName",null);
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://justmark-38096-default-rtdb.asia-southeast1.firebasedatabase.app/");

        DatabaseReference reference = database.getReference("attendance-register");

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(college).child(dept).child("Admin").child("password").setValue(pass.getText().toString());
            }
        });



    }

}