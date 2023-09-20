package com.example.justmark;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.justmark.Admin.AdminLoginActivity;
import com.example.justmark.Faculty.FacultyLoginActivity;
import com.example.justmark.Student.StudentLoginActivity;


public class ChoiceActivity extends AppCompatActivity {
    CardView teacher,stud,admin;
    SharedPreferences sh;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_choice);
        teacher=findViewById(R.id.f);
        stud=findViewById(R.id.s);
        admin=findViewById(R.id.admin);
        setTitle("Choose your Role");

        sh=getSharedPreferences("Choice", Context.MODE_PRIVATE);


        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePreferences("Teacher");

            }
        });
        stud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePreferences("Student");
            }
        });
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePreferences("Admin");
            }
        });

    }

    public void savePreferences(String s){
        if (s == "Admin") {
            sh.edit().putInt("Condition",1).apply();
            Intent i=new Intent(ChoiceActivity.this, AdminLoginActivity.class);
            startActivity(i);
            finish();
        }
        if (s == "Teacher") {
            sh.edit().putInt("Condition",2).apply();
            Intent i=new Intent(ChoiceActivity.this, FacultyLoginActivity.class);
            startActivity(i);
            finish();
        }
        if(s=="Student"){
            sh.edit().putInt("Condition",3).apply();
            Intent i=new Intent(ChoiceActivity.this, StudentLoginActivity.class);
            startActivity(i);
            finish();



        }

    }
}
