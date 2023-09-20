package com.example.justmark.Admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justmark.R;

import java.util.ArrayList;

public class SubAdapter extends RecyclerView.Adapter<SubAdapter.MyViewHolder>{
    Context context;
    ArrayList<subject> s=new ArrayList<>();
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name; public TextView dept;TextView bat;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            name=itemView.findViewById(R.id.subName);
            dept=itemView.findViewById(R.id.subDept);
            bat=itemView.findViewById(R.id.subBat);


        }
    }
    public SubAdapter(Context context,ArrayList<subject> s){
        this.context=context;
        this.s=s;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.subitem,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SubAdapter.MyViewHolder holder, int position) {

        holder.name.setText(s.get(position).getName());
        holder.dept.setText(s.get(position).getFac());
        holder.bat.setText(s.get(position).getBatch());

    }

    @Override
    public int getItemCount() {
        return s.size();
    }

}