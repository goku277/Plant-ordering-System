package com.goku277.plantorder.Adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.goku277.plantorder.ProductModel.SeedsAndFertilizersModelData;
import com.goku277.plantorder.R;

import java.util.ArrayList;

public class SeedsAndFertilizersAdapter extends RecyclerView.Adapter<SeedsAndFertilizersAdapter.ViewHolder>{

    ArrayList<SeedsAndFertilizersModelData> seedsData= new ArrayList<>();

    Context context;

    public SeedsAndFertilizersAdapter(ArrayList<SeedsAndFertilizersModelData> seedsData, Context context) {
        this.seedsData = seedsData;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.seedsandfertilizerslayout, parent, false);
        ViewHolder holder= new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText("Name: "+ seedsData.get(position).getName().replace("name:",""));
        holder.quantity.setText("Quantity: " + seedsData.get(position).getQuantity().replace("quantity:",""));
        holder.category.setText("Category: " + seedsData.get(position).getCategory().replace("category:",""));
        holder.details.setText("Details: " + seedsData.get(position).getDetail().replace("detail:",""));
        holder.seedsqty.setText("Seeds Quantity: " + seedsData.get(position).getSeedqty().replace("seedsqty:",""));
        holder.price.setText("Price: " + seedsData.get(position).getPrice().replace("price:",""));
        Glide.with(context).load(seedsData.get(position).getImageUrl()).into(holder.cig);
    }

    @Override
    public int getItemCount() {
        return seedsData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cig;
        TextView name, price, seedsqty, details, category, quantity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cig= (ImageView) itemView.findViewById(R.id.cig_id);
            name= (TextView) itemView.findViewById(R.id.name_id);
            price= (TextView) itemView.findViewById(R.id.price_id);
            seedsqty= (TextView) itemView.findViewById(R.id.seeds_qty_id);
            details= (TextView) itemView.findViewById(R.id.details_id);
            category= (TextView) itemView.findViewById(R.id.category_id);
            quantity= (TextView) itemView.findViewById(R.id.quantity_id);
        }
    }
}