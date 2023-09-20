package com.example.justmark.Student;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justmark.Admin.Batch;
import com.example.justmark.Admin.MyAdapter;
import com.example.justmark.Faculty.Attendance;
import com.example.justmark.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AtAdapter extends RecyclerView.Adapter<AtAdapter.MyViewHolder>{

Context context;
    ArrayList<Attendance> at=new ArrayList<>();String sub;
    private Long pr;String college,dept,bat,user;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView attend,dt;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            attend=itemView.findViewById(R.id.atvalue);
            dt=itemView.findViewById(R.id.atdate);
attend.setText("Attendance");
dt.setText("Date");

        }
    }
    public AtAdapter(Context context,ArrayList<Attendance> at,String sub,String college,String dept,String bat,String user){
        this.context=context;
        this.at=at;
        this.sub=sub;
        this.college=college;
        this.dept=dept;
        this.bat=bat;
        this.user=user;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.stuattenditem,parent,false);
        return new MyViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AtAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {


         FirebaseDatabase database = FirebaseDatabase.getInstance("https://justmark-38096-default-rtdb.asia-southeast1.firebasedatabase.app");

        DatabaseReference reference =  database.getReference("attendance-register");
        reference.child(college).child(dept).child(bat).child("Attendance").child(sub).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                AtAdapter.this.pr = snapshot.child(at.get(position).getDat()).child("Students").child(user).getValue(Long.class);
if(AtAdapter.this.pr==null)
    AtAdapter.this.pr=0L;
                holder.attend.setText(String.valueOf(pr)+"/"+at.get(position).getVal());
                holder.dt.setText(at.get(position).getDat());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });



    }

    @Override
    public int getItemCount() {
        return at.size();
    }

}