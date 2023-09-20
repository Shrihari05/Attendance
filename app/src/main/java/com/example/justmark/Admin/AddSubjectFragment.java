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


public class AddSubjectFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public AddSubjectFragment() {
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



    Context context;EditText name;EditText fac;EditText cnt;EditText dept;EditText bat;Button addSub;
    SharedPreferences sh;String college;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_add_subject, container, false);
        name=(EditText)view.findViewById(R.id.sub_name);
        fac=(EditText)view.findViewById(R.id.subfac);
        sh=getActivity().getSharedPreferences("College", Context.MODE_PRIVATE);
        ArrayList<String> stu=new ArrayList<>();
        cnt=(EditText)view.findViewById(R.id.cnt);
        bat=(EditText)view.findViewById(R.id.subbat);
        dept=(EditText)view.findViewById(R.id.subdep);
        addSub=(Button)view.findViewById(R.id.addsub);

        context= container.getContext();

        addSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String n=name.getText().toString();
                String f=fac.getText().toString();
                String b=bat.getText().toString();
                String d=dept.getText().toString();
                String c=cnt.getText().toString();college=sh.getString("CName",null);
int th=0;
                if(n.isEmpty()){
                    name.setError("Cannot be empty");
                    return;
                }
                if(b.isEmpty()){
                    bat.setError("Cannot be Empty");
                    return;
                }
                if(c.isEmpty()){
                    cnt.setError("Cannot be Empty");
                    return;
                }
                if(d.isEmpty()){
                    dept.setError("Cannot be Empty");
                    return;
                }
                if(f.isEmpty()){
                    fac.setError("Cannot be Empty");
                    return;
                }
                addSubjectToDB(n, stu, d, b, college, f, c,th);
            }
        });
        return view;
    }
    private void addSubjectToDB(String n, ArrayList<String> stu, String d, String b, String college, String f, String c,int th){








       subject s=new subject(n,d,b,college,f,c,th);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://justmark-38096-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference root =  database.getReference("attendance-register");
Map<String,Object> m=new HashMap();
m.put(n,n);
root.child(college).child(d).child(b).child("Fac-Sub").child(f).updateChildren(m);
        root.child(college).child(d).child("Faculty").child(f).child("Subjects").setValue(m);
        root.child(college).child(d).child("Subjects").child(n).setValue(s).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(context,"Added",Toast.LENGTH_SHORT);
                name.getText().clear();
                fac.getText().clear();
                dept.getText().clear();
                bat.getText().clear();
                cnt.getText().clear();
            }
        });





    }

}
