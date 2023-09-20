package com.example.justmark.Faculty;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;



import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.justmark.Admin.subject;
import com.example.justmark.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class AttendanceActivity extends AppCompatActivity {

    // on below line we are creating variables.
    private EditText dateEdt; FusedLocationProviderClient mFusedLocationClient;
    private static final int PERMISSION_ID = 44;String val;
    String bat;
    String dat;
    SharedPreferences sh3;

ArrayList<HashMap<String,Object>> stu=new ArrayList<HashMap<String,Object>>();HashMap<String, Object> m=new HashMap<String, Object>();
    private static final int REQUEST_LOCATION = 1;LocationManager locationManager;
    String latitude, longitude;
int con=1;    DatabaseReference reference;int th=0;
    SharedPreferences sh,sh2;Button mark;EditText vl,b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://justmark-38096-default-rtdb.asia-southeast1.firebasedatabase.app/");
b=findViewById(R.id.bh);
      vl= findViewById(R.id.val);

        Bundle bundle = getIntent().getExtras();

// getting the string back
       String sub=bundle.getString("key1", "Default");
        reference = database.getReference("attendance-register");
        sh=getSharedPreferences("College", Context.MODE_PRIVATE);
        sh3=getSharedPreferences("Attendance", Context.MODE_PRIVATE);
        sh2=getSharedPreferences("FacultyUser",Context.MODE_PRIVATE);
        String college=sh.getString("CName",null);

        String dept=sh.getString("DName",null);
        String user=sh2.getString("user",null);
        // on below line we are initializing our variables.
        dateEdt = findViewById(R.id.date);
        Button button=findViewById(R.id.calen);
       mark=findViewById(R.id.attend);
        reference.child(college).child(dept).child("Fac-Sub-Stu").child("ramIT").child("science").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot itemSnapShot : snapshot.getChildren()) {
                    m.put(itemSnapShot.getKey(),itemSnapShot.getValue());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference.child(college).child(dept).child("Subjects").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                th=Integer.valueOf(snapshot.child(sub).child("th").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // on below line we are adding click listener
        // for our pick date button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting
                // the instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting
                // our day, month and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        AttendanceActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our edit text.
                                dateEdt.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);


                            }
                        },
                        // on below line we are passing year,
                        // month and day for selected date in our date picker.
                        year, month, day);
                // at last we are calling show to
                // display our date picker dialog.
                datePickerDialog.show();
            }
        });
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(AttendanceActivity.this);

        // method to get the location
       String l= getLastLocation();
        mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference = FirebaseDatabase.getInstance("https://justmark-38096-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("attendance-register");
                DatabaseReference root = database.getReference("attendance-register");

                val = vl.getText().toString();
                bat = b.getText().toString();
                dat = dateEdt.getText().toString();
                Attendance at = new Attendance(bat, dat, latitude, longitude, val, 0, con);
                if(mark.getText().toString().equals("Start")) {
                    mark.setText("Stop");
                    at.setCon(1);
                    con=1;
                    sh3.edit().putString("Date",dat).apply();
                    sh3.edit().putInt("Con",con).apply();
                    sh3.edit().putString("Batch",bat).apply();
                    sh3.edit().putString("Value",val).apply();
                    root.child(college).child(dept).child(bat).child("Attendance").child(sub).child(dat).child("Students").setValue(stu);
                    root.child(college).child(dept).child("Subjects").child(sub).child("th").setValue(th+Integer.valueOf(val));

                    root.child(college).child(dept).child(bat).child("Attendance").child(sub).child(dat).setValue(at);
                    root.child(college).child(dept).child(bat).child("Attendance").child(sub).child(dat).child("Students").updateChildren(m);
                    Toast.makeText(AttendanceActivity.this,"Attendance has been started", Toast.LENGTH_SHORT);
                }else if(mark.getText().toString().equals("Stop")) {
                    mark.setText("Start"); at.setCon(0);
                    vl.getText().clear();
                    b.getText().clear();
                    dateEdt.getText().clear();
                    con=0;
                    sh3.edit().putInt("Con",con).apply();
                    sh3.edit().putString("Date","").apply();
                    sh3.edit().putString("Batch","").apply();
                    sh3.edit().putString("Value","").apply();
                    root.child(college).child(dept).child(bat).child("Attendance").child(sub).child(dat).setValue(at);
                    Toast.makeText(AttendanceActivity.this, "Attendance has been taken", Toast.LENGTH_SHORT);


                }
            }
        });
    }

public String loc="";
    @SuppressLint("MissingPermission")
    private String getLastLocation() {
        // check if permissions are given

        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                // getting last
                // location from
                // FusedLocationClient
                // object
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {

                            loc=location.getLatitude()+" "+location.getLongitude();
latitude=String.valueOf(location.getLatitude());
longitude=String.valueOf(location.getLongitude());


                            Toast.makeText(AttendanceActivity.this,loc,Toast.LENGTH_SHORT);
                        }
                    }
                });return loc;
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }
        return  loc;
    }



    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new com.google.android.gms.location.LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();

        }
    };

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // If everything is alright then
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
               String h= getLastLocation();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();



    }



    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            String j=getLastLocation();
        }
if(sh3.getInt("Con",1)==1){
    vl.setText(sh3.getString("Value",""));
    b.setText(sh3.getString("Batch",""));
    dateEdt.setText(sh3.getString("Date",""));
    mark.setText("Stop");
}


    }
}
