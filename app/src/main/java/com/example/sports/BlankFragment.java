package com.example.sports;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends Fragment {

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
    TextView noGround;
    String selectedSport;

    public BlankFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
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

        selectedSport = getArguments().getString("sport");

        groundList = new ArrayList<Ground>();

        Home.db.collection("grounds").whereArrayContains("sportsPlayed",selectedSport.toUpperCase()).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        List<?> grounds = queryDocumentSnapshots.getDocuments();

                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()){
                            Ground tempGround = documentSnapshot.toObject(Ground.class);
                            groundList.add(tempGround);
                            Log.d("TAG", "onSuccess: " + tempGround.getName());
                        }
                        updateUi();
                        Log.d("TAG", "onSuccess: " + queryDocumentSnapshots.getDocuments().size());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("TAG", "onFailure: ", e);
                    }
                });

//        groundList = new ArrayList<Ground>();
//        groundList.add(new Ground("Some name","Some Address", (float) 4.5, "gs://fir-rtc-4c5df.appspot.com/Groundimages/bad.jpg"));
//        groundList.add(new Ground("Some name","Some Address", (float) 4.5));
//        groundList.add(new Ground("Some name","Some Address", (float) 4.5));
//
//        groundAdapter = new GroundAdapter(groundList,getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_blank, container, false);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),1,GridLayoutManager.VERTICAL,false);
//        grounds = view.findViewById(R.id.grounds_recycler2);
//        grounds.setLayoutManager(gridLayoutManager);
//        grounds.setAdapter(groundAdapter);
        noGround = view.findViewById(R.id.textView14);
        grounds = view.findViewById(R.id.grounds_recycler2);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),1,GridLayoutManager.VERTICAL,false);
        grounds.setLayoutManager(gridLayoutManager);
        updateUi();
        return view;
    }

    private void updateUi(){
        Log.d("TAG", "Update ui" + groundList.size());
        if (groundList.size() > 0){
            noGround.setVisibility(View.GONE);
            Log.d("TAG", "update ui: if " + groundList.size());
        }
        else{
            noGround.setText("Sorry no grounds yet");
            noGround.setVisibility(View.VISIBLE)    ;
        }
        groundAdapter = new GroundAdapter(groundList,getContext(),selectedSport);
        grounds.setAdapter(groundAdapter);
    }
}