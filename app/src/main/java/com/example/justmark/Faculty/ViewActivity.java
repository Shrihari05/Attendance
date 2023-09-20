package com.example.justmark.Faculty;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.aspose.cells.CellsHelper;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.example.justmark.Admin.Batch;
import com.example.justmark.CSVWriter;
import com.example.justmark.DownloadsLocationHelper;
import com.example.justmark.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;


import java.text.DecimalFormat;
import java.util.ArrayList;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class ViewActivity extends AppCompatActivity {
    PieChart pieChart;
    Button ex;
    SharedPreferences sh, sh2;    FacAtAdapter faa=new FacAtAdapter();FacAtAdapter fab=new FacAtAdapter();
DatabaseReference reference;ArrayList<String> facpre=new ArrayList<String>();ArrayList<String> facab=new ArrayList<String>();
String college,dept,sub,dat,name;
    int cnt,ab;
    TextView p,a;Context context;
    RecyclerView rvp,rva;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
ex=findViewById(R.id.export);

        pieChart = findViewById(R.id.piechart);
        rva= findViewById(R.id.rva);

        rvp= findViewById(R.id.rvp);


        sh = getSharedPreferences("College", Context.MODE_PRIVATE);
        sh2=getSharedPreferences("FacultyUser",Context.MODE_PRIVATE);

        college = sh.getString("CName", null);
           p=findViewById(R.id.present);
        a=findViewById(R.id.absent);
        dept = sh.getString("DName", null);
        String user = sh2.getString("user", "");
        DownloadsLocationHelper h=new DownloadsLocationHelper();
        Bundle bundle = getIntent().getExtras();
        sub=bundle.getString("Sub", "Default");
        dat=bundle.getString("Date", "Default");
        reference = FirebaseDatabase.getInstance("https://justmark-38096-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("attendance-register");
        Toolbar tb=findViewById(R.id.toolbar);
        tb.setTitle(String.valueOf(h.getDownloadsDirectory()));

ex.setOnClickListener(new View.OnClickListener() {
    HashMap<String,Object> hm=new HashMap<>();
    @Override
    public void onClick(View v) {

        List<String[]> data=new ArrayList<>();

 FirebaseDatabase database = FirebaseDatabase.getInstance("https://justmark-38096-default-rtdb.asia-southeast1.firebasedatabase.app");



        DatabaseReference reference =  database.getReference("attendance-register");
        reference.child(college).child(dept).child("IT2020").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                data.add(new String[]{"Sno","Roll Number","Name","Attendance (Hours="+snapshot.child("Attendance").child(sub).child(dat).child("val").getValue(String.class)+")"});
                hm=(HashMap<String, Object>) snapshot.child("Attendance").child(sub).child(dat).child("Students").getValue();int i=1;
                for(Map.Entry m:hm.entrySet()){

                    data.add(new String[]{String.valueOf(i), (String) m.getKey(),snapshot.child("Students").child(String.valueOf(m.getKey())).child("name").getValue(String.class),String.valueOf((Long)m.getValue())});i++;
                }

                CSVWriter.writeCSV(ViewActivity.this,data,"new9.csv");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


CSVWriter.writeCSV(ViewActivity.this,data,"new.csv");
    }
});

        reference.child(college).child(dept).child("Subjects").child(sub).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String b= snapshot.child("batch").getValue(String.class);


               bc(b,dat,user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });






    }
void bc(String bat,String dat,String user){

        Collections.sort(facab,new Comparator<String>(){

            @Override
            public int compare(String o1, String o2) {
                return Integer.valueOf(o1).compareTo(Integer.valueOf(o2));
            }
        });
    faa= new FacAtAdapter(ViewActivity.this,facpre,sub,college,dept,bat,user,dat);
    fab= new FacAtAdapter(ViewActivity.this,facab,sub,college,dept,bat,user,dat);





    DatabaseReference root =   FirebaseDatabase.getInstance("https://justmark-38096-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("attendance-register").child(college).child(dept).child(bat).child("Attendance").child(sub).child(dat).child("Students");
    root.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            facab.clear();facpre.clear();
            for(DataSnapshot itemSnapShot:snapshot.getChildren()){
if((Long)itemSnapShot.getValue()==0L) {
    rva.setAdapter(fab);
    rva.setHasFixedSize(true);
    rva.setLayoutManager(new LinearLayoutManager(ViewActivity.this));
    facab.add((String) itemSnapShot.getKey());
}else{
    rvp.setAdapter(faa);
    rvp.setHasFixedSize(true);
    rvp.setLayoutManager(new LinearLayoutManager(ViewActivity.this));
    facpre.add((String) itemSnapShot.getKey());
}




            }
            faa.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });

    reference.child(college).child(dept).child(bat).child("Attendance").child(sub).addListenerForSingleValueEvent(new ValueEventListener() {

        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {

            long pr = snapshot.child(dat).child("pre").getValue(Long.class);

            p.setText(String.valueOf((int)pr));
            db(pr,sub);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
        }
    });
}
    void db(long p,String sub){
        reference.child(college).child(dept).child("Subjects").addValueEventListener(new ValueEventListener() {
int pre= (int) p;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int c= Integer.valueOf(Objects.requireNonNull(snapshot.child(sub).child("cnt").getValue(String.class)));
                ViewActivity.this.cnt=c;   int present= Math.round((((float)pre/c)*100));   int absent= Math.round((((float)(c- pre)/c)*100));          a.setText(String.valueOf(c- pre));
                TextView t=findViewById(R.id.total);
                TextView l=findViewById(R.id.percent);
                DecimalFormat format=new DecimalFormat("###.00");

                l.setText(String.valueOf(format.format(((float)pre/c)*100)));
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

    }

}