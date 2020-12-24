package com.example.sports;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.sports.Home.db;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Spinner spinner,daysSpinner,timeSpinner;
    Ground ground;
    List<String> sportsPlayed;
    private Button book;

    public BookingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookingFragment newInstance(String param1, String param2) {
        BookingFragment fragment = new BookingFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_booking, container, false);

        spinner = view.findViewById(R.id.sport_spinner);
        daysSpinner = view.findViewById(R.id.date_spinner);
        timeSpinner = view.findViewById(R.id.time_spinner);
        book = view.findViewById(R.id.book);


        db.collection("grounds").document("xcHQVQnRtJWDWlVoxqmQ")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        ground = documentSnapshot.toObject(Ground.class);
                        HashMap<String,Object> data = ground.getData();
                        sportsPlayed = new ArrayList<String>(data.keySet());
                        Log.d("TAG", "onSuccess: " + sportsPlayed);
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(),
                                R.layout.support_simple_spinner_dropdown_item,
                                sportsPlayed);
//                        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                        spinner.setAdapter(adapter);

                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                String option = String.valueOf(spinner.getSelectedItem());  //Don't forget to move this here otherwise it won't be updated.

                                HashMap<String,Object> subMap = (HashMap<String, Object>) data.get(option);
                                List<String> days = new ArrayList<String>(subMap.keySet());
                                Collections.sort(days);
                                ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(view.getContext(), R.layout.support_simple_spinner_dropdown_item, days);
                                arrayAdapter1.notifyDataSetChanged();
                                daysSpinner.setAdapter(arrayAdapter1);
                                daysSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        String day = String.valueOf(daysSpinner.getSelectedItem());
                                        HashMap<String,Boolean> dayMap = (HashMap<String, Boolean>) subMap.get(day);
                                        List<String> slots = new ArrayList<String>(dayMap.keySet());
                                        List<String> availableSlots = new ArrayList<String>();
                                        for (int a = 0; a < slots.size(); a++){
                                            String time = slots.get(a);
                                            if (dayMap.get(time)){
                                                availableSlots.add(time);

                                            }
                                        }
                                        Collections.sort(availableSlots);
                                        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(view.getContext(), R.layout.support_simple_spinner_dropdown_item, availableSlots);
                                        arrayAdapter2.notifyDataSetChanged();

                                        timeSpinner.setAdapter(arrayAdapter2);
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                                Toast.makeText(view.getContext(),option,Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("TAG", "onFailure: ", e);
                    }
                });

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sport = String.valueOf(spinner.getSelectedItem());
                String day = String.valueOf(daysSpinner.getSelectedItem());
                String time = String.valueOf(timeSpinner.getSelectedItem());
                String field = "data."+sport + "." + day + "." + time;
                db.collection("grounds").document("xcHQVQnRtJWDWlVoxqmQ")
                        .update(field,false)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(view.getContext(),"Data updated\n Ground booked",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(view.getContext(),"Booking Failed",Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
//        String []sports = {"BADMINTON","CRICKET","FOOTBALL","GOLF","BASKETBALL"};
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(),
//                R.layout.support_simple_spinner_dropdown_item,
//                sportsPlayed);
////        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);



        return view;
    }
}