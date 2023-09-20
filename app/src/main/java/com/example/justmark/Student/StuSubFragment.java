package com.example.justmark.Student;

import static com.example.justmark.Student.StudentHomeActivity.frag;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.justmark.R;
import com.example.justmark.StudentSubject;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class StuSubFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StuSubFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FacSubjectFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StuSubFragment newInstance(String param1, String param2) {
        StuSubFragment fragment = new StuSubFragment();
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
    RecyclerView rv;
    ArrayList<StudentSubject> sj;SharedPreferences sh2;public static final String NEXT_SCREEN = "details_screen";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context= container.getContext();
        sh2=getActivity().getSharedPreferences("StudentUser",Context.MODE_PRIVATE);
        sh=getActivity().getSharedPreferences("College", Context.MODE_PRIVATE);
        String college=sh.getString("CName",null);
        View view= inflater.inflate(R.layout.fragment_fac_subject, container, false);
        rv=(RecyclerView) view.findViewById(R.id.rv);
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://justmark-38096-default-rtdb.asia-southeast1.firebasedatabase.app");

        String dept=sh.getString("DName",null);
        String bat=sh.getString("BName",null);
        rv= rv.findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(context));
        String user=sh2.getString("user",null);

        DatabaseReference root =  database.getReference("attendance-register").child(college).child(dept).child(bat).child("Stusub").child(user);



        sj= new ArrayList<>();
       StuSubAdapter a= new StuSubAdapter(context,sj);
        rv.setAdapter(a);

        ValueEventListener eventListener = root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sj.clear();
                for (DataSnapshot itemSnapShot : snapshot.getChildren()) {
                   StudentSubject sub= itemSnapShot.getValue(StudentSubject.class);
                    sj.add(sub);


                }
                a.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        a.setOnClickListener(new StuSubAdapter.OnClickListener() {
            @Override
            public void onClick(int position,StudentSubject model) {
                if(frag==0) {
                    Intent intent = new Intent(getActivity(), MarkActivity.class);
                    // Passing the data to the
                    // EmployeeDetails Activity
                    Bundle bundle = new Bundle();

                    bundle.putString("key1", model.getName());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else if(frag==1) {
                    Intent intent = new Intent(getActivity(), LookActivity.class);
                    // Passing the data to the
                    // EmployeeDetails Activity
                    Bundle bundle = new Bundle();

                    bundle.putString("key1", model.getName());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
        return view;
    }
}