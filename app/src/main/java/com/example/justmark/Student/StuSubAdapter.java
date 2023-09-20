package com.example.justmark.Student;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justmark.R;
import com.example.justmark.StudentSubject;

import java.util.ArrayList;
import java.util.HashMap;

public class StuSubAdapter extends RecyclerView.Adapter<StuSubAdapter.MyViewHolder>{
    Context context;private OnClickListener onClickListener;
    ArrayList<StudentSubject> s=new ArrayList<>();
    public StuSubAdapter(){

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name; public TextView dept;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            name=itemView.findViewById(R.id.subName);
        }
    }
    public StuSubAdapter(Context context, ArrayList<StudentSubject> s){
        this.context=context;
        this.s=s;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.facsubitem,parent,false);
        return new MyViewHolder(v);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    public interface OnClickListener {
        void onClick(int position, StudentSubject model);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,int position) {

StudentSubject st=s.get(position);
        holder.name.setText(st.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickListener != null) {
                    onClickListener.onClick(holder.getAdapterPosition(),st);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return s.size();
    }

}