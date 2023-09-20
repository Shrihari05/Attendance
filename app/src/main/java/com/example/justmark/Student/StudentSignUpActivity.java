package com.example.justmark.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
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

import java.util.HashMap;
import java.util.Map;

public class StudentSignUpActivity extends AppCompatActivity {

    EditText signupName, college, signupEmail, signupPassword,signUpUser,bat,year,sem,sec;
    TextView loginRedirectText;
    Button signupButton;EditText dep;
    FirebaseDatabase database;
    DatabaseReference reference;

    private SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_sign_up);
        setTitle("Admin Register");
        dep=findViewById(R.id.dept);

        signupName = findViewById(R.id.admin_name);


bat=findViewById(R.id.bat);
sec=findViewById(R.id.sec);
sem=findViewById(R.id.sem);
year=findViewById(R.id.year);

        signupEmail = findViewById(R.id.admin_mail);
        college = findViewById(R.id.college);


        signUpUser=findViewById(R.id.admin_username);
        signupPassword = findViewById(R.id.admin_password);
        loginRedirectText = findViewById(R.id.loginRedirectText);
        signupButton = findViewById(R.id.admin_register);
        sh=getSharedPreferences("College", Context.MODE_PRIVATE);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseDatabase database = FirebaseDatabase.getInstance("https://justmark-38096-default-rtdb.asia-southeast1.firebasedatabase.app/");

                reference = database.getReference("attendance-register");
String b,y,s,sm;
                String name = signupName.getText().toString();
                String email = signupEmail.getText().toString();
                String user=signUpUser.getText().toString();
                String colleg = college.getText().toString();
                String password = signupPassword.getText().toString();
                String dept=dep.getText().toString();
                b=bat.getText().toString();
                y=year.getText().toString();
                s=sec.getText().toString();
                sm=sem.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    signupEmail.setError("Email is required");
                    signupEmail.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(name)) {
                    signupName.setError("Name is required");
                    signupName.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(colleg)) {
                    college.setError("College name is required");
                    college.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(dept)) {
                    dep.setError("Department name is required");
                    dep.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(user)) {
                    signUpUser.setError("User name is required");
                    signUpUser.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    signupPassword.setError("Password is required");
                    signupPassword.requestFocus();
                    return;
                }
                if (password.length()<6) {
                    signupPassword.setError("Password must be equal to or more than 6 characters");
                    signupPassword.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()){
                    signupEmail.setError("Invalid Email");
                    signupEmail.requestFocus();
                    return;
                }

                Helper helperClass = new Helper(name,user,email,colleg,password,dept,b,y,sm,s);

                reference.child(colleg).child(dept).child(b).child("Students").child(user).setValue(helperClass);
                Map<String,Object> m=new HashMap<>();
                m.put(user,user);
                reference.child(colleg).child(dept).child("Students").child(b).child(s).updateChildren(m);

                sh.edit().putString("CName",colleg).apply();
                sh.edit().putString("DName",dept).apply();
                sh.edit().putString("BName",b).apply();
                Toast.makeText(StudentSignUpActivity.this, "Account has been created successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(StudentSignUpActivity.this, StudentLoginActivity.class);
                startActivity(intent);







            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(StudentSignUpActivity.this, StudentLoginActivity.class);
                startActivity(intent);
            }
        });
    }
}