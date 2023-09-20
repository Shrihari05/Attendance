package com.example.justmark.Faculty;



import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justmark.Admin.subject;
import com.example.justmark.R;
import com.example.justmark.Student.AtAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FacAtAdapter extends RecyclerView.Adapter<com.example.justmark.Faculty.FacAtAdapter.MyViewHolder>{
    Context context; List<String[]> data = new ArrayList<>();int sn;
    ArrayList<String> s=new ArrayList<>();String college,dept,bat,user,sub,dat;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,sno,roll,val;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            name=itemView.findViewById(R.id.sname);
            roll=itemView.findViewById(R.id.sroll);
            sno=itemView.findViewById(R.id.sno);
            val=itemView.findViewById(R.id.sval);


        }
    }
    public FacAtAdapter(){}
    public List<String[]> datalist(){
        return data;
    }
    public FacAtAdapter(Context context,ArrayList<String> s,String sub,String college,String dept,String bat,String user,String dat){
        this.context=context;
        this.s=s;this.sn=0;
        this.sub=sub;
        this.college=college;this.dat=dat;
        this.dept=dept;
        this.bat=bat;
        this.user=user;
    }
    @NonNull
    @Override
    public com.example.justmark.Faculty.FacAtAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.facattenditem,parent,false);
        return new com.example.justmark.Faculty.FacAtAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.justmark.Faculty.FacAtAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://justmark-38096-default-rtdb.asia-southeast1.firebasedatabase.app");

        DatabaseReference reference =  database.getReference("attendance-register");
        reference.child(college).child(dept).child(bat).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {





                    holder.sno.setText(String.valueOf(++sn));
                    holder.roll.setText(s.get(position));
                    holder.val.setText(String.valueOf(snapshot.child("Attendance").child(sub).child(dat).child("Students").child(s.get(position)).getValue(Long.class)));
                    holder.name.setText( snapshot.child("Students").child(s.get(position)).child("name").getValue(String.class));



             // data.add(new String[]{String.valueOf(FacAtAdapter.this.sn), s.get(position),snapshot.child("Students").child(s.get(position)).child("name").getValue(String.class),String.valueOf(snapshot.child("Attendance").child(sub).child(dat).child("Students").child(s.get(position)).getValue(Long.class)) });





            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });



    }

    @Override
    public int getItemCount() {
        return s.size();
    }

}