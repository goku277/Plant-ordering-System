package com.goku277.plantorder.Components;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.goku277.plantorder.R;

public class AboutUs extends AppCompatActivity {

    TextView aboutus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        aboutus= (TextView) findViewById(R.id.about_us);

        String message="Hello this Simi Roy I am a freelancer recently started with a new idea about selling plants and other plant related merchandise to the people through online mode. Ofcourse the delivery of the product will be totally offline but the payments can be made easily via online mode." +
                "Through this application customer can easily browse through a wide category of plants and other plant related merchandise.";

        aboutus.setText(message);
    }
}