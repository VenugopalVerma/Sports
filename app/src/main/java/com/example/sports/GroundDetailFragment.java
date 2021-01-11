package com.example.sports;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GroundDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroundDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Ground ground;

    public GroundDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GroundDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GroundDetailFragment newInstance(String param1, String param2) {
        GroundDetailFragment fragment = new GroundDetailFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ground_detail, container, false);

        TextView groundName = view.findViewById(R.id.textView5);
        groundName.setText(ground.getName());

        TextView address = view.findViewById(R.id.textView6);
        address.setText("Address :" + ground.getAddress().get("city"));

        TextView sportsPlayed = view.findViewById(R.id.textView8);
        sportsPlayed.setText("Sports Played : " + String.valueOf(ground.getSportsPlayed()));

        RatingBar ratingBar = view.findViewById(R.id.ratingBar);
        ratingBar.setRating(ground.getRating());

        ImageView image = view.findViewById(R.id.imageView5);
        String uri = ground.getImage();
        if (uri != null){
//            holder.image.setImageURI(uri);
            Glide.with(view)
//                    .using(new FirebaseImageLoader())
                    .load(uri).placeholder(R.drawable.update)
                    .into(image );

            Log.d("Image for ground", "onBindViewHolder: " + uri);
        }

        Button book = view.findViewById(R.id.continue_to_book);
        book.setOnClickListener(view1 -> {
            Navigation.findNavController(view1).navigate(R.id.action_groundDetailFragment_to_bookingFragment,getArguments());
        });
        return view;
    }
}