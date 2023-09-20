package com.example.justmark.Admin;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.justmark.R;
import com.example.justmark.StudentSubject;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.Subject;


public class AllocateStuSubFragment extends Fragment {



    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public AllocateStuSubFragment() {
        // Required empty public constructor
    }


    public static SubjectFragment newInstance(String param1, String param2) {
        SubjectFragment fragment = new SubjectFragment();
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
    Context context; EditText b;
    SharedPreferences sh;
String fac,college,dept;FirebaseDatabase database;
    ArrayList<String> s=new ArrayList<>();Spinner spino; String st="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context= container.getContext();
        sh=getActivity().getSharedPreferences("College", Context.MODE_PRIVATE);
       college=sh.getString("CName",null);
        View view= inflater.inflate(R.layout.fragment_allocate_stu_sub, container, false);
        spino = (Spinner)view.findViewById(R.id.coursesspinner);
        TextView t=getActivity().findViewById(R.id.title);
       b=(EditText) view.findViewById(R.id.batc);
        EditText e=view.findViewById(R.id.sturoll);
        t.setText("Subjects");
       database = FirebaseDatabase.getInstance("https://justmark-38096-default-rtdb.asia-southeast1.firebasedatabase.app");
              String dept=sh.getString("DName",null);



        DatabaseReference root =  database.getReference("attendance-register").child(college).child(dept).child("Subjects");



        s= new ArrayList<>();



        ValueEventListener eventListener = root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                s.clear();
                for (DataSnapshot itemSnapShot : snapshot.getChildren()) {
                    subject sub = itemSnapShot.getValue(subject.class);
                    s.add(sub.getName());
                    fac=sub.getFac();


                }
                ArrayAdapter ad = new ArrayAdapter(context, android.R.layout.simple_spinner_item, s);

                ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spino.setAdapter(ad);

                spino.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        st = parent.getItemAtPosition(position).toString();


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







        Button bat=view.findViewById(R.id.button);
        bat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDB(college,dept,st,fac,e.getText().toString(),b.getText().toString());
                      }
        });
        return view;
    }
    void addDB(String college,String dept,String sub,String fac,String roll,String ba){
        StudentSubject ss=new StudentSubject(st,0);
        HashMap<String,Object> m = new HashMap<>();
        m.put(roll,0);
        database.getReference("attendance-register").child(college).child(dept).child("Fac-Sub-Stu").child(fac).child(sub).updateChildren(m);

        database.getReference("attendance-register").child(college).child(dept).child(ba).child("Stusub").child(roll).child(sub).setValue(ss);

    }

     }