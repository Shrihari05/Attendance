package com.example.justmark;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.justmark.Admin.AdminHomeActivity;
import com.example.justmark.Admin.AdminLoginActivity;
import com.example.justmark.Faculty.FacultyHomeActivity;
import com.example.justmark.Faculty.FacultyLoginActivity;
import com.example.justmark.Student.StudentHomeActivity;
import com.example.justmark.Student.StudentLoginActivity;

public class SplashActivity extends AppCompatActivity {
    Animation topAnim,bottomAnim;
    ImageView image;
    TextView title;
    SharedPreferences sh;
    int c;
    private final static int SPLASH_SCREEN_TIME_OUT=1525;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sh=getSharedPreferences("Choice", Context.MODE_PRIVATE);
        c=sh.getInt("Condition",10);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //This method is used so that your splash activity
        //can cover the entire screen.

        setContentView(R.layout.activity_splash);
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        image= findViewById(R.id.imageView);
        title=findViewById(R.id.textView2);
        image.setAnimation(topAnim);
        title.setAnimation(bottomAnim);
        SharedPreferences sh1=getSharedPreferences("Login",Context.MODE_PRIVATE);int d=sh1.getInt("Status",0);
        //this will bind your MainActivity.class file with activity_main.
        SharedPreferences sh2=getSharedPreferences("FacultyLogin",Context.MODE_PRIVATE);int e=sh2.getInt("Status",0);
        SharedPreferences sh3=getSharedPreferences("StudentLogin",Context.MODE_PRIVATE);int s=sh3.getInt("Status",0);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(c==1){
                    if(d!=1){
                        Intent i = new Intent(SplashActivity.this,
                                AdminLoginActivity.class);
                        startActivity(i);
                        finish();}
                    else{
                        Intent i = new Intent(SplashActivity.this,
                                AdminHomeActivity.class);
                        startActivity(i);
                        finish();
                    }
                }
                else if(c==2){
                    if(e!=1){
                        Intent i = new Intent(SplashActivity.this,
                                FacultyLoginActivity.class);
                        startActivity(i);
                        finish();}
                    else{
                        Intent i = new Intent(SplashActivity.this,
                                FacultyHomeActivity.class);
                        startActivity(i);
                        finish();
                    }
                }
                else if(c==3){
                    if(s!=1){
                        Intent i = new Intent(SplashActivity.this,
                                StudentLoginActivity.class);
                        startActivity(i);
                        finish();}
                    else{
                        Intent i = new Intent(SplashActivity.this,
                                StudentHomeActivity.class);
                        startActivity(i);
                        finish();
                    }
                }

                else {
                    Intent i = new Intent(SplashActivity.this,
                            ChoiceActivity.class);
                    startActivity(i);
                    finish();
                }








            }
        }, SPLASH_SCREEN_TIME_OUT);
    }


}
































