package com.example.justmark.Admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justmark.Admin.Batch;
import com.example.justmark.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
Context context;
ArrayList<Batch> b=new ArrayList<>();
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView bname;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            bname=itemView.findViewById(R.id.batchitem);


        }
    }
public MyAdapter(Context context,ArrayList<Batch> b){
this.context=context;
this.b=b;
}
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v= LayoutInflater.from(context).inflate(R.layout.batchitem,parent,false);
    return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {

holder.bname.setText(b.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return b.size();
    }

}