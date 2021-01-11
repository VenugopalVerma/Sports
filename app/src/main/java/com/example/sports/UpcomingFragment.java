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
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpcomingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpcomingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView receipts;
    ReceiptAdapter receiptAdapter;
    List<Receipt> receiptList;

    public UpcomingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpcomingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpcomingFragment newInstance(String param1, String param2) {
        UpcomingFragment fragment = new UpcomingFragment();
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

        receiptList = new ArrayList<Receipt>();
//        HashMap<String,Object> map = new HashMap<>();
//        HashMap<String,Object> data = new HashMap<>();
//        data.put("cost","1234");
//        data.put("groundName","Some Name");
//        data.put("groundId","Some ID");
//        data.put("timestamp","some timestamp");
//
//        map.put("time_booked",data);
//
//        receiptList.add(new Receipt(data));
//        receiptList.add(new Receipt(data));
//        receiptList.add(new Receipt(data));
//        receiptList.add(new Receipt(data));
//        receiptList.add(new Receipt(data));
//        receiptList.add(new Receipt(data));
        Home.db.collection("users").document(Home.mAuth.getUid()).collection("upcomings").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots.getDocuments()){
                            Log.d("TAG", "onSuccess: " + queryDocumentSnapshots.size());
                            receiptList.add(new Receipt((HashMap<String, Object>) queryDocumentSnapshot.getData()));
                        }
                        updateUi();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("TAG", "onFailure: ", e);
                    }
                });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_upcoming, container, false);
//        grounds = view.findViewById(R.id.sports_recycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),1,GridLayoutManager.VERTICAL,false);
        receipts = view.findViewById(R.id.upcoming_bookings);
        receipts.setLayoutManager(gridLayoutManager);

        return view;
    }

    private void updateUi(){
        receiptAdapter = new ReceiptAdapter(receiptList,getContext());
        receipts.setAdapter(receiptAdapter);
    }
}