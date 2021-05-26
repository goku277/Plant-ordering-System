package com.goku277.plantorder.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Placeholder;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.goku277.plantorder.Components.PlaceOrder;
import com.goku277.plantorder.ProductModel.AddToCartModelData;
import com.goku277.plantorder.R;

import java.util.ArrayList;

public class AddToCartAdapter extends RecyclerView.Adapter<AddToCartAdapter.ViewHolder>{

    ArrayList<AddToCartModelData> dataList= new ArrayList<>();
    Context context;

    public AddToCartAdapter(ArrayList<AddToCartModelData> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.add_to_cart_layout_list_item, parent, false);
        ViewHolder holder= new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.price.setText(dataList.get(position).getPrice());
        holder.name.setText(dataList.get(position).getName());
        System.out.println("From onBindViewHolder() dataList.get(position).getImageUrl(): " + dataList.get(position).getImageUrl());
        Glide.with(context).load(dataList.get(position).getImageUrl().replace("imageUri:","").trim()).into(holder.cig);

        holder.cig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Clicked on: " + dataList.get(position).getName(), Toast.LENGTH_SHORT).show();
                AlertDialog.Builder a11= new AlertDialog.Builder(context);
                a11.setTitle("Alert");
                a11.setMessage("Place order or delete");
                a11.setPositiveButton("Order", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        order(position);
                    }
                });
                a11.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete();
                    }
                });

                AlertDialog a1= a11.create();
                a1.show();
            }
        });

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Clicked on: " + dataList.get(position).getName(), Toast.LENGTH_SHORT).show();
                AlertDialog.Builder a11= new AlertDialog.Builder(context);
                a11.setTitle("Alert");
                a11.setMessage("Place order or delete");
                a11.setPositiveButton("Order", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        order(position);
                    }
                });
                a11.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete();
                    }
                });

                AlertDialog a1= a11.create();
                a1.show();
            }
        });

        holder.price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Clicked on: " + dataList.get(position).getName(), Toast.LENGTH_SHORT).show();
                AlertDialog.Builder a11= new AlertDialog.Builder(context);
                a11.setTitle("Alert");
                a11.setMessage("Place order or delete");
                a11.setPositiveButton("Order", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       // order(dataList.get(position).getName(), dataList.get(position).getPrice(), dataList.get(position).get);
                        order(position);
                    }
                });
                a11.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete();
                    }
                });

                AlertDialog a1= a11.create();
                a1.show();
            }
        });
    }

    private void order(int position) {
        Intent sendData= new Intent(context, PlaceOrder.class);
        sendData.putExtra("name", dataList.get(position).getName());
        sendData.putExtra("price", dataList.get(position).getPrice());
        sendData.putExtra("qty", dataList.get(position).getQty());
        sendData.putExtra("imageurl", dataList.get(position).getImageUrl());
        context.startActivity(sendData);
      /*  StringBuilder sb1= new StringBuilder();
        sb1.append(dataList.get(position).getName() + "\n");
        sb1.append(dataList.get(position).getPrice() + "\n");
        sb1.append(dataList.get(position).getQty() + "\n");
        AlertDialog.Builder a11= new AlertDialog.Builder(context);
        a11.setTitle("Order this item");
        a11.setMessage("Place order for this item\n\n" + sb1);
        a11.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        a11.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog a1= a11.create();
        a1.show();   */
    }

    private void delete() {

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout parent;
        ImageView cig;
        TextView name, price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent= (ConstraintLayout) itemView.findViewById(R.id.parent_id);
            cig= (ImageView) itemView.findViewById(R.id.img_id);
            name= (TextView) itemView.findViewById(R.id.name_id);
            price= (TextView) itemView.findViewById(R.id.price_id);
        }
    }
}