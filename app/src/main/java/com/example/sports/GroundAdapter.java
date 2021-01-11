package com.example.sports;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class GroundAdapter extends RecyclerView.Adapter<GroundAdapter.ViewHolder> {

    private List<Ground> grounds;
    private LayoutInflater layoutInflater;
    Context context;
    String selectedSport;

    public GroundAdapter(List<Ground> grounds, Context context, String selectedSport) {
        this.grounds = grounds;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.selectedSport = selectedSport;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.ground_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ground ground = grounds.get(position);
        holder.name.setText(ground.getName());
        holder.rating.setText(String.valueOf(ground.getRating()));
        holder.sportsPlayed.setText(String.valueOf(ground.getSportsPlayed()));
        holder.address.setText((CharSequence) ground.getAddress().get("city"));

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("ground",grounds.get(position));
                bundle.putString("sport",selectedSport);
                if (Home.navController.getCurrentDestination().getId() == R.id.blankFragment) {
                    Navigation.findNavController(view).navigate(R.id.action_blankFragment_to_groundDetailFragment,bundle);
                }
                else {
                    Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_groundDetailFragment,bundle);
                }
            }
        });

        String uri = ground.getImage();
        if (uri != null){
//            holder.image.setImageURI(uri);
            Glide.with( context)
//                    .using(new FirebaseImageLoader())
                    .load(uri)
                    .placeholder(R.drawable.update)
                    .into(holder.image );

            Log.d("Image for ground", "onBindViewHolder: " + uri);
        }
//        Bitmap bmp = Home.bmp;
//        holder.image.setImageBitmap(bmp);
        Log.d("Bind view holder", "onBindViewHolder: " + grounds.get(position).getName() );
    }

    @Override
    public int getItemCount() {
        return grounds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name,rating,address,sportsPlayed;
        public ImageView image;
        public CardView card;
//        public RatingBar rating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.ground_name);
            image = itemView.findViewById(R.id.imageView2);
            card = itemView.findViewById(R.id.ground_card);
            rating = itemView.findViewById(R.id.ground_rating);
            address = itemView.findViewById(R.id.ground_loation);
            sportsPlayed = itemView.findViewById(R.id.sports_played);

        }
    }

}
