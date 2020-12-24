package com.example.sports;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.sports.Home.mAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView displayName;
    private Button signOut;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private Ground ground;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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

        ground = new Ground("Name 1", "address 1", (float) 4.5);
//        Boolean[] arr = new Boolean[7];
        HashMap<String,Boolean> time = new HashMap<String, Boolean>();
        time.put("5",true);
        time.put("6",true);
        time.put("7",true);
        time.put("8",true);
        time.put("9",true);
        time.put("10",true);
        time.put("11",true);
        time.put("12",false);
        time.put("13",true);
        time.put("14",true);
        time.put("15",true);
        time.put("16",false);
        time.put("17",true);
        time.put("18",true);
        time.put("19",true);
        time.put("20",false);
        time.put("21",true);
        time.put("22",true);

//        List<Boolean> arr = new ArrayList<Boolean>();
//        arr.add(true);
//        arr.add(true);
//        arr.add(true);
//        arr.add(true);
//        arr.add(true);
//        arr.add(true);
//        arr.add(true);
        HashMap<String, Object> data = new HashMap<String, Object>();
        HashMap<String, Object> subdata = new HashMap<String, Object>();
        subdata.put("0-SUN",time);
        subdata.put("1-MON",time);
        subdata.put("2-TUE",time);
        subdata.put("3-WED",time);
        subdata.put("4-THU",time);
        subdata.put("5-FRI",time);
        subdata.put("6-SAT",time);
        data.put("BADMINTON", subdata);
        data.put("CRICKET", subdata);
        data.put("BASKETBALL", subdata);
        data.put("FOOTBALL", subdata);
        ground.setData(data);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        signOut = view.findViewById(R.id.signOut);
        displayName = view.findViewById(R.id.displayName);
//        signOut.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.homeFragment));

        TextView addData = view.findViewById(R.id.secret_button);
//        addData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Home.db.collection("grounds").add(ground).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Toast.makeText(view.getContext(),"Data added",Toast.LENGTH_SHORT).show();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(view.getContext(),"Failed",Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });


//        updateUi();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                Log.d("auth change", "onCreateView of profile: ");
                updateUi();
            }
        };
        Log.d("Profile", "onCreateView: ");

        return view;
    }

    public void updateUi(){

        FirebaseUser user = mAuth.getCurrentUser();
        Log.d("TAG", "updateUi: Profile");
        if (user != null){
            displayName.setText(user.getDisplayName());
            signOut.setVisibility(View.VISIBLE);
            signOut.setOnClickListener(view -> {
                signOut();
//                Navigation.findNavController(view).navigate(R.id.homeFragment);
            });
        }
        else {
            displayName.setText("");
            signOut.setVisibility(View.GONE);
        }


    }

    public void signOut() {
        // [START auth_fui_signout]
        AuthUI.getInstance()
                .signOut(getContext())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.i("Sign Out","Signed Out successful");
                    }
                });
        // [END auth_fui_signout]

        Toast.makeText(getContext(),"User signed out",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onResume() {
        super.onResume();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mAuthStateListener != null){
            if (mAuth != null){
                mAuth.removeAuthStateListener(mAuthStateListener);
            }
        }
    }
}