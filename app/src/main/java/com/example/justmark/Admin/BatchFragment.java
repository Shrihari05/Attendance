package com.example.justmark.Admin;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.justmark.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BatchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BatchFragment extends Fragment {


    ArrayList<Batch> b;


    ValueEventListener eventListener;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;




    public static BatchFragment newInstance(String param1, String param2) {
        BatchFragment fragment = new BatchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    Context context;
    SharedPreferences sh;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
  context= container.getContext();
        sh=getActivity().getSharedPreferences("College", Context.MODE_PRIVATE);String college=sh.getString("CName",null);
     View view= inflater.inflate(R.layout.fragment_batch, container, false);
        RecyclerView rv=(RecyclerView) view.findViewById(R.id.rv);
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://justmark-38096-default-rtdb.asia-southeast1.firebasedatabase.app");
        FloatingActionButton add=(FloatingActionButton)view.findViewById(R.id.add);String dept=sh.getString("DName",null);
         rv= rv.findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(context));


        DatabaseReference root =  database.getReference("attendance-register").child(college).child("Batches").child(dept);


add.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame, new AddBatchFragment()).addToBackStack(null).commit();
    }
});
        b=new ArrayList<Batch>();
        MyAdapter a= new MyAdapter(context,b);
        rv.setAdapter(a);

        eventListener=root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                b.clear();
                for(DataSnapshot itemSnapShot:snapshot.getChildren()){
                    Batch batch=itemSnapShot.getValue(Batch.class);
                    b.add(batch);



                }
                a.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }



}