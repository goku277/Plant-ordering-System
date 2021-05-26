package com.goku277.plantorder.Components;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.goku277.plantorder.Database.Profiledb;
import com.goku277.plantorder.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class Profile extends AppCompatActivity implements ProfileDialog.ProfileCreateListener, ShowCreatedProfileDialog.ShowProfileCreateListener, View.OnClickListener {

    Profiledb pf;

    FirebaseStorage storage;
    StorageReference storageReference;

    String uriPath="";

    ImageView profile, check, update, delete;

    TextView profile_text, check_text, update_text, delete_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profile= (ImageView) findViewById(R.id.profile);
        profile_text= (TextView) findViewById(R.id.profile_text_id);

        check= (ImageView) findViewById(R.id.check_profile_id);
        check_text= (TextView) findViewById(R.id.check_profile_text_id);

        update= (ImageView) findViewById(R.id.update_id);
        update_text= (TextView) findViewById(R.id.update_text_id);

        delete= (ImageView) findViewById(R.id.delete_id);
        delete_text= (TextView) findViewById(R.id.delete_text_id);

        profile.setOnClickListener(this);
        profile_text.setOnClickListener(this);

        check.setOnClickListener(this);
        check_text.setOnClickListener(this);

        update.setOnClickListener(this);
        update_text.setOnClickListener(this);

        delete.setOnClickListener(this);
        delete_text.setOnClickListener(this);

        storage= FirebaseStorage.getInstance();
        storageReference= storage.getReference();

        pf= new Profiledb(Profile.this);

    }

    private void profileAlert() {
        AlertDialog.Builder a11= new AlertDialog.Builder(Profile.this);
        a11.setTitle("Profile Section");
        a11.setMessage("Choose appropriate option\n\n");
        a11.setPositiveButton("Crete Profile", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                openDialog();
            }
        });

        a11.setNegativeButton("Check Profile", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                checkProfile();
            }
        });

        a11.setNeutralButton("Update profile", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog a1= a11.create();
        a1.show();
    }

    private void checkProfile() {
        SQLiteDatabase db= pf.getWritableDatabase();

        String query = "select * from profile";
        Cursor c1 = db.rawQuery(query, null);

        if (c1.getCount() == 0) {
            Toast.makeText(this, "Create profile first!", Toast.LENGTH_SHORT).show();
        }
        else {
            if (c1!=null && c1.getCount() > 0) {
                if (c1.moveToFirst()) {
                    openShowProfileDialog();
                }
            }
        }
    }

    private void openShowProfileDialog() {
        ShowCreatedProfileDialog spd= new ShowCreatedProfileDialog();
        spd.show(getSupportFragmentManager(), "Show Created Profile");
    }

    private void openDialog() {
        ProfileDialog pd= new ProfileDialog();
        pd.show(getSupportFragmentManager(), "Profile Creation");
    }

    @Override
    public void applyShowProfileCreateFields(String name1, String mobile1, Uri imageUri) {

    }

    @Override
    public void applyProfileCreateFields(String name1, String mobile1, Uri imageUri) {
        System.out.println("From MainActivity applyProfileCreateFields(): " + name1 + " " + mobile1);
        System.out.println("imageUri==null? " + imageUri==null);
        saveProfileData(name1, mobile1, imageUri);
    }



    private void saveProfileData(final String name1, final String mobile1, Uri imageUri) {
        if (imageUri != null) {
            final StorageReference ref = storageReference.child("Profile Pics/" + UUID.randomUUID().toString());

            final ProgressDialog pd = new ProgressDialog(this);
            pd.setTitle("Please wait...");
            pd.show();

            try {
                ref.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Toast.makeText(Profile.this, "Profile pic uploaded successfully", Toast.LENGTH_SHORT).show();

                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Toast.makeText(Profile.this, "Uploaded Image url: " + uri+"", Toast.LENGTH_SHORT).show();
                                uriPath= uri.getPath();
                                System.out.println("uriPath is: " + uriPath);
                                SQLiteDatabase db= pf.getWritableDatabase();
                                String query = "select * from profile";
                                Cursor c1 = db.rawQuery(query, null);
                                if (c1!= null && c1.getCount() > 0) {
                                    Toast.makeText(Profile.this, "User cannot create multiple profiles!", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    pf.insertData(name1,mobile1, uriPath.replace("uriPath is:","").trim());
                                    Toast.makeText(Profile.this, "Data successfully saved", Toast.LENGTH_SHORT).show();
                                    System.out.println("uriPath is: " + uriPath);
                                }
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(Profile.this, "Error while uploading profile pic", Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        pd.setMessage("Uploaded: " + progress + "%");
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            SQLiteDatabase db= pf.getWritableDatabase();

            String query = "select * from profile";
            Cursor c1 = db.rawQuery(query, null);
            if (c1.getCount() > 0) {
                System.out.println("c1.getCount is: " + c1.getCount());
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profile:
                openDialog();
                break;
            case R.id.profile_text_id:
                openDialog();
                break;
            case R.id.check_profile_id:
                openShowProfileDialog();
                break;
            case R.id.check_profile_text_id:
                openShowProfileDialog();
                break;
            case R.id.update_id:
                update();
                break;
            case R.id.update_text_id:
                update();
                break;
            case R.id.delete_id:
                delete();
                break;
            case R.id.delete_text_id:
                delete();
                break;
        }
    }

    private void delete() {
        SQLiteDatabase db= pf.getWritableDatabase();
        String query = "select * from profile";
        Cursor c1 = db.rawQuery(query, null);
        if (c1!=null && c1.getCount() > 0) {
            pf.delete();
        }
        else {
            Toast.makeText(this, "There is no profile data to delete", Toast.LENGTH_SHORT).show();
        }
    }

    private void update() {
        SQLiteDatabase db= pf.getWritableDatabase();
        String query = "select * from profile";
        Cursor c1 = db.rawQuery(query, null);
        if (c1!=null && c1.getCount() > 0) {
            pf.delete();
            openDialog();
        }
        else {
            Toast.makeText(this, "There is no profile data to update", Toast.LENGTH_SHORT).show();
        }
    }
}