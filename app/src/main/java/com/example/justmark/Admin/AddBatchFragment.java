package com.example.justmark.Admin;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.justmark.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddBatchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddBatchFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public AddBatchFragment() {
        // Required empty public constructor
    }



    public static AddBatchFragment newInstance(String param1, String param2) {
        AddBatchFragment fragment = new AddBatchFragment();
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



    Context context;EditText bname;EditText year;EditText sem;EditText sec;Button addSec;Button addBat;
    SharedPreferences sh;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_add_batch, container, false);
bname=(EditText)view.findViewById(R.id.batch_name);
year=(EditText)view.findViewById(R.id.year); sh=getActivity().getSharedPreferences("College", Context.MODE_PRIVATE);
        ArrayList<String> l=new ArrayList<>();
sem=(EditText)view.findViewById(R.id.sem);
sec=(EditText)view.findViewById(R.id.section);
addSec=(Button)view.findViewById(R.id.addsection);
 addBat=(Button)view.findViewById(R.id.addbatch);
        context= container.getContext();
        addSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                l.add(String.valueOf(sec.getText()));
                sec.getText().clear();

            }
        });
        addBat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=bname.getText().toString();
                String y=year.getText().toString();
                String s=sem.getText().toString();
                String se=sec.getText().toString();

                if(name.isEmpty()){
                    bname.setError("Cannot be empty");
                    return;
                }
                if(y.isEmpty()){
                    year.setError("Cannot be Empty");
                    return;
                }
                if(s.isEmpty()){
                    sem.setError("Cannot be Empty");
                    return;
                }
                if(se.isEmpty()){
                    sec.setError("Cannot be Empty");
                    return;
                }
                addBatchToDB(name,y,s,se,l);
            }
        });
        return view;
    }
    private void addBatchToDB(String name,String y,String s,String se,ArrayList<String> l){
        Map<String,String> m=new HashMap<>();

        List<String> l1=new ArrayList<>();
        List<String> l2=new ArrayList<>();
        m.put("Batch",name);
        m.put("Attendance","Attendance");

        m.put("Students","Students");

        m.put("Fac-Sub","Fac-Sub");
        m.put("Stusub","Stusub");
       String dept=sh.getString("DName",null);


        String college=sh.getString("CName",null);
Batch batch=new Batch(name,y,s,dept,l);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://justmark-38096-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference root = database.getReference("attendance-register");

        root.child(college).child(dept).child(name).setValue(m);

        root.child(college).child("Batches").child(dept).child(name).setValue(batch).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(context,"Added",Toast.LENGTH_SHORT);
                bname.getText().clear();
                sem.getText().clear();
                sec.getText().clear();
                year.getText().clear();
            }
        });

        for(String i:l1){
            Map<String, Object> childUpdates = new HashMap<>();

            childUpdates.put(i,m);

            root.child(college).child(dept).updateChildren(childUpdates);


        }



    }

    }
