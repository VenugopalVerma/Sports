package com.example.sports;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.Calendar;
import java.util.List;

public class GroundAdapter extends RecyclerView.Adapter<GroundAdapter.ViewHolder> {

    private List<Ground> grounds;
    private LayoutInflater layoutInflater;
    Context context;

    public GroundAdapter(List<Ground> grounds, Context context) {
        this.grounds = grounds;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);

    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.ground_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(grounds.get(position).getName());
        holder.card.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (Home.navController.getCurrentDestination().getId() == R.id.blankFragment) {
                    Navigation.findNavController(view).navigate(R.id.action_blankFragment_to_groundDetailFragment);
                }
                else {
                    Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_groundDetailFragment);
                }
            }
        });

        Uri uri = Home.temp;
        if (uri != null){
            holder.image.setImageURI(uri);
            Glide.with( holder.image.getContext()/* context */)
//                    .using(new FirebaseImageLoader())
                    .load(uri).placeholder(R.drawable.bskt)
                    .into(holder.image );
            Log.d("Image for ground", "onBindViewHolder: " + uri);
        }
//        Bitmap bmp = Home.bmp;
//        holder.image.setImageBitmap(bmp);

    }

    @Override
    public int getItemCount() {
        return grounds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public ImageView image;
        public CardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.ground_name);
            image = itemView.findViewById(R.id.imageView2);
            card = itemView.findViewById(R.id.ground_card);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Fragment getCurrentFragmentId(){
//        Activity activity = (Activity) context;
        Home.navController.getCurrentDestination().getId();
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime();
//        android.app.Fragment fragment =  activity.getFragmentManager().findFragmentById(R.id.nav_host_fragment_container);
//        FragmentManager fragmentManager = activity.getFragmentManager();
//        fragment.getChildFragmentManager().findFragmentById(R.layout.)
//        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
//        return navHostFragment == null ? null : navHostFragment.getChildFragmentManager().getFragments().get(0);
//        List<android.app.Fragment> fragments = fragmentManager.getFragments();

//        for (android.app.Fragment fragment : fragments) {
//            if (fragment != null && fragment.isVisible())
//                return fragment;
//        }
        return null;
    }

}
