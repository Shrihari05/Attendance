package com.example.justmark.Faculty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justmark.R;
import com.example.justmark.Student.StuSubAdapter;

import java.util.ArrayList;

public class FacSubAdapter extends RecyclerView.Adapter<FacSubAdapter.MyViewHolder>{
    Context context;
    ArrayList<String> s=new ArrayList<>();
    private OnClickListener onClickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name; public TextView dept;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            name=itemView.findViewById(R.id.subName);



        }
    }
    public FacSubAdapter(Context context, ArrayList<String> s){
        this.context=context;
        this.s=s;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.facsubitem,parent,false);
        return new MyViewHolder(v);
    }
    public void setOnClickListener(FacSubAdapter.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    public interface OnClickListener {
        void onClick(int position, String model);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String st=s.get(position);
        holder.name.setText(s.get(position));

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