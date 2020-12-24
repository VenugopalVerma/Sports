package com.example.sports;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GroundsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroundsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView grounds;
    GroundAdapter groundAdapter;
    List<Ground> groundList;

    public GroundsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GroundsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GroundsFragment newInstance(String param1, String param2) {
        GroundsFragment fragment = new GroundsFragment();
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

        groundList = new ArrayList<Ground>();
        groundList.add(new Ground("Some name","Some Address", (float) 4.5, "gs://fir-rtc-4c5df.appspot.com/Groundimages/bad.jpg"));
        groundList.add(new Ground("Some name","Some Address", (float) 4.5));
        groundList.add(new Ground("Some name","Some Address", (float) 4.5));
        groundList.add(new Ground("Some name","Some Address", (float) 4.5));
        groundList.add(new Ground("Some name","Some Address", (float) 4.5));

        groundAdapter = new GroundAdapter(groundList,getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_grounds, container, false);
//        grounds = view.findViewById(R.id.sports_recycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),1,GridLayoutManager.VERTICAL,false);
        grounds = view.findViewById(R.id.grounds_recycler);
        grounds.setLayoutManager(gridLayoutManager);
        grounds.setAdapter(groundAdapter);
        return view;
    }
}