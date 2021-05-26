package com.goku277.plantorder.Components;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.goku277.plantorder.Adapter.AddToCartAdapter;
import com.goku277.plantorder.Adapter.CategoricalDataAdapter;
import com.goku277.plantorder.ProcessingUnit.AddToCartData;
import com.goku277.plantorder.ProductModel.AddToCartModelData;
import com.goku277.plantorder.R;

import java.util.ArrayList;

public class AddToCart extends AppCompatActivity {

    String details="";

    RecyclerView rec;

    AddToCartAdapter addToCartData;

    ArrayList<AddToCartModelData> dataList= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);

        rec= (RecyclerView) findViewById(R.id.recyclerview_id);

        Intent getData= getIntent();
        details= getData.getStringExtra("concat");

        System.out.println("From AddToCart details: " + details);

        String split[]= details.split("\\*");

        for (String s: split) {
            if (!s.trim().isEmpty()) {
                System.out.println("split: " + s);
                addToCart(s);
            }
        }
    }

    private void addToCart(String s) {
        ArrayList<String> a11= new ArrayList<>();
        if (s.contains("Name:") && s.contains("Price:")) {
            a11.add(s.substring(s.indexOf("Name:"), s.indexOf("Price:")).trim());
        }
        if (s.contains("Price:") && s.contains("imageUri:")) {
            a11.add(s.substring(s.indexOf("Price:"), s.indexOf("imageUri:")).trim());
        }
        if (s.contains("imageUri:") && s.contains("quantity:")) {
            a11.add(s.substring(s.indexOf("imageUri:")).trim());
        }
        if (s.contains("quantity:")) {
            a11.add(s.substring(s.indexOf("quantity")));
        }
        System.out.println("a11: " + a11);

        AddToCartModelData addToCartModelData= new AddToCartModelData(a11.get(2), a11.get(1), a11.get(0), a11.get(3));

        dataList.add(addToCartModelData);


        LinearLayoutManager layoutManager= new LinearLayoutManager(AddToCart.this, LinearLayoutManager.VERTICAL, false);

        rec.setLayoutManager(layoutManager);

        rec.setItemAnimator(new DefaultItemAnimator());

        addToCartData = new AddToCartAdapter(dataList, AddToCart.this);

        rec.setAdapter(addToCartData);
    }
}