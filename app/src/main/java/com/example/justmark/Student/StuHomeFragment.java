package com.example.justmark.Student;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.justmark.Faculty.FacSubjectFragment;
import com.example.justmark.Faculty.UpdateFragment;
import com.example.justmark.R;
import static com.example.justmark.Student.StudentHomeActivity.frag;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StuHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StuHomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StuHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StuHomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StuHomeFragment newInstance(String param1, String param2) {
        StuHomeFragment fragment = new StuHomeFragment();
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
ImageView take,look;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_stu_home, container, false);
        take=(ImageView) view.findViewById(R.id.take);

        look=(ImageView) view.findViewById(R.id.imageView2);
        take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frag=0;
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame, new StuSubFragment()).addToBackStack(null).commit();

            }
        });
        look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frag=1;
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame, new StuSubFragment()).addToBackStack(null).commit();

            }
        });
        return view;
    }
}