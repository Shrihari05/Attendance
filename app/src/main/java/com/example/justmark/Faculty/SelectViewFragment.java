package com.example.justmark.Faculty;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.justmark.Admin.subject;
import com.example.justmark.R;
import com.example.justmark.Student.MarkActivity;
import com.example.justmark.StudentSubject;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectViewFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SelectViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelectViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectViewFragment newInstance(String param1, String param2) {
        SelectViewFragment fragment = new SelectViewFragment();
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
    SharedPreferences sh;Button calen;

    ArrayList<String> s=new ArrayList<>();
    Spinner spino; String st="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context= container.getContext();
        sh=getActivity().getSharedPreferences("College", Context.MODE_PRIVATE);
        String college=sh.getString("CName",null);
        View view= inflater.inflate(R.layout.fragment_select_view, container, false);
        spino = (Spinner)view.findViewById(R.id.coursesspinner);
        TextView t=getActivity().findViewById(R.id.title);
        EditText b=(EditText) view.findViewById(R.id.datp);
calen=(Button)view.findViewById(R.id.calen);
        t.setText("Subjects");
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://justmark-38096-default-rtdb.asia-southeast1.firebasedatabase.app");
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
                }
                ArrayAdapter ad = new ArrayAdapter(context, android.R.layout.simple_spinner_item, s);

                ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spino.setAdapter(ad);

                spino.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        st = parent.getItemAtPosition(position).toString();
                        Toast.makeText(parent.getContext(), st, Toast.LENGTH_SHORT).show();
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
        calen.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();

            // on below line we are getting
            // our day, month and year.
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // on below line we are creating a variable for date picker dialog.
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    // on below line we are passing context.
                    getActivity(),
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            // on below line we are setting date to our edit text.
                            b.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);


                        }
                    },
                    // on below line we are passing year,
                    // month and day for selected date in our date picker.
                    year, month, day);
            // at last we are calling show to
            // display our date picker dialog.
            datePickerDialog.show();
        });






        Button bat=view.findViewById(R.id.button);
        bat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
String d=b.getText().toString();
                Intent intent = new Intent(getActivity(), ViewActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("Sub", st);
                bundle.putString("Date", d);intent.putExtras(bundle);
                startActivity(intent);
          }
        });
        return view;
    }

}