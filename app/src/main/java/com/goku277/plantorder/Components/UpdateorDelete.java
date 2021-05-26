package com.goku277.plantorder.Components;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.goku277.plantorder.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class UpdateorDelete extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Map<String, Set<Set<String>>> mapIdToDetails= new LinkedHashMap<>();
    ArrayList<String> keys= new ArrayList<>();

    ArrayList<String> list = new ArrayList<String>();

    /** Declaring an ArrayAdapter to set items to ListView */
    ArrayAdapter<String> adapter;

    Spinner spin;

    String selectedId= "";

    boolean isSelected= false;

    Button goback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateor_delete);
        goback= (Button) findViewById(R.id.goback_id);
        Intent getData= getIntent();
        mapIdToDetails= (Map) getData.getSerializableExtra("map");
        System.out.println("From UpdateorDelete() mapIdToDetails: " + mapIdToDetails);

        for (Map.Entry<String, Set<Set<String>>> e1: mapIdToDetails.entrySet()) {
            keys.add(e1.getKey());
        }
        System.out.println("keys: " + keys);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);

        spin= (Spinner) findViewById(R.id.spin_id);

        spin.setOnItemSelectedListener(this);

        keys.add(0, "Select any ids to update");

        Spinner s = (Spinner) findViewById(R.id.spin_id);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, keys);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSelected) {
                    AlertDialog.Builder a11 = new AlertDialog.Builder(UpdateorDelete.this);
                    a11.setTitle("Go back");
                    a11.setMessage("Go back and update the specific product with respect to this particular id");
                    a11.setPositiveButton("Back", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent sendData = new Intent(UpdateorDelete.this, Admin.class);
                            sendData.putExtra("selectedid", selectedId);
                            startActivity(sendData);
                        }
                    });

                    AlertDialog a1 = a11.create();
                    a1.show();
                }
                else {
                    Toast.makeText(UpdateorDelete.this, "Please select an Id from the menu then try again", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

  /*  @Override
    protected void onStart() {
        super.onStart();

        if (isSelected) {

            AlertDialog.Builder a11 = new AlertDialog.Builder(UpdateorDelete.this);
            a11.setTitle("Go back");
            a11.setMessage("Go back and update the specific product with respect to this particular id");
            a11.setPositiveButton("Back", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent sendData = new Intent(UpdateorDelete.this, Admin.class);
                    sendData.putExtra("selectedid", selectedId);
                    startActivity(sendData);
                }
            });

            AlertDialog a1 = a11.create();
            a1.show();
        }
    }    */

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedId= keys.get(position);
        System.out.println("Selected id from the spinner is: " + selectedId);
        isSelected= true;

       /* if (isSelected) {
            AlertDialog.Builder a11 = new AlertDialog.Builder(UpdateorDelete.this);
            a11.setTitle("Go back");
            a11.setMessage("Go back and update the specific product with respect to this particular id");
            a11.setPositiveButton("Back", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent sendData = new Intent(UpdateorDelete.this, Admin.class);
                    sendData.putExtra("selectedid", selectedId);
                    startActivity(sendData);
                }
            });

            AlertDialog a1 = a11.create();
            a1.show();
        }   */
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}