package com.goku277.plantorder.Components;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.goku277.plantorder.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlaceOrder extends AppCompatActivity {

    String name="", price="", qty="", imageUrl="";

    TextView name1, price1, qty1, number1;

    ImageButton plus, minus;

    CircleImageView cig;

    Button order;

    long initial=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        name1= (TextView) findViewById(R.id.name_id);
        price1= (TextView) findViewById(R.id.price_id);
        qty1= (TextView) findViewById(R.id.quantity_status_id);

        number1= (TextView) findViewById(R.id.number_id);

        plus= (ImageButton) findViewById(R.id.plus_id);
        minus= (ImageButton) findViewById(R.id.minus_id);

        order= (Button) findViewById(R.id.order_id);

        cig= (CircleImageView) findViewById(R.id.cig_id);

        Intent getData= getIntent();
        name= getData.getStringExtra("name");
        price= getData.getStringExtra("price");
        qty= getData.getStringExtra("qty");
        imageUrl= getData.getStringExtra("imageurl");

        name1.setText(name);
        price1.setText(price);
        Glide.with(PlaceOrder.this).load(imageUrl.replace("imageUri:","").trim()).into(cig);

        if (initial < 0) {
           initial= 0;
            number1.setText(String.valueOf(initial));
        }

        else {
            number1.setText(String.valueOf(initial));
        }

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (initial < 0) {
                    initial=0;
                }
                initial++;
                number1.setText(String.valueOf(initial));
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (initial < 0) {
                    initial= 0;
                }
                initial--;
                number1.setText(String.valueOf(initial));
            }
        });
        System.out.println("From PlaceOrder onCreate() " + name + "\t" + price + "\t" + qty);
    }
}