package com.goku277.plantorder.Components;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.goku277.plantorder.Credentials.Signin;
import com.goku277.plantorder.CustomeAlertDialog.ProductDataInput;
import com.goku277.plantorder.CustomeAlertDialog.SeddsAndFertilizersDialog;
import com.goku277.plantorder.Database.Profiledb;
import com.goku277.plantorder.ProcessingUnit.ExtractId;
import com.goku277.plantorder.ProcessingUnit.GetProductData;
import com.goku277.plantorder.ProductModel.SetProductDetails;
import com.goku277.plantorder.R;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.UploadTask;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class Admin extends AppCompatActivity implements View.OnClickListener, ProductDataInput.companyadminlistener, SeddsAndFertilizersDialog.SoilFertilizerLisener {

    private static final int PROXIMITY_RADIUS = 25;
    private static final String TAG = "MainActivity";
    ImageView plant, seeds, summerplants, winterplants, allseason, gardenigtools, medicinal, vegatable, soilAndFertilizer, logout, update, delete;

    TextView plant_text, seeds_text, summer_text, winterplants_text, allseason_text, gardeningtools_text, medicinal_text, vegetable_text, soilAndFertilizer_text, logout_text,
    update_text, delete_text;

    FirebaseStorage storage;
    StorageReference storageReference;

    String uriPath= "";

    Profiledb pf;

    boolean deleted= false, updated= false;

    private static final int CONTACT_PERMISSION_CODE= 1;
    private static final int CONTACT_PICK_CODE= 2;

    private StorageReference mStorageReference;
    private DatabaseReference mDatabaseReference;

    FirebaseDatabase database;

    String uri1="";

    SetProductDetails setProductDetails;

    GetProductData getProductData;

    ArrayList<String> getProductDetails= new ArrayList<>();

    ExtractId extractId;

    Map<String, Set<Set<String>>> mapIdToDetails= new LinkedHashMap<>();

    String receivedId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        FirebaseApp.initializeApp(this);

        extractId= new ExtractId();

        storage= FirebaseStorage.getInstance();
        storageReference= storage.getReference();

        setProductDetails= new SetProductDetails();

        pf= new Profiledb(Admin.this);

        plant= (ImageView) findViewById(R.id.plants_id);
        plant_text= (TextView) findViewById(R.id.plants_text_id);

        logout= (ImageView) findViewById(R.id.logout);
        logout_text= (TextView) findViewById(R.id.logout_id);

        seeds= (ImageView) findViewById(R.id.seeds_id);
        seeds_text= (TextView) findViewById(R.id.seeds_text_id);

        summerplants= (ImageView) findViewById(R.id.summer_plants_id);
        summer_text= (TextView) findViewById(R.id.summer_plants_text_id);

        winterplants= (ImageView) findViewById(R.id.winter_plants_id);
        winterplants_text= (TextView) findViewById(R.id.winter_plants_text_id);

        allseason= (ImageView) findViewById(R.id.all_seasonal_plants_id);
        allseason_text= (TextView) findViewById(R.id.all_seasonal_plants_text_id);

        gardenigtools= (ImageView) findViewById(R.id.tools_id);
        gardeningtools_text= (TextView) findViewById(R.id.tools_text_id);

        medicinal= (ImageView) findViewById(R.id.medicinal_plants_id);
        medicinal_text= (TextView) findViewById(R.id.medicinal_plants_text_id);

        vegatable= (ImageView) findViewById(R.id.vegetable_id);
        vegetable_text= (TextView) findViewById(R.id.vegetable_text_id);

        soilAndFertilizer= (ImageView) findViewById(R.id.soil_and_fertilizer_id);
        soilAndFertilizer_text= (TextView) findViewById(R.id.soil_and_fertilizer_text_id);

        update= (ImageView) findViewById(R.id.update_id);
        update_text= (TextView) findViewById(R.id.update_products_id);

        delete= (ImageView) findViewById(R.id.delete_product_id);
        delete_text= (TextView) findViewById(R.id.delete_product_text_id);

        plant.setOnClickListener(this);
        plant_text.setOnClickListener(this);

        seeds.setOnClickListener(this);
        seeds_text.setOnClickListener(this);

        summerplants.setOnClickListener(this);
        summer_text.setOnClickListener(this);

        winterplants.setOnClickListener(this);
        winterplants_text.setOnClickListener(this);

        allseason.setOnClickListener(this);
        allseason_text.setOnClickListener(this);

        gardenigtools.setOnClickListener(this);
        gardeningtools_text.setOnClickListener(this);

        medicinal.setOnClickListener(this);
        medicinal_text.setOnClickListener(this);

        vegatable.setOnClickListener(this);
        vegetable_text.setOnClickListener(this);

        soilAndFertilizer.setOnClickListener(this);
        soilAndFertilizer_text.setOnClickListener(this);

        logout.setOnClickListener(this);
        logout_text.setOnClickListener(this);

        update.setOnClickListener(this);
        update_text.setOnClickListener(this);

        delete.setOnClickListener(this);
        delete_text.setOnClickListener(this);

        Intent getData= getIntent();

        receivedId= getData.getStringExtra("selectedid");

        System.out.println("From Admin receivedId is: " + receivedId);

        if (receivedId!=null) {
            AlertDialog.Builder a11= new AlertDialog.Builder(Admin.this);
            a11.setTitle("Delete or Update product details");
            a11.setMessage("You can either delete or update product information");
            a11.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Products").child(receivedId);
                    ref.removeValue();
                    Toast.makeText(Admin.this, "Data successfully removed!", Toast.LENGTH_SHORT).show();
                }
            });

            a11.setNegativeButton("Update", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Products").child(receivedId);
                    ref.removeValue();
                 //   Toast.makeText(Admin.this, "Data successfully removed!", Toast.LENGTH_SHORT).show();
                    openProductsDialog();

                }
            });

            a11.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            AlertDialog a1= a11.create();
            a1.show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.plants_id:
                openProductsDialog();
                break;
            case R.id.plants_text_id:
                openProductsDialog();
                break;
            case R.id.seeds_id:
                openProductsDialog();
                break;
            case R.id.seeds_text_id:
                openProductsDialog();
                break;
            case R.id.summer_plants_id:
                openProductsDialog();
                break;
            case R.id.summer_plants_text_id:
                openProductsDialog();
                break;
            case R.id.winter_plants_id:
                openProductsDialog();
                break;
            case R.id.winter_plants_text_id:
                openProductsDialog();
                break;
            case R.id.all_seasonal_plants_id:
                openProductsDialog();
                break;
            case R.id.all_seasonal_plants_text_id:
                openProductsDialog();
                break;
            case R.id.medicinal_plants_id:
                openProductsDialog();
                break;
            case R.id.medicinal_plants_text_id:
                openProductsDialog();
                break;
            case R.id.vegetable_id:
                openProductsDialog();
                break;
            case R.id.vegetable_text_id:
                openProductsDialog();
                break;
            case R.id.tools_id:
                openProductsDialog();
                break;
            case R.id.tools_text_id:
                openProductsDialog();
                break;
            case R.id.soil_and_fertilizer_id:
                openSeedsDialog();
                break;
            case R.id.soil_and_fertilizer_text_id:
                openSeedsDialog();
                break;
            case R.id.logout_id:
                Logout();
                break;
            case R.id.logout:
                Logout();
                break;
            case R.id.update_id:
                updated= true;
                update();
                break;
            case R.id.update_products_id:
                updated= true;
                update();
                break;
            case R.id.delete_product_id:
                deleted= true;
                delete();
                break;
            case R.id.delete_product_text_id:
                deleted= true;
                delete();
                break;

        }
    }

    private void openSeedsDialog() {
        SeddsAndFertilizersDialog sd= new SeddsAndFertilizersDialog();
        sd.show(getSupportFragmentManager(), "Seeds And Fertilizer");
    }

    private void delete() {
        doWork();
    }

    private void update() {
        doWork();
    }

    public void doWork() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Products");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    HashMap<String, Object> dataMap = (HashMap<String, Object>) snapshot.getValue();

                    for (String key : dataMap.keySet()) {

                        Object data = dataMap.get(key);

                        try {
                            HashMap<String, Object> userData = (HashMap<String, Object>) data;         // cQualification

                            SetProductDetails getProduct = new SetProductDetails((String) userData.get("name"), (String) userData.get("price"), (String) userData.get("details"), (String) userData.get("category"), (String) userData.get("id"), (String) userData.get("imageUrl"), (String) userData.get("quantity"));

                            getProductDetails.add(getProduct.getName());
                            getProductDetails.add(getProduct.getPrice());
                            getProductDetails.add(getProduct.getDetails());
                            getProductDetails.add(getProduct.getCategory());
                            getProductDetails.add(getProduct.getId());
                            getProductDetails.add(getProduct.getImageUrl());
                            getProductDetails.add(getProduct.getQuantity());

                        } catch (ClassCastException cce) {
                            try {
                                String mString = String.valueOf(dataMap.get(key));
                            } catch (ClassCastException cce2) {

                            }
                        }

                        System.out.println("From users getPlanDetails() getProductDetails: " + getProductDetails);

                        String val1 = "";

                        for (String s : getProductDetails) {
                            val1 += s + " ";
                        }

                        System.out.println("From Users getPlanDetails() val1: " + val1);

                        mapIdToDetails = extractId.init(val1);

                        System.out.println("mapIdToDetails: " + mapIdToDetails);

                        if (updated && !deleted) {

                            AlertDialog.Builder a11 = new AlertDialog.Builder(Admin.this);
                            a11.setTitle("Confirm Update");
                            a11.setMessage("Are you sure you want to update product details?");
                            a11.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent sendData = new Intent(Admin.this, UpdateorDelete.class);
                                    sendData.putExtra("map", (Serializable) mapIdToDetails);
                                    startActivity(sendData);
                                }
                            });
                            a11.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog a1 = a11.create();
                            a1.show();
                            updated= false;
                        }

                        else if (!updated && deleted) {
                            AlertDialog.Builder a11 = new AlertDialog.Builder(Admin.this);
                            a11.setTitle("Confirm Delete");
                            a11.setMessage("Are you sure you want to delete product details?");
                            a11.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent sendData = new Intent(Admin.this, UpdateorDelete.class);
                                    sendData.putExtra("map", (Serializable) mapIdToDetails);
                                    startActivity(sendData);
                                }
                            });
                            a11.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            AlertDialog a1 = a11.create();
                            a1.show();
                            deleted= false;
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void Logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Signin.class));
        finish();
    }

    private void openProductsDialog() {
        ProductDataInput pd= new ProductDataInput();
        pd.show(getSupportFragmentManager(), "ProductDataInput");
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
    public void companyadminfields(String name1, String price, String detail, String uriPath, String selectedCategories, Uri imageUri, String quantity) {
        System.out.println("From Admin name1 is: " + name1 + " price is: " + price + " details: " + detail
        + " uriPath is: " + uriPath + " selectedCategories is: " + selectedCategories + " Quantity: " + quantity);
    }

    @Override
    public void setSoilAndFertilizerDetails(String name1, String price, String detail, String uriPath, String selectedCategories, Uri imageUri, String quantity) {

    }
}