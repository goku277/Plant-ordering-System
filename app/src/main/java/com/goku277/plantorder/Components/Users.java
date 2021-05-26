package com.goku277.plantorder.Components;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.goku277.plantorder.Adapter.AllSeasonAdapter;
import com.goku277.plantorder.Adapter.SummerAdapter;
import com.goku277.plantorder.Credentials.Signin;
import com.goku277.plantorder.Database.Profiledb;
import com.goku277.plantorder.ProcessingUnit.GetProductData;
import com.goku277.plantorder.ProcessingUnit.seedsandfertilizerdata;
import com.goku277.plantorder.ProductModel.SaveAllSeasonPlantsData;
import com.goku277.plantorder.ProductModel.SaveSummerPlantsData;
import com.goku277.plantorder.ProductModel.SetProductDetails;
import com.goku277.plantorder.ProductModel.SetSoilAndFertilizerData;
import com.goku277.plantorder.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class Users extends AppCompatActivity implements View.OnClickListener {

    private static final int PROXIMITY_RADIUS = 25;
    private static final String TAG = "MainActivity";

    ImageView plants, seeds, winterplants, gardentools, medicinalplants, vegetable ;
    TextView plants_text, seeds_text, winter_plants_text, gardening_tools_text, medicinal_plants_text, vegetable_text;

    FirebaseStorage storage;
    StorageReference storageReference;

    FirebaseDatabase database1;
    DatabaseReference ref1;

    String uriPath= "";

    Profiledb pf;

    private static final int CONTACT_PERMISSION_CODE= 1;
    private static final int CONTACT_PICK_CODE= 2;

    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;

    ArrayList<String> getProductDetails= new ArrayList<>();

    ArrayList<String> getSeedsProductDetails= new ArrayList<>();

    GetProductData getProductData;

    RecyclerView recyclerView;

    RecyclerView recyclerView1;

    SaveSummerPlantsData save;

    SaveAllSeasonPlantsData saveAll;

    ArrayList<SaveAllSeasonPlantsData> saveList1= new ArrayList<>();

    ArrayList<SaveSummerPlantsData> saveList= new ArrayList<>();

    SummerAdapter summerAdapter;

    AllSeasonAdapter allSeasonAdapter;

    ArrayList<String> imageUrlList= new ArrayList<>();

    Set<Set<String>> setOfSets= new LinkedHashSet<>();

    Set<Set<String>> setOfSets1= new LinkedHashSet<>();

    Map<String, Set<Set<String>>> a1;

    BottomNavigationView bottomNavigationView;

    com.goku277.plantorder.ProcessingUnit.seedsandfertilizerdata seedsandfertilizerdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        seedsandfertilizerdata= new seedsandfertilizerdata();
        FirebaseApp.initializeApp(this);
        getProductData= new GetProductData();
        storage= FirebaseStorage.getInstance();
        storageReference= storage.getReference();
        pf= new Profiledb(Users.this);
        recyclerView= (RecyclerView) findViewById(R.id.summer_recycler_id);
        recyclerView1= (RecyclerView) findViewById(R.id.all_season_recycler_id);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        nav=(NavigationView)findViewById(R.id.navmenu);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer);
        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getPlantDetails();

        database1= FirebaseDatabase.getInstance();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.home:
                        Toast.makeText(Users.this, "Home", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.cart:
                        Toast.makeText(Users.this, "Cart", Toast.LENGTH_SHORT).show();
                       // openFragment(new TrendFragment());
                        return true;

                    case R.id.category:
                        Toast.makeText(Users.this, "Category", Toast.LENGTH_SHORT).show();
                      //  openFragment(new AccountFragment());
                        return true;

                    case R.id.profile:
                        Toast.makeText(Users.this, "Profile", Toast.LENGTH_SHORT).show();
                      //  openFragment(new SettingsFragment());
                        startActivity(new Intent(Users.this, com.goku277.plantorder.Components.Profile.class));
                        return true;
                }
                return false;
            }
        });

      //  populateSoilandFertilizer();

        plants= (ImageView) findViewById(R.id.plants_id);
        plants_text= (TextView) findViewById(R.id.plants_text_id);

        seeds= (ImageView) findViewById(R.id.seeds_id);
        seeds_text= (TextView) findViewById(R.id.seeds_text_id);

        winterplants= (ImageView) findViewById(R.id.winter_id);
        winter_plants_text= (TextView) findViewById(R.id.winter_text_id);
        gardentools= (ImageView) findViewById(R.id.gardening_id);
        gardening_tools_text= (TextView) findViewById(R.id.gardening_text_id);

        medicinalplants= (ImageView) findViewById(R.id.medicinal_id);
        medicinal_plants_text= (TextView) findViewById(R.id.medicinal_text_id);

        vegetable= (ImageView) findViewById(R.id.vegetable_id);
        vegetable_text= (TextView) findViewById(R.id.vegetable_text_id);

        plants.setOnClickListener(this);
        plants_text.setOnClickListener(this);

        seeds.setOnClickListener(this);
        seeds_text.setOnClickListener(this);

        winterplants.setOnClickListener(this);
        winter_plants_text.setOnClickListener(this);

        gardentools.setOnClickListener(this);
        gardening_tools_text.setOnClickListener(this);

        medicinalplants.setOnClickListener(this);
        medicinal_plants_text.setOnClickListener(this);

        vegetable.setOnClickListener(this);
        vegetable_text.setOnClickListener(this);

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                switch (menuItem.getItemId())
                {
                    case R.id.menu_home :
                        Toast.makeText(getApplicationContext(),"Clicked on Home",Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                        case R.id.menu_plants:
                        populatePlants();
                        Toast.makeText(getApplicationContext(),"Clicked on plants",Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.soil_and_fertilizers_id:
                        System.out.println("Clicked on Soil And Fertilizers");
                        populateSoilandFertilizer();
                        Toast.makeText(getApplicationContext(),"Clicked on Soil And Fertilizers",Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_seeds :
                        populateSeeds();
                        Toast.makeText(getApplicationContext(),"Clicked on seeds",Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_medicinalplants:
                        populateMedicinal();
                        Toast.makeText(getApplicationContext(),"Clicked on medicinal plants",Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_winterplants :
                        populateWinter();
                        Toast.makeText(getApplicationContext(),"Clicked on Winter plants",Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_vegetable :
                        populateVegatable();
                        Toast.makeText(getApplicationContext(),"Clicked on All Vegetable",Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_myorders :
                        Toast.makeText(getApplicationContext(),"Clicked on My orders",Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_contactus :
                        Toast.makeText(getApplicationContext(),"Clicked on Contact Us",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Users.this, ContactUs.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_aboutus :
                        Toast.makeText(getApplicationContext(),"Clicked on About Us",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Users.this, AboutUs.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_pricing:
                        Toast.makeText(Users.this, "Clicked on Pricing", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Users.this, Pricing.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_logout :
                        Toast.makeText(getApplicationContext(),"Clicked on Logout",Toast.LENGTH_LONG).show();
                        logout();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,Menu.FIRST,Menu.NONE,"Logout");
        menu.add(1, Menu.FIRST+1, Menu.NONE, "Profile");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case Menu.FIRST:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Signin.class));
                finish();
                break;
            case Menu.FIRST+1:
                startActivity(new Intent(Users.this, com.goku277.plantorder.Components.Profile.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void cart() {

    }

    private void profile() {

    }

    private void getPlantDetails() {
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

                        String val1= "";

                        for (String s: getProductDetails) {
                            val1+= s + " ";
                        }

                        System.out.println("From Users getPlanDetails() val1: " + val1);

                        a1= getProductData.init(val1);

                        System.out.println("From Users getPlanDetails() a1: " + a1);

                        for (Map.Entry<String, Set<Set<String>>> e1: a1.entrySet()) {
                            if (e1.getKey().equals("Summer Plants")) {
                                setHorizontalRecyclerView1(e1.getValue());
                            }
                            if (e1.getKey().equals("All Season Plants")) {
                                setHorizontalRecyclerView11(e1.getValue());
                            }
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setHorizontalRecyclerView11(Set<Set<String>> value) {
        System.out.println("From Users setHorizontalRecyclerView11 value is: " + value);
        String name= "", urlImage="";
        ArrayList<String> urlImageList= new ArrayList<>();
        for (Set<String> set1: value) {
            for (String s1: set1) {
                if (s1.contains("imageUri:")) {
                    urlImage= s1.replace("imageUri:","").trim();
                    if (!imageUrlList.contains(urlImage)) {
                        imageUrlList.add(urlImage);
                        saveAll = new SaveAllSeasonPlantsData(urlImage);
                        saveList1.add(saveAll);
                    }
                }
            }
            setOfSets1.add(set1);
            LinearLayoutManager layoutManager= new LinearLayoutManager(Users.this, LinearLayoutManager.HORIZONTAL, false);

            recyclerView1.setLayoutManager(layoutManager);

            recyclerView1.setItemAnimator(new DefaultItemAnimator());

            System.out.println("From SaveSummerPlantsData setHorizontalRecyclerView() saveList is: " + saveList1);

            allSeasonAdapter = new AllSeasonAdapter(Users.this, saveList1, setOfSets1);

            recyclerView1.setAdapter(allSeasonAdapter);
        }

        System.out.println("From Users SetHorizontalRecyclerView1() setOfSets: " + setOfSets1);

        System.out.println("From Users SetHorizontalRecyclerView1() imageUrlList is: " + imageUrlList);
    }

    private void setHorizontalRecyclerView1(Set<Set<String>> value) {
        System.out.println("From Users setHorizontalRecyclerView value is: " + value);
        String name= "", urlImage="";
        ArrayList<String> urlImageList= new ArrayList<>();
        for (Set<String> set1: value) {
            for (String s1: set1) {
                if (s1.contains("imageUri:")) {
                    urlImage= s1.replace("imageUri:","").trim();
                    if (!imageUrlList.contains(urlImage)) {
                        imageUrlList.add(urlImage);
                        save = new SaveSummerPlantsData(urlImage);
                        saveList.add(save);
                    }
                }
            }
            setOfSets.add(set1);
            LinearLayoutManager layoutManager= new LinearLayoutManager(Users.this, LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(layoutManager);

            recyclerView.setItemAnimator(new DefaultItemAnimator());

            System.out.println("From SaveSummerPlantsData setHorizontalRecyclerView() saveList is: " + saveList);

            summerAdapter= new SummerAdapter(Users.this, saveList, setOfSets);

            recyclerView.setAdapter(summerAdapter);
        }

        System.out.println("From Users SetHorizontalRecyclerView1() setOfSets: " + setOfSets);

        System.out.println("From Users SetHorizontalRecyclerView1() imageUrlList is: " + imageUrlList);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Signin.class));
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.plants_id:
                populatePlants();
                break;
                case R.id.plants_text_id:
                    populatePlants();
                    break;
                case R.id.seeds_id:
                    populateSeeds();
                    break;
                case R.id.soil_and_fertilizer_id:
                    populateSoilandFertilizer();
                    break;
                case R.id.soil_and_fertilizer_text_id:
                    populateSoilandFertilizer();
                    break;
                case R.id.seeds_text_id:
                    populateSeeds();
                    break;
                case R.id.winter_id:
                    populateWinter();
                    break;
                case R.id.winter_text_id:
                    populateWinter();
                    break;
                case R.id.gardening_id:
                    populateGarden();
                    break;
                case R.id.gardening_text_id:
                    populateGarden();
                    break;
                case R.id.medicinal_id:
                    populateMedicinal();
                    break;
                case R.id.medicinal_text_id:
                    populateMedicinal();
                    break;
                case R.id.vegetable_id:
                    populateVegatable();
                    break;
                case R.id.vegetable_text_id:
                    populateVegatable();
                    break;
        }
    }

    private void populateVegatable() {
        try {
            for (Map.Entry<String, Set<Set<String>>> e1 : a1.entrySet()) {
                if (e1.getKey().equals("Vegetable")) {
                    setVerticalRecyclerView(e1.getValue());
                    break;
                }
            }
        } catch (Exception e){}
    }

    private void populateSoilandFertilizer() {
        try {
            // Need to adjust this code... by retrieving SoilAndFertilizerProducts from the firebase database... To be continued on tomorrow...

            Toast.makeText(this, "Visited populateSoilAndFertilizer() ", Toast.LENGTH_SHORT).show();

            System.out.println("Visited populateSoilAndFertilizer()");

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("SoilAndFertilizerProducts");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        HashMap<String, Object> dataMap = (HashMap<String, Object>) snapshot.getValue();

                        for (String key : dataMap.keySet()) {

                            Object data = dataMap.get(key);

                            try {
                                HashMap<String, Object> userData = (HashMap<String, Object>) data;         // cQualification

                                SetSoilAndFertilizerData getProduct = new SetSoilAndFertilizerData((String) userData.get("name"), (String) userData.get("price"), (String) userData.get("imageUrl"), (String) userData.get("seedsQty"), (String) userData.get("id"), (String) userData.get("category"), (String) userData.get("details"), (String) userData.get("quantity"));
                                getSeedsProductDetails.add(getProduct.getName());
                                getSeedsProductDetails.add(getProduct.getPrice());
                                getSeedsProductDetails.add(getProduct.getSeedsQty());
                                getSeedsProductDetails.add(getProduct.getDetails());
                                getSeedsProductDetails.add(getProduct.getCategory());
                                getSeedsProductDetails.add(getProduct.getId());
                                getSeedsProductDetails.add(getProduct.getImageUrl());
                                getSeedsProductDetails.add(getProduct.getQuantity());

                            } catch (ClassCastException cce) {
                                try {
                                    String mString = String.valueOf(dataMap.get(key));
                                    System.out.println("mString " + mString);
                                } catch (ClassCastException cce2) {
                                }
                            }

                            System.out.println("From users getPlanDetails() getProductDetails: " + getSeedsProductDetails);

                            String val1= "";

                            for (String s: getSeedsProductDetails) {
                                val1+= s + " ";
                            }

                            System.out.println("From Users getPlanDetails() val1: " + val1);

                            Set<Set<String>> a1= seedsandfertilizerdata.init(val1);

                            System.out.println("From populateSoilandFertilizer() a1 is: " + a1);

                            String val11= "";

                            for (Set<String> set1: a1) {
                                for (String s1: set1) {
                                    val11+= s1 + " ";
                                }
                                val11 += " * ";
                            }

                            Intent sendData= new Intent(Users.this, SeedsAndFertilizers.class);

                            sendData.putExtra("val11", val11);

                            startActivity(sendData);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            for (Map.Entry<String, Set<Set<String>>> e1 : a1.entrySet()) {
                System.out.println("e1.getKey(): " + e1.getKey());
                if (e1.getKey().contains("Soil And Fertilizer")) {
                    setVerticalRecyclerView(e1.getValue());
                    break;
                }
            }
        } catch (Exception e){}
    }

    private void populateMedicinal() {
        try {
            for (Map.Entry<String, Set<Set<String>>> e1 : a1.entrySet()) {
                if (e1.getKey().equals("Medicinal Plants")) {
                    setVerticalRecyclerView(e1.getValue());
                    break;
                }
            }
        } catch (Exception e){}
    }

    private void populateGarden() {
        try {
            for (Map.Entry<String, Set<Set<String>>> e1 : a1.entrySet()) {
                if (e1.getKey().equals("Gardening Tools")) {
                    setVerticalRecyclerView(e1.getValue());
                    break;
                }
            }
        } catch (Exception e){}
    }

    private void populateWinter() {
        try {
            for (Map.Entry<String, Set<Set<String>>> e1 : a1.entrySet()) {
                if (e1.getKey().equals("Winter Plants")) {
                    setVerticalRecyclerView(e1.getValue());
                    break;
                }
            }
        } catch (Exception e){}
    }

    private void setVerticalRecyclerView(Set<Set<String>> value) {
        System.out.println("From User setVerticalRecyclerView value is: " + value);
        String val1="";
        for (Set<String> set1: value) {
            for (String s: set1) {
                val1+= s+ " ";
            }
        }
        System.out.println("From Users setVerticalRecyclerView() val1 is: " + val1);
        Intent sendData= new Intent(Users.this, CategoricalData.class);
        sendData.putExtra("val", val1);
        startActivity(sendData);
    }

    private void populateSeeds() {
        try {
            for (Map.Entry<String, Set<Set<String>>> e1 : a1.entrySet()) {
                if (e1.getKey().equals("Seeds")) {
                    setVerticalRecyclerView(e1.getValue());
                    break;
                }
            }
        } catch (Exception e){}
    }

    private void populatePlants() {
        try {
            // Plants
            for (Map.Entry<String, Set<Set<String>>> e1 : a1.entrySet()) {
                if (e1.getKey().equals("Plants")) {
                    System.out.println("e1.getValue(): " + e1.getValue());
                    setVerticalRecyclerView(e1.getValue());
                    break;
                }
            }
        } catch (Exception e){}
    }
}