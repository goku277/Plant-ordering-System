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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.goku277.plantorder.Components.Admin;
import com.goku277.plantorder.Components.Users;
import com.goku277.plantorder.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class Signin extends AppCompatActivity implements View.OnClickListener {

    EditText email, password;
    Button signIn;
    TextView goToSignUp;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth, mAuth;

    Button google;

    UserStatus us;

    private final static int RC_SIGN_IN= 123;

    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user= firebaseAuth.getCurrentUser();

        if (user!=null) {
            startActivity(new Intent(getApplicationContext(), Users.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        goToSignUp= (TextView) findViewById(R.id.create_new_one_id);

        goToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Signup.class));
            }
        });

        us= new UserStatus(Signin.this);

        createRequest();

        progressDialog= new ProgressDialog(this);

        firebaseAuth= FirebaseAuth.getInstance();

        mAuth= FirebaseAuth.getInstance();

        email= (EditText) findViewById(R.id.email_id);
        password= (EditText) findViewById(R.id.password_id);
        signIn= (Button) findViewById(R.id.signin_btn_id);
        goToSignUp= (TextView) findViewById(R.id.create_new_one_id);

        google= (Button) findViewById(R.id.signin_with_google_btn_id);
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        signIn.setOnClickListener(this);
        goToSignUp.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signin_btn_id:
                Login();
                break;
            case R.id.create_new_one_id:
                startActivity(new Intent(this, Signup.class));
                break;
        }
    }

    private void createRequest() {
        GoogleSignInOptions gso= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("863258722860-o7ml8e0kpt0rs8rdjv5bd60hdu4tlub3.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient= GoogleSignIn.getClient(this,gso);
    }

    private void signIn() {
        Intent signInIntent= mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account= task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {}
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential= GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user= firebaseAuth.getCurrentUser();
                            startActivity(new Intent(getApplicationContext(), Users.class));
                        }
                        else {
                            Toast.makeText(Signin.this, "Authentication failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void Login() {
        String getEmail= email.getText().toString().trim();
        String getPassword= password.getText().toString().trim();

        if (TextUtils.isEmpty(getEmail)) {
            email.setError("Please input your Email id!");
            return;
        }
        if (TextUtils.isEmpty(getPassword)) {
            password.setError("Please input your Password!");
            return;
        }
        if (!isValidEmail(getEmail)) {
            email.setError("Please input valid email!");
            return;
        }
        if (getPassword.length()< 6) {
            password.setError("Password length must be > 5");
        }
        if (getEmail.equals("simi@gmail.com") && getPassword.equals("Im@12345678")) {
            signInToAdmin(getEmail, getPassword);
         //   startActivity(new Intent(Signin.this, Admin.class));
         //   finishAffinity();
        }
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);

        firebaseAuth.signInWithEmailAndPassword(getEmail,getPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(Signin.this, "SignIn Successfull!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), Users.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    progressDialog.dismiss();
                    Toast.makeText(Signin.this, "SignIn not Successfull!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void signInToAdmin(final String email, final String password) {
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(Signin.this, "SignIn Successfull!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), Admin.class);
                    startActivity(intent);
                    finishAffinity();
                }
                else {
                    progressDialog.dismiss();
                    System.out.println("Signin not successfull due to: " + task.getException().getMessage());
                    if (task.getException().getMessage().equals("There is no user record corresponding to this identifier. The user may have been deleted.")) {
                        signUp(email, password);
                    }
                    else {
                        Toast.makeText(Signin.this, "SignIn not Successfull!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void signUp(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Signin.this, "Successfully registered!", Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(getApplicationContext(), Admin.class);
                    startActivity(intent);
                    finishAffinity();
                }
                else {
                    Toast.makeText(Signin.this, "SignUp Failed!", Toast.LENGTH_SHORT).show();
                    System.out.println("Task failed due to: " + task.getException());
                }
                progressDialog.dismiss();
            }
        });
    }

    private boolean isValidEmail(CharSequence email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}