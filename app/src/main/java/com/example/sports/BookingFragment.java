package com.example.sports;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.example.sports.Home.db;
import static com.example.sports.Home.mAuth;

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
    String selected;

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

        ground = (Ground) getArguments().get("ground");
        selected = (String) getArguments().get("sport");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_booking, container, false);

        spinner = view.findViewById(R.id.sport_spinner);
        daysSpinner = view.findViewById(R.id.date_spinner);
        timeSpinner = view.findViewById(R.id.time_spinner);

//        Log.d("TAG", "onCreateView: " + ground.getDocId());
        setSpinnerData(ground.getDocId());

        Button book = view.findViewById(R.id.book);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sport = String.valueOf(spinner.getSelectedItem());
                String day = String.valueOf(daysSpinner.getSelectedItem());
                String time = String.valueOf(timeSpinner.getSelectedItem());

                Log.d("TAG", "onClick: " + sport + day + time);
                if (time.isEmpty() || time.equals("null")){
                    Toast.makeText(getContext(),"Try Again",Toast.LENGTH_LONG).show();
                }
                else {
                    createBatch(ground.getDocId(),sport,day,time);
                }

//                db.collection("grounds").document("xcHQVQnRtJWDWlVoxqmQ")
//                        .update(field,false)
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                Toast.makeText(view.getContext(),"Data updated\n Ground booked",Toast.LENGTH_SHORT).show();
//                                Navigation.findNavController(view).navigate(R.id.action_bookingFragment_to_historyFragment);
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Toast.makeText(view.getContext(),"Booking Failed",Toast.LENGTH_SHORT).show();
//                            }
//                        });
            }
        });
//        String []sports = {"BADMINTON","CRICKET","FOOTBALL","GOLF","BASKETBALL"};
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(),
//                R.layout.support_simple_spinner_dropdown_item,
//                sportsPlayed);
////        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);



        return view;
    }

    private void setSpinnerData(String docId){

        sportsPlayed = ground.getSportsPlayed();
        Log.d("TAG", "onSuccess: " + sportsPlayed);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                R.layout.support_simple_spinner_dropdown_item,
                sportsPlayed);
//                        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        Log.d("TAG", "setSpinnerData: " + selected);
        if (selected != null){
            spinner.setSelection(sportsPlayed.indexOf(selected));
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                    String sport = String.valueOf(spinner.getSelectedItem());
                String sport = adapterView.getItemAtPosition(i).toString();
                Log.d("TAG", "onItemSelected: " + sport);

                ArrayList<String> days = dateList();
                ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(view.getContext(), R.layout.support_simple_spinner_dropdown_item, days);
                arrayAdapter1.notifyDataSetChanged();
                daysSpinner.setAdapter(arrayAdapter1);
                daysSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String day = String.valueOf(daysSpinner.getSelectedItem()).substring(0,3);
                        Log.d("TAG", "onItemSelected: " + day + docId + sport);


                        db.collection("grounds").document(docId).collection("sports").document(sport).get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {

                                    Map<String, Object> sportDocument = documentSnapshot.getData();
                                    Log.d("TAG", "onItemSelected: " + sportDocument);
                                    @SuppressWarnings("unchecked") Map<String,Boolean> dayMap = (Map<String, Boolean>) sportDocument.get(day);
                                    List<String> slots = new ArrayList<String>(dayMap.keySet());
                                    List<String> availableSlots = new ArrayList<>();
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
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e("TAG", "onFailure: ", e);
                                }
                            });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                Toast.makeText(view.getContext(),sport,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void createBatch(String docId,String sport,String day,String time){
        WriteBatch batch = db.batch();
        DocumentReference groundRef = db.collection("grounds").document(docId).collection("sports").document(sport);
        batch.update(groundRef,day.substring(0,3)+"."+time,false);

        DocumentReference user = db.collection("users").document(mAuth.getUid()).collection("upcomings").document();


        HashMap<String,Object> data = new HashMap<>();
        data.put("cost","1234");
        data.put("image",this.ground.getImage());
        data.put("groundId",docId);
        data.put("sport",sport);
        data.put("bookedFor",day +" "+ time);
        data.put("timestamp",new Date().toString());

//        Log.d("TAG", "createBatch: "+db.collection("users").document(mAuth.getUid()).collection("upcommings").document());
//        batch.update(user,"bookings.upcoming." + day + " " + time,new Receipt(data));
        batch.set(user,data);

        batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getContext(),"Data updated\n Ground booked",Toast.LENGTH_SHORT).show();
                Navigation.findNavController(getView()).navigate(R.id.action_bookingFragment_to_historyFragment);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),"Booking Failed",Toast.LENGTH_SHORT).show();
                Log.e("Booking", "onFailure: ", e);
            }
        });
    }

    private ArrayList<String> dateList(){
        Calendar calendar = Calendar.getInstance();
        ArrayList<String> list = new ArrayList<String>();
        String temp;
        DateFormat dateFormat = new SimpleDateFormat("EEE dd-MMM-yyyy",Locale.getDefault());

        for (int i=0; i < 7;i++){
            String day = calendar.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.SHORT, Locale.getDefault());
            temp = dateFormat.format(calendar.getTime());
            list.add(temp.toUpperCase());
            calendar.add(Calendar.DATE,1);
        }
        return list;
    }
}