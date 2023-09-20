package com.example.justmark.Student;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;


import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.justmark.Faculty.Attendance;
import com.example.justmark.Faculty.AttendanceActivity;
import com.example.justmark.Faculty.FacultyHomeActivity;
import com.example.justmark.Student.StuSubAdapter;

import com.example.justmark.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MarkActivity extends AppCompatActivity {
    private static final int PERMISSION_ID = 44;
    ValueEventListener eventListener;
    SharedPreferences sh;

int con=1;int f=1;
String dat;
  int val=0;String reslat,reslong;EditText d;int pre=0;int stupre=0;int th=0;
    Button btnscan,calen; Bitmap mBitmap;int resourceId;

TextView roll,name,sub;
   FusedLocationProviderClient mFusedLocationClient;
    LocationManager locationManager;
    String latitude, longitude;String n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark);



        //Location

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        Toolbar tool = findViewById(R.id.toolbar);
        setSupportActionBar(tool);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tool.setNavigationOnClickListener(view -> onBackPressed());

        name = findViewById(R.id.display_name);
        sub = findViewById(R.id.display_sub);
        roll = findViewById(R.id.display_roll);
calen=findViewById(R.id.calen);d=findViewById(R.id.date);
        String user = getSharedPreferences("StudentUser", Context.MODE_PRIVATE).getString("user", " ");
        String bat= getSharedPreferences("College", Context.MODE_PRIVATE).getString("BName", " ");
        String college= getSharedPreferences("College", Context.MODE_PRIVATE).getString("CName", " ");
        String dept= getSharedPreferences("College", Context.MODE_PRIVATE).getString("DName", " ");

        roll.setText(user);

        Bundle bundle = getIntent().getExtras();
        sub.setText(bundle.getString("key1", "Default"));
        String sb=bundle.getString("key1", "Default");
        n=roll.getText().toString();
        ImageView imageCode = findViewById(R.id.qrCode);


        FirebaseDatabase database = FirebaseDatabase.getInstance("https://justmark-38096-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference root = database.getReference("attendance-register");
        root.child(college).child(dept).child(bat).child("Students").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                name.setText(snapshot.child(user).child("name").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        root.child(college).child(dept).child(bat).child("Stusub").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

               stupre=Integer.valueOf(snapshot.child(user).child(sb).child("stupre").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






//QR Generator
        btnscan= findViewById(R.id.scan);

        calen.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            qrGen();
            // on below line we are getting
            // our day, month and year.
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // on below line we are creating a variable for date picker dialog.
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    // on below line we are passing context.
                    MarkActivity.this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            // on below line we are setting date to our edit text.
                            d.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            dat=dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;

                        }
                    },
                    // on below line we are passing year,
                    // month and day for selected date in our date picker.
                    year, month, day);
            // at last we are calling show to
            // display our date picker dialog.
            datePickerDialog.show();
        });



      //QRScanner

        btnscan=findViewById(R.id.scan);//Marking
        btnscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Bitmap screenshotBitmap = BitmapFactory.decodeResource(getResources(),R.id.imageCode);
                String qrCodeData = decodeQRCodeFromBitmap(mBitmap);//Scan data
                if (TextUtils.isEmpty(dat)) {
                    d.setError("Email is required");
                    d.requestFocus();
                    return;
                }
                if (qrCodeData != null) {
                    // QR code data successfully decoded

                    root.child(college).child(dept).child(bat).child("Attendance").child(sb).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            con=Integer.valueOf(snapshot.child(d.getText().toString()).child("con").getValue().toString());
pre=Integer.valueOf(snapshot.child(d.getText().toString()).child("pre").getValue().toString());

                            val= Integer.valueOf(snapshot.child(d.getText().toString()).child("val").getValue().toString());
                            reslat= snapshot.child(d.getText().toString()).child("lat").getValue().toString().substring(0,7);
                            reslong= snapshot.child(d.getText().toString()).child("longi").getValue().toString().substring(0,7);

                           Toast.makeText(MarkActivity.this, "QR Code: " + qrCodeData, Toast.LENGTH_SHORT).show();
if(con==1) {
    if (qrCodeData.equals(user + " " + reslat + " " + reslong)) {
               if(f==1) {
                   f=0;

                   root.child(college).child(dept).child(bat).child("Attendance").child(sb).child(dat).child("Students").child(user).setValue(val);
                   root.child(college).child(dept).child(bat).child("Stusub").child(user).child(sb).child("stupre").setValue(stupre+val);
                   root.child(college).child(dept).child(bat).child("Attendance").child(sb).child(dat).child("pre").setValue(pre + 1);

        }} else {
        Toast.makeText(MarkActivity.this, "Attendance not marked since you are not in the class", Toast.LENGTH_SHORT).show();

    }
}
else if(con==0){
    Toast.makeText(MarkActivity.this, "Sorry!Attendance has been ended. Contact Faculty", Toast.LENGTH_SHORT).show();

}
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                                      // Toast.makeText(MarkActivity.this, "QR Code: " + qrCodeData, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MarkActivity.this, "No QR Code found", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }
   //QRSCANNER
    private String decodeQRCodeFromBitmap(Bitmap bitmap) {
        // Convert bitmap to luminance source
        LuminanceSource source = new RGBLuminanceSource(bitmap.getWidth(), bitmap.getHeight(), getRGBPixels(bitmap));

        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));
        MultiFormatReader reader = new MultiFormatReader();

        try {
            Result result = reader.decode(binaryBitmap);
            return result.getText();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private int[] getRGBPixels(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        return pixels;
    }
//Location
    @SuppressLint("MissingPermission")
    private void getLastLocation() {
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

                            latitude= String.valueOf(location.getLatitude()).substring(0,7);
                            longitude= String.valueOf(location.getLongitude()).substring(0,7);
                        }
                    }
                });
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
    }
    private void qrGen(){
        n=roll.getText().toString();
        ImageView imageCode = findViewById(R.id.qrCode);
        String myText = n+" "+ latitude +" "+ longitude;
        MultiFormatWriter mWriter = new MultiFormatWriter();
        try {
            BitMatrix mMatrix = mWriter.encode(myText, BarcodeFormat.QR_CODE, 500, 500);
            BarcodeEncoder mEncoder = new BarcodeEncoder();
            mBitmap = mEncoder.createBitmap(mMatrix);
            imageCode.setImageBitmap(mBitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("MissingPermission")

    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new com.google.android.gms.location.LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
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


            latitude= String.valueOf(mLastLocation.getLatitude()).substring(0,7);
            longitude= String.valueOf(mLastLocation.getLongitude()).substring(0,7);
        }
    };

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

          }


    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }


    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        getLastLocation();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
            qrGen();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        d.getText().clear();
    }
}