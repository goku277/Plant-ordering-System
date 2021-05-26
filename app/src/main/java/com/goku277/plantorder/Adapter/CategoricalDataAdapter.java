package com.goku277.plantorder.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.goku277.plantorder.Components.AddToCart;
import com.goku277.plantorder.Database.Profiledb;
import com.goku277.plantorder.ProcessingUnit.AddToCartData;
import com.goku277.plantorder.ProductModel.SaveAddToCartItemsDetails;
import com.goku277.plantorder.ProductModel.SaveCategoricalPlantsData;
import com.goku277.plantorder.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoricalDataAdapter extends RecyclerView.Adapter<CategoricalDataAdapter.ViewHolder>{

    ArrayList<SaveCategoricalPlantsData> save;
    String name, mobile;
    Context context;

    FirebaseDatabase database1;
    DatabaseReference ref1;

    SaveAddToCartItemsDetails saveAddToCartItemsDetails;

    Profiledb pdb;

    String usename="", usermobile="";

    ArrayList<String> getCartDetails= new ArrayList<>();

    AddToCartData add;

    Profiledb pf;

    SaveCategoricalPlantsData saveCategoricalPlantsData= new SaveCategoricalPlantsData();

    public CategoricalDataAdapter(ArrayList<SaveCategoricalPlantsData> save, String name, String mobile, Context context) {
        this.save = save;
        this.name= name;
        this.mobile= mobile;
        this.context = context;
    }

    public ArrayList<SaveCategoricalPlantsData> getSave() {
        return save;
    }

    public void setSave(ArrayList<SaveCategoricalPlantsData> save) {
        this.save = save;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.categorical_plant_layout, parent, false);
        ViewHolder holder= new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.name.setText(save.get(position).getName());
        holder.price.setText(save.get(position).getPrice().replace("price:", "Price:"));
        holder.category.setText(save.get(position).getCategory().replace("category:", "Category:"));
        holder.details.setText(save.get(position).getDetail().replace("detail:", "Detail:"));

        database1= FirebaseDatabase.getInstance();

        Glide.with(context).load(save.get(position).getImageUri().replace("imageUri:","").trim()).into(holder.img);

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Clicked on Add To Cart", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder a11= new AlertDialog.Builder(context);
                a11.setTitle("Add to cart");
                a11.setMessage("Are you sure that you want to add this items to the cart");
                a11.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addToCart(holder, save.get(position).getImageUri());
                    }
                });

                a11.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog a1= a11.create();
                a1.show();
            }
        });

        holder.order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Clicked on Order Items", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addToCart(ViewHolder holder, String imageUri) {
        ref1= FirebaseDatabase.getInstance().getReference().child("AddToCartPerUser");
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        pdb= new Profiledb(context);

        SQLiteDatabase db= pdb.getWritableDatabase();
        String query = "select * from profile";
        Cursor c1 = db.rawQuery(query, null);

        if (c1!=null && c1.getCount() > 0) {
            if (c1.moveToFirst()) {
                usename= c1.getString(0);
                usermobile= c1.getString(1);
            }
        }
        saveAddToCartItemsDetails= new SaveAddToCartItemsDetails(usename, usermobile, holder.name.getText().toString().trim(), holder.details.getText().toString().trim(), holder.price.getText().toString().trim(), holder.category.getText().toString().trim(), save.get(holder.getAdapterPosition()).getImageUri(), holder.quantity.getText().toString().trim());

        saveAddToCartItemsDetails.setCategory(holder.category.getText().toString().trim());
        saveAddToCartItemsDetails.setImageuri(imageUri);
        saveAddToCartItemsDetails.setItemdetail(holder.details.getText().toString().trim());
        saveAddToCartItemsDetails.setMobile("usermobile: " + usermobile);
        saveAddToCartItemsDetails.setPrice(holder.price.getText().toString().trim());
        saveAddToCartItemsDetails.setUsername("username: " + usename);
        saveAddToCartItemsDetails.setName(holder.name.getText().toString().trim());
        ref1.child(usename + " " + usermobile + " " + UUID.randomUUID().toString().trim()).setValue(saveAddToCartItemsDetails);

        Toast.makeText(context, "Added to cart successfully", Toast.LENGTH_SHORT).show();

        AlertDialog.Builder a11= new AlertDialog.Builder(context);
        a11.setTitle("Check cart");
        a11.setMessage("Do you want to check your cart items?");
        a11.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkCart();
            }
        });

        a11.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog a1= a11.create();
        a1.show();
    }

    private void checkCart() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("AddToCartPerUser");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    HashMap<String, Object> dataMap = (HashMap<String, Object>) snapshot.getValue();

                    for (String key : dataMap.keySet()) {

                        Object data = dataMap.get(key);

                        try {
                            HashMap<String, Object> userData = (HashMap<String, Object>) data;         // cQualification

                            SaveAddToCartItemsDetails saveCart = new SaveAddToCartItemsDetails((String) userData.get("username"), (String) userData.get("mobile"), (String) userData.get("name"), (String) userData.get("itemdetail"), (String) userData.get("price"), (String) userData.get("category"), (String) userData.get("imageuri"), (String) userData.get("quantity"));

                            getCartDetails.add(saveCart.getUsername());
                            getCartDetails.add(saveCart.getMobile());
                            getCartDetails.add(saveCart.getName());
                            getCartDetails.add(saveCart.getItemdetail());
                            getCartDetails.add(saveCart.getPrice());
                            getCartDetails.add(saveCart.getCategory());
                            getCartDetails.add(saveCart.getImageuri());
                            getCartDetails.add(saveCart.getQuantity());
                            //   System.out.println("From Tpo getData is: " + getData);

                        } catch (ClassCastException cce) {
                            try {

                                String mString = String.valueOf(dataMap.get(key));

                                System.out.println("From Tpo mString is: " + mString);

                            } catch (ClassCastException cce2) {

                            }
                        }
                    }

                    System.out.println("getCartDetails: " + getCartDetails);

                    String val1= "";

                    for (String s: getCartDetails) {
                        val1+= s + " ";
                    }
                    System.out.println("From CategoricalDataAdapter val1: " + val1);

                    add= new AddToCartData();

                    pf= new Profiledb(context);

                    SQLiteDatabase db= pf.getWritableDatabase();
                    String query = "select * from profile";
                    Cursor c1 = db.rawQuery(query, null);

                    String username= "";

                    String usermobile= "";

                    if (c1!=null && c1.getCount() > 0) {
                        if (c1.moveToFirst()) {
                            username= c1.getString(0);
                            usermobile= c1.getString(1);
                        }
                    }

                    Set<Set<String>> a1= add.init(val1, username, usermobile);

                    System.out.println("From CategoricalDataAdapter checkCart() a1 is: " + a1);

                    String concat="";

                    for (Set<String> set1: a1) {
                        for (String s12: set1) {
                            concat+= s12 + " ";
                        }
                        concat+= " * ";
                    }

                    System.out.println("concat is: " + concat);

                    Intent sendData= new Intent(context, AddToCart.class);

                    sendData.putExtra("concat", concat);

                    context.startActivity(sendData);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return save.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, details, category, quantity;
        Button add, order;
        CircleImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name= (TextView) itemView.findViewById(R.id.name_id);
            price= (TextView) itemView.findViewById(R.id.price_id);
            details= (TextView) itemView.findViewById(R.id.details_id);
            category= (TextView) itemView.findViewById(R.id.category_id);
            quantity= (TextView) itemView.findViewById(R.id.quantity_id);

            img= (CircleImageView) itemView.findViewById(R.id.image_id);

            add= (Button) itemView.findViewById(R.id.add_to_cart_btn_id);
            order= (Button) itemView.findViewById(R.id.order_id);
        }
    }
}
