package com.example.sports;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.ViewHolder> {

    private final List<Receipt> receipts;
    private final LayoutInflater layoutInflater;
    Context context;

    public ReceiptAdapter(List<Receipt> receipts, Context context) {
        this.receipts = receipts;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.receipt_card,parent,false);
        return new ReceiptAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Receipt receipt = receipts.get(position);
        holder.timestamp.setText(receipt.getTimestamp());
        holder.cost.setText(receipt.getCost());
        holder.bookedFor.setText(receipt.getBookedFor());
        holder.groundName.setText(receipt.getGroundId());
        holder.sport.setText(receipt.getSport());
        String uri = receipt.getImage();
        if (uri != null){
//            holder.image.setImageURI(uri);
            Glide.with( context)
//                    .using(new FirebaseImageLoader())
                    .load(uri)
                    .placeholder(R.drawable.update)
                    .into(holder.image);
            Log.d("Image for ground", "onBindViewHolder: " + uri);
        }
    }

    @Override
    public int getItemCount() {
        return receipts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView groundName,cost,timestamp,sport,bookedFor;
        public ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            groundName = itemView.findViewById(R.id.receipt_ground_name);
            image = itemView.findViewById(R.id.imageView4);
            bookedFor = itemView.findViewById(R.id.booking_time);
            sport = itemView.findViewById(R.id.textView16);
            cost = itemView.findViewById(R.id.cost);
            timestamp = itemView.findViewById(R.id.book_timestamp);

        }
    }
}
