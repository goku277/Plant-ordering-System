package com.goku277.plantorder.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.goku277.plantorder.Components.CollapsingToolbar;
import com.goku277.plantorder.ProductModel.SaveAllSeasonPlantsData;
import com.goku277.plantorder.ProductModel.SaveSummerPlantsData;
import com.goku277.plantorder.R;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class AllSeasonAdapter extends RecyclerView.Adapter<AllSeasonAdapter.ViewHolder>{

    ArrayList<SaveAllSeasonPlantsData> save= new ArrayList<>();
    Set<Set<String>> setOfSets= new LinkedHashSet<>();
    Context context;
    ArrayList<String> detailsList= new ArrayList<>();

    public AllSeasonAdapter(Context context, ArrayList<SaveAllSeasonPlantsData> save, Set<Set<String>> setOfSets) {
        this.save = save;
        this.context= context;
        this.setOfSets= setOfSets;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.allseasonlayout, parent, false);
        ViewHolder holder= new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Glide.with(context).load(save.get(position).getImageUri()).into(holder.image);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           //     Toast.makeText(context, "Clicked on: " + save.get(holder.getAdapterPosition()).getImageUri(), Toast.LENGTH_SHORT).show();
                System.out.println("Clicked on: " + save.get(holder.getAdapterPosition()).getImageUri());
                String url= save.get(position).getImageUri().trim();
                for (Set<String> set1: setOfSets) {
                    for (String s: set1) {
                        if (s.contains("imageUri:")) {
                            s= s.replace("imageUri:","").trim();
                            if (s.equals(url)) {
                                doWork(set1, save.get(holder.getAdapterPosition()).getImageUri());
                            }
                        }
                    }
                }
            }
        });
    }

    private void doWork(Set<String> set1, String url) {
        detailsList.addAll(set1);
        System.out.println("From AllSeasonAdapter doWork() is: " + detailsList);

        String val1= "";

        for (String s: detailsList) {
            val1+= s+ " ";
        }

        System.out.println("From AllSeasonAdapter val1: " + val1);

        String split[]= val1.split("name:");

        String details="", s1="";

        ArrayList<String> detailsLList= new ArrayList<>();

        for (String s: split) {
            if (!s.trim().isEmpty()) {
                s= s.trim();
                System.out.println("split: " + s);
                if (s.contains("imageUri:")) {
                    s1= s;
                    String str= s.substring(s.indexOf("imageUri:")).replace("imageUri:","").trim();
                    if (str.equals(url.trim())) {
                        details = s1;
                    }
                }
            }
        }

        System.out.println("From AllSeasonAdapter details: " + details);

        if (details.contains("price:")) {
            detailsLList.add(details.substring(details.indexOf(details.charAt(0)), details.indexOf("price:")).trim());
        }
        if (details.contains("price:") && details.contains("detail:")) {
            detailsLList.add(details.substring(details.indexOf("price:"), details.indexOf("detail:")).trim());
        }
        if (details.contains("detail:") && details.contains("category:")) {
            detailsLList.add(details.substring(details.indexOf("detail:"), details.indexOf("category:")).trim());
        }
        if (details.contains("category:") && details.contains("key:")) {
            detailsLList.add(details.substring(details.indexOf("category:"), details.indexOf("key:")).trim());
        }
        if (details.contains("key:") && details.contains("imageUri:")) {
            detailsLList.add(details.substring(details.indexOf("key:"), details.indexOf("imageUri:")).trim());
        }
        if (details.contains("imageUri:")) {
            detailsLList.add(details.substring(details.indexOf("imageUri:")).trim());
        }

        Intent sendData= new Intent(context, CollapsingToolbar.class);
        sendData.putStringArrayListExtra("details", detailsLList);
        context.startActivity(sendData);
    }

    @Override
    public int getItemCount() {
        return save.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image= itemView.findViewById(R.id.image);
        }
    }
}