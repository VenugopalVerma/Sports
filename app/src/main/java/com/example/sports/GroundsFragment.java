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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
        Home.db.collection("grounds").whereEqualTo("address.city",Home.locationCity).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                List<?> temp = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()){
                    Ground tempGround = documentSnapshot.toObject(Ground.class);
                    groundList.add(tempGround);
//                    Log.d("TAG", "onSuccess: " + tempGround.getName());
                }
                updateUi();
//                Toast.makeText(getContext(),"Total no of documents" + temp.size(),Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(getContext(),"Failed Total no of documents",Toast.LENGTH_LONG).show();
                Log.e("TAG", "onFailure: ", e);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_grounds, container, false);
//        grounds = view.findViewById(R.id.sports_recycler);
//        groundAdapter = new GroundAdapter(groundList,getContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),1,GridLayoutManager.VERTICAL,false);
        grounds = view.findViewById(R.id.grounds_recycler);
        grounds.setLayoutManager(gridLayoutManager);
        updateUi();
        return view;
    }

    private void updateUi(){
        groundAdapter = new GroundAdapter(groundList,getContext(), null);
        grounds.setAdapter(groundAdapter);
    }
}