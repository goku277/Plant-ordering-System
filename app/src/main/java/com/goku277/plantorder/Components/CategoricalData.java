package com.goku277.plantorder.Components;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.goku277.plantorder.Adapter.AllSeasonAdapter;
import com.goku277.plantorder.Adapter.CategoricalDataAdapter;
import com.goku277.plantorder.Adapter.SummerAdapter;
import com.goku277.plantorder.Database.Profiledb;
import com.goku277.plantorder.ProductModel.SaveCategoricalPlantsData;
import com.goku277.plantorder.R;

import java.util.ArrayList;

public class CategoricalData extends AppCompatActivity {

    String value="";

    RecyclerView recyclerView;

    SaveCategoricalPlantsData save;

    CategoricalDataAdapter categoricalDataAdapter;

    ArrayList<SaveCategoricalPlantsData> save1= new ArrayList<>();

    String name="", mobile="";

    ArrayList<String> fetchData;

    Profiledb pf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorical_data);

        recyclerView= (RecyclerView) findViewById(R.id.recycler_id);

        pf= new Profiledb(CategoricalData.this);

        Intent getData= getIntent();

        value= getData.getStringExtra("val");

        System.out.println("From CategoricalData value is: " + value);

        String split[]= value.split("name:");

        for (String s: split) {
            if (!s.trim().isEmpty()) {
                fetchData= new ArrayList<>();
                s= s.trim();
                System.out.println("From CategoricalData split is: " + s);
                if (s.contains("price:")) {
                    fetchData.add("Name: " + s.substring(s.indexOf(s.charAt(0)), s.indexOf("price:")).trim());
                }
                if (s.contains("price:") && s.contains("detail:")) {
                    fetchData.add(s.substring(s.indexOf("price:"), s.indexOf("detail:")).trim());
                }
                if (s.contains("detail:") && s.contains("category:")) {
                    fetchData.add(s.substring(s.indexOf("detail:"), s.indexOf("category:")).trim());
                }
                if (s.contains("category:") && s.contains("key:")) {
                    fetchData.add(s.substring(s.indexOf("category:"), s.indexOf("key:")).trim());
                }
                if (s.contains("key:") && s.contains("imageUri:")) {
                    fetchData.add(s.substring(s.indexOf("key:"), s.indexOf("imageUri:")).trim());
                }
                if (s.contains("imageUri:") && s.contains("quantity:")) {
                    fetchData.add(s.substring(s.indexOf("imageUri:")).trim());
                }
                if (s.contains("quantity:")) {
                    fetchData.add(s.substring(s.indexOf("quantity:")));
                }
                save= new SaveCategoricalPlantsData(fetchData.get(0), fetchData.get(1), fetchData.get(2), fetchData.get(3), fetchData.get(5), fetchData.get(6));
                save1.add(save);
            }
        }

        SQLiteDatabase db= pf.getWritableDatabase();
        String query = "select * from profile";
        Cursor c1 = db.rawQuery(query, null);

        if (c1!=null && c1.getCount() > 0) {
            if (c1.moveToFirst()) {
                name= c1.getString(0);
                mobile= c1.getString(1);
                System.out.println("From CategoricalData c1.getString(0): " + c1.getString(0) + " c1.getString(1): " + c1.getString(1));
            }
        }

        LinearLayoutManager layoutManager= new LinearLayoutManager(CategoricalData.this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        categoricalDataAdapter = new CategoricalDataAdapter(save1, name, mobile, CategoricalData.this);

        recyclerView.setAdapter(categoricalDataAdapter);
    }
}