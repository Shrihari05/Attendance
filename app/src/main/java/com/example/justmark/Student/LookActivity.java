package com.example.justmark.Student;




import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.justmark.Admin.Batch;
import com.example.justmark.Admin.MyAdapter;
import com.example.justmark.Admin.subject;
import com.example.justmark.Faculty.Attendance;
import com.example.justmark.Faculty.FacultyHomeActivity;
import com.example.justmark.R;
import com.example.justmark.StudentSubject;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class LookActivity extends AppCompatActivity {


    PieChart pieChart;
    SharedPreferences sh, sh2;
    ArrayList<Attendance> at;  ValueEventListener eventListener;
public int cnt,pre,ab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look);


        pieChart = findViewById(R.id.piechart);

        sh = getSharedPreferences("College", Context.MODE_PRIVATE);
        sh2 = getSharedPreferences("StudentUser", Context.MODE_PRIVATE);

        String college = sh.getString("CName", null);
        TextView p,a;     p=findViewById(R.id.present);
        a=findViewById(R.id.absent);
        String dept = sh.getString("DName", null);
        String user = sh2.getString("user", "");
        String bat = sh.getString("BName", null);
        Bundle bundle = getIntent().getExtras();
        String sub=bundle.getString("key1", "Default");
        DatabaseReference reference = FirebaseDatabase.getInstance("https://justmark-38096-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("attendance-register");
        Toolbar tb=findViewById(R.id.toolbar);
        tb.setTitle(sub);

        reference.child(college).child(dept).child(bat).child("Stusub").child(user).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                long pr = snapshot.child(sub).child("stupre").getValue(Long.class);

                p.setText(String.valueOf((int)pr));
                LookActivity.this.pre=(int)pr;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        reference.child(college).child(dept).child("Subjects").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

               int c= Integer.valueOf(String.valueOf(snapshot.child(sub).child("th").getValue(Long.class)));
             LookActivity.this.cnt=c;   int present= Math.round((((float)pre/c)*100));   int absent= Math.round((((float)(c-pre)/c)*100));          a.setText(String.valueOf(c-pre));
                TextView t=findViewById(R.id.total);
                TextView l=findViewById(R.id.percent);
                DecimalFormat  format=new DecimalFormat("#.00");

                l.setText(String.valueOf(format.format((((float)pre/c)*100))));
                t.setText(String.valueOf(c));
                pieChart.addPieSlice(
                        new PieModel(
                                "Present",
                                present,
                                Color.parseColor("#66BB6A")));
                pieChart.addPieSlice(
                        new PieModel(
                                "Absent",
                                absent,
                                Color.parseColor("#ff0000")));

                pieChart.startAnimation();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        at=new ArrayList<Attendance>();

        AtAdapter att= new AtAdapter(LookActivity.this,at,sub,college,dept,bat,user);
        RecyclerView rv=(RecyclerView) findViewById(R.id.rvc);
        rv.setAdapter(att);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(LookActivity.this));
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://justmark-38096-default-rtdb.asia-southeast1.firebasedatabase.app");

        DatabaseReference root =  database.getReference("attendance-register").child(college).child(dept).child(bat).child("Attendance").child(sub);
        eventListener=root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                at.clear();
                for(DataSnapshot itemSnapShot:snapshot.getChildren()){
                    Attendance ad=itemSnapShot.getValue(Attendance.class);
                    at.add(ad);
                    Collections.sort(at,new Comparator<Attendance>(){

                        @Override
                        public int compare(Attendance o1, Attendance o2) {
                            String pattern = "dd-MM-yyyy";
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                            Date d1 = null;
                            Date d2 = null;
                            try {
                                d1 = simpleDateFormat.parse(o1.getDat());
                                d2= simpleDateFormat.parse(o2.getDat());
                            } catch (ParseException e) {
                                throw new RuntimeException(e);

                            }


                            return d1.compareTo(d2);
                        }
                    });


                }
                att.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

}




