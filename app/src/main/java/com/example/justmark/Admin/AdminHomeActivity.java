package com.example.justmark.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.justmark.Faculty.FacultyLoginActivity;
import com.example.justmark.R;
import com.example.justmark.SplashActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class AdminHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener   {
DrawerLayout drawerLayout;MenuItem previous=null;
ActionBarDrawerToggle actionBarDrawerToggle;  SharedPreferences sh1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Home");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new HomeFragment()).commit();

     sh1 =getSharedPreferences("Login",Context.MODE_PRIVATE);
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ImageView l=findViewById(R.id.logout);
        l.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminHomeActivity.this);

                // Set the message show for the Alert time
                builder.setMessage("Do you want to Logout ?");

                // Set Alert Title


                // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
                builder.setCancelable(false);

                // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
                builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                    sh1.edit().putInt("Status",0).apply();
                    sh1.edit().putString("User","").apply();
                    Intent i = new Intent(AdminHomeActivity.this,
                            AdminLoginActivity.class);
                    startActivity(i);
                    finish();
                    finish();
                });

                // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
                builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                    // If user click no then dialog box is canceled.
                    dialog.cancel();
                });

                // Create the Alert dialog
                AlertDialog alertDialog = builder.create();
                // Show the Alert Dialog box
                alertDialog.show();



            }
        });
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) AdminHomeActivity.this);
        navigationView.setCheckedItem(R.id.nav_home);


        // to make the Navigation drawer icon always appear on the action bar



    }
    @Override
    public void onBackPressed() {
        Fragment f=getSupportFragmentManager().findFragmentById(R.id.frame); DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getSupportFragmentManager().popBackStack();
        }
        if (drawer.isDrawerOpen(                                                                                          GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {




        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.


        int id = item.getItemId();



        if (id == R.id.nav_home) {

            getSupportFragmentManager().beginTransaction().replace(R.id.frame, new HomeFragment()).commit();
            getSupportActionBar().setTitle("Home");
            drawerLayout.closeDrawers();
        } else if (id == R.id.nav_log) {
            AlertDialog.Builder builder = new AlertDialog.Builder(AdminHomeActivity.this);

            // Set the message show for the Alert time
            builder.setMessage("Do you want to Logout ?");



            // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
            builder.setCancelable(false);

            // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
            builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                sh1.edit().putInt("Status",0).apply(); sh1.edit().putString("User","").apply();
                Intent i = new Intent(AdminHomeActivity.this,
                        AdminLoginActivity.class);
                startActivity(i);

                finish();
            });

            // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
            builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                // If user click no then dialog box is canceled.
                dialog.cancel();
            });

            // Create the Alert dialog
            AlertDialog alertDialog = builder.create();
            // Show the Alert Dialog box
            alertDialog.show();

        } else if (id == R.id.nav_pass) {
            Intent i=new Intent(AdminHomeActivity.this, AdminChangePassword.class);
            startActivity(i);

        } else if (id == R.id.nav_batch) {

            getSupportFragmentManager().beginTransaction().replace(R.id.frame, new BatchFragment()).addToBackStack(null).commit();
            getSupportActionBar().setTitle("Batch");
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            drawerLayout.closeDrawers();

        } else if (id == R.id.nav_dept) {

        } else if (id == R.id.nav_sub) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, new AllocateStuSubFragment()).addToBackStack(null).commit();
            getSupportActionBar().setTitle("Batch");
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            drawerLayout.closeDrawers();
        }
        else if (id == R.id.nav_about) {

        }

        return true;
    }
}