package com.goku277.plantorder.Credentials;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;

import com.goku277.plantorder.Components.Admin;
import com.goku277.plantorder.Components.Users;
import com.goku277.plantorder.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SplashForAll extends AppCompatActivity {

    ImageView bus;
    SwitchCompat switch1, switch11;
    TextView res;
    String UserStatus="";

    UserStatus us;

    FirebaseAuth firebaseAuth;


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user= firebaseAuth.getCurrentUser();

        SQLiteDatabase db1 = us.getReadableDatabase();
        String query1 = "select * from user";
        Cursor c11 = db1.rawQuery(query1, null);

        if (c11!=null && c11.getCount() > 0) {
            if (c11.moveToFirst()) {
                do {
                    System.out.println("user is: " + c11.getString(0));
                } while (c11.moveToNext());
            }
        }

        if (user!=null) {
            SQLiteDatabase db = us.getReadableDatabase();
            String query = "select * from user";
            Cursor c1 = db.rawQuery(query, null);
            if (c1!=null && c1.getCount() > 0) {
                if (c1.moveToFirst()) {
                    String extractInfo= c1.getString(0);
                    if (extractInfo.contains("Admin!")) {
                        startActivity(new Intent(getApplicationContext(), Admin.class));
                    }
                    else if (extractInfo.contains("User!")) {
                        startActivity(new Intent(getApplicationContext(), Users.class));
                    }
                }
            }
            else {
                if (c1.getCount() == 0) {
                    startActivity(new Intent(getApplicationContext(), SplashForAll.class));
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_for_all);

        firebaseAuth= FirebaseAuth.getInstance();

        us= new UserStatus(SplashForAll.this);

        grantAllPermisions();

        bus= (ImageView) findViewById(R.id.vehicle_id);
        switch1= (SwitchCompat) findViewById(R.id.doctor_or_patient_id);
        switch11= (SwitchCompat) findViewById(R.id.patient_or_doctor_id);
        res= (TextView) findViewById(R.id.doctor_or_patient_textview_id);

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    res.setText("User is an Admin!");
                    UserStatus = "User is a Admin!";
                } else {
                    res.setText("User is a User!");
                    UserStatus = "User is an User!";
                }

                SQLiteDatabase db = us.getReadableDatabase();
                String query = "select * from user";
                Cursor c1 = db.rawQuery(query, null);

                if (c1.getCount() > 0) {
                    us.delete();
                    us.insertData(UserStatus);
                }

                else if (c1.getCount() < 1) {
                    us.insertData(UserStatus);
                }

                startActivity(new Intent(getApplicationContext(), Admin.class));
                finish();
            }
        });

        switch11.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    res.setText("User is a user!");
                    UserStatus = "User is a User!";
                } else {
                    res.setText("User is a Admin!");
                    UserStatus = "User is an Admin!";
                }

                us.insertData(UserStatus);

                startActivity(new Intent(getApplicationContext(), Users.class));
                finish();
            }
        });

        moveAnimation();

    }

     private void grantAllPermisions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(SplashForAll.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            }
        }
    }

    public void moveAnimation() {
        Animation anim= new TranslateAnimation(0,500,0,0);
        anim.setDuration(4000);
        anim.setFillAfter(true);
        bus.startAnimation(anim);
        bus.setVisibility(View.INVISIBLE);
    }
}