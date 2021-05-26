package com.goku277.plantorder.Credentials;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.goku277.plantorder.Components.Admin;
import com.goku277.plantorder.Components.Users;
import com.goku277.plantorder.ProductModel.SaveCustomerData;
import com.goku277.plantorder.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Signup extends AppCompatActivity implements View.OnClickListener {

    EditText email, password, confirmPassword, name, location, city, phone, address, pin;
    Button SignUp;
    TextView gotoSignIn;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;

    FirebaseDatabase database, db2;
    DatabaseReference ref, childref;

    UserStatus us;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        us = new UserStatus(Signup.this);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        email = (EditText) findViewById(R.id.email_id);
        password = (EditText) findViewById(R.id.password_id);
        confirmPassword = (EditText) findViewById(R.id.cnf_password_id);
        SignUp = (Button) findViewById(R.id.signin_btn_id);
        gotoSignIn = (TextView) findViewById(R.id.create_new_one_id);

        name= (EditText) findViewById(R.id.name_id);
        location= (EditText) findViewById(R.id.location_id);
        city= (EditText) findViewById(R.id.city_id);
        phone= (EditText) findViewById(R.id.phno_id);
        address= (EditText) findViewById(R.id.address);
        pin= (EditText) findViewById(R.id.pin_id);

        SignUp.setOnClickListener(this);
        gotoSignIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signin_btn_id:
                Register();
                break;
            case R.id.create_new_one_id:
                startActivity(new Intent(this, Signin.class));
                break;
        }
    }

    private void Register() {
        String getEmail= email.getText().toString().trim();
        String getPassword= password.getText().toString().trim();
        String getConfirmPassword= confirmPassword.getText().toString().trim();

        if (TextUtils.isEmpty(getEmail)) {
            email.setError("Please input your Email id!");
            return;
        }
        if (TextUtils.isEmpty(getPassword)) {
            password.setError("Please input your Password!");
            return;
        }
        if (TextUtils.isEmpty(getConfirmPassword)) {
            confirmPassword.setError("Please input this confirm password field!");
            return;
        }
        if (!getPassword.equals(getConfirmPassword)) {
            confirmPassword.setError("Passwords donot match, please try again!");
            return;
        }
        if (!isValidEmail(getEmail)) {
            email.setError("Please input valid email!");
            return;
        }
        if (getPassword.length()< 6) {
            password.setError("Password length must be > 5");
        }
        if (getConfirmPassword.length()< 6) {
            password.setError("Incorrect password! p;ease try again");
        }
        if (!name.getText().toString().trim().trim().isEmpty() && !location.getText().toString().trim().isEmpty() &&
        !city.getText().toString().trim().isEmpty() && !phone.getText().toString().trim().isEmpty() && !address.getText().toString().trim().isEmpty() &&
        !pin.getText().toString().trim().isEmpty()) {
            uploadData();
        }

        if (getEmail.trim().equals("simiheartfilia@gmail.com") && getPassword.trim().equals("Im@12345678")) {
            Toast.makeText(this, "Hello Admin Welcome to the Plant Ordering System", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), Admin.class);
            startActivity(intent);
            finish();
        }


        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);

        firebaseAuth.createUserWithEmailAndPassword(getEmail,getPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Signup.this, "Successfully registered!", Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(getApplicationContext(), Users.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(Signup.this, "SignUp Failed!", Toast.LENGTH_SHORT).show();
                    System.out.println("Task failed due to: " + task.getException());
                }
                progressDialog.dismiss();
            }
        });
    }

    private void uploadData() {
        SaveCustomerData saveCustomerData= new SaveCustomerData();

        saveCustomerData.setAddress(address.getText().toString().trim());
        saveCustomerData.setCity(city.getText().toString().trim());
        saveCustomerData.setEmail(email.getText().toString().trim());
        saveCustomerData.setLocation(location.getText().toString().trim());
        saveCustomerData.setName(name.getText().toString().trim());
        saveCustomerData.setPassword(password.getText().toString().trim());
        saveCustomerData.setPhone(phone.getText().toString().trim());
        saveCustomerData.setPin(pin.getText().toString().trim());

        ref= database.getInstance().getReference().child("Customer");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ref.child(name.getText().toString().trim().replace(".","").replace(",","") + " " + phone.getText().toString().trim().replace(".","").replace(",","")).setValue(saveCustomerData);

        Toast.makeText(this, "Customer profile successfully created", Toast.LENGTH_SHORT).show();
;    }

    private boolean isValidEmail(CharSequence email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }
}