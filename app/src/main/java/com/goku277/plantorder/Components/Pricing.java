package com.goku277.plantorder.Components;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.goku277.plantorder.R;


public class Pricing extends AppCompatActivity {

    TextView price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pricing);

        price= (TextView) findViewById(R.id.pricing_id);

        String priceMsg= "Prices are ranging from products to products inclusive of all taxes.";

        price.setText(priceMsg);
    }
}