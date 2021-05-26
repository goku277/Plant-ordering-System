package com.goku277.plantorder.CustomeAlertDialog;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.goku277.plantorder.Components.Admin;
import com.goku277.plantorder.ProductModel.SetProductDetails;
import com.goku277.plantorder.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductDataInput extends DialogFragment implements AdapterView.OnItemSelectedListener {

    EditText u_name, u_email, u_address, u_contact_no;

        public static final int CAMERA_PERMISSION_CODE = 101;
        public static final int CAMERA_REQUEST_CODE = 102;
        public static final int READ_EXTERNAL_STORAGE_PERMISSION = 1;
        public static final int IMAGE_PICK_CODE = 2;

        public static final int REQUEST_CODE_PERMISSION_RESULT = 5;

        companyadminlistener listener;

        EditText name, price, details, quantity;
        Button click;
        ImageView cig;

        Uri imageUri;

        Spinner spin;

        ArrayList<String> categories= new ArrayList<>();

        String selectedCategory= "";

        private StorageReference mStorageReference;
        private DatabaseReference mDatabaseReference;

        FirebaseDatabase database;

        FirebaseStorage storage;
        StorageReference storageReference;

        SetProductDetails setProductDetails;

        String uri1="";

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog.Builder profileDialog = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.product_details_input_layout, null);
            profileDialog.setView(view)
                    .setTitle("Set Product Details")
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    })
                    .setPositiveButton("Add Product", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String Name = name.getText().toString().trim();
                            String price1 = price.getText().toString().trim();
                            String details1 = details.getText().toString().trim();
                            String uriPath= imageUri + "";
                            if (Name.trim().length()==0 || price1.trim().length()==0 || details1.trim().length()==0 || uriPath.trim().length()==0 || selectedCategory.trim().length()==0 || quantity.getText().toString().trim().length()==0) {
                                Toast.makeText(getActivity(), "Please enter all the fields", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(getActivity(), "Product data uploaded successfully", Toast.LENGTH_SHORT).show();
                                uploadFile(Name, price1, details1, selectedCategory, imageUri);
                                listener.companyadminfields(Name, price1, details1, uriPath, selectedCategory, imageUri, quantity.getText().toString().trim());
                            }
                        }
                    });

            storage= FirebaseStorage.getInstance();
            storageReference= storage.getReference();
            mStorageReference= storage.getReference();

            setProductDetails= new SetProductDetails();


            name= (EditText) view.findViewById(R.id.name_id);
            price= (EditText) view.findViewById(R.id.price_id);
            details= (EditText) view.findViewById(R.id.details_id);

            spin= (Spinner) view.findViewById(R.id.category_chooser_id);

            quantity= (EditText) view.findViewById(R.id.quantity_id);

            categories.add(0, "Please choose any product items");

            categories.add(1,"Plants");
            categories.add(2,"Seeds");
            categories.add(3,"Summer Plants");
            categories.add(4,"Winter Plants");
            categories.add(5,"All Season Plants");
            categories.add(6,"Gardening Tools");
            categories.add(7,"Medicinal Plants");
            categories.add(8,"Vegetable");
            categories.add(9,"Soil And Fertilizer");

            spin.setOnItemSelectedListener(this);

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);

            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spin.setAdapter(dataAdapter);


            click= (Button) view.findViewById(R.id.click_photo_btn_id);
            cig= (ImageView) view.findViewById(R.id.photo_id);

            click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder photo = new AlertDialog.Builder(getActivity());
                    photo.setTitle("Use appropriate actions");
                    photo.setMessage("Upload or Click your profile photo!\n\n");
                    photo.setPositiveButton("Click photo", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                askCameraPermission();
                            }
                        }
                    });
                    photo.setNeutralButton("Upload photo", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_PERMISSION);
                                } else {
                                    pickFromGallery();
                                }
                            } else {
                                pickFromGallery();
                            }
                        }
                    });
                    AlertDialog a1 = photo.create();
                    a1.show();
                }
            });

            return profileDialog.create();
        }

        private void askCameraPermission() {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERMISSION_CODE);
            }
            else {
                openCamera();
            }
        }

        private void openCamera() {
            ContentValues cv= new ContentValues();
            cv.put(MediaStore.Images.Media.TITLE, "NEW PICTURE");
            cv.put(MediaStore.Images.Media.DESCRIPTION, "FROM THE CAMERA");
            imageUri= getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cv);
            Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA_REQUEST_CODE);
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            if (requestCode==CAMERA_PERMISSION_CODE) {
                if (grantResults.length > 0) {
                    if (grantResults[0]== PackageManager.PERMISSION_GRANTED) {
                        openCamera();
                    }
                    else {
                        Toast.makeText(getActivity(), "Permission required to click photo!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            if (requestCode == READ_EXTERNAL_STORAGE_PERMISSION) {
                if (grantResults.length > 0) {
                    if (grantResults[0]== PackageManager.PERMISSION_GRANTED) {
                        pickFromGallery();
                    }
                    else {
                        Toast.makeText(getActivity(), "Permission required to upload photo!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

        private void pickFromGallery() {
            Intent intent= new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, IMAGE_PICK_CODE);
        }

        private void uploadFile(final String name1, final String price, final String detail, final String selectedCategories, final Uri imageUri) {
            if (imageUri!=null) {

                System.out.println("From uploadFile name1 is: " + name1 + " price: " + price + " detail: " + detail + " selectedCategories: " + selectedCategories + "imageUri: " + imageUri);

                final StorageReference ref= mStorageReference.child(selectedCategories + " " + name1 + " " + UUID.randomUUID().toString());

                ref.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                //  Toast.makeText(getActivity(), "Uploaded Image url: " + uri + "", Toast.LENGTH_SHORT).show();
                                uri1 = uri + "";
                                System.out.println("uriPath is: " + uri1);
                                uploadTextData(name1, price, detail, selectedCategories, imageUri, uri1, quantity.getText().toString().trim());
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Failed to upload image", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

        private void uploadTextData(String name1, String price, String detail, String selectedCategories, Uri imageUri, String uri1, String quantity) {
            setProductDetails.setName("name: "+ name1);
            setProductDetails.setCategory("category: " + selectedCategories);
            setProductDetails.setDetails("detail: " + detail);
            setProductDetails.setImageUrl("imageUri: " + uri1);
            String key= selectedCategories + " " + name1 + " " + UUID.randomUUID().toString();
            setProductDetails.setId("key: " + key);
            setProductDetails.setPrice("price: " + price);
            setProductDetails.setQuantity("quantity: " + quantity+"");

            mDatabaseReference= database.getInstance().getReference().child("Products");

            mDatabaseReference.child(key).setValue(setProductDetails);

            //  Toast.makeText(getContext(), "Product details uploaded successfully", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode==getActivity().RESULT_OK) {
                if (requestCode == CAMERA_REQUEST_CODE) {
                    Bitmap image = (Bitmap) data.getExtras().get("data");
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    image.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                    String path = "";
                    path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), image, "IMG_" + Calendar.getInstance().getTime(), null);
                    Toast.makeText(getActivity(), "path is: " + path, Toast.LENGTH_SHORT).show();
                    imageUri= Uri.parse(path);
                    imageUri = data.getData();
                    cig.setImageURI(imageUri);
                }
            }
            if (requestCode== IMAGE_PICK_CODE) {
                try {
                    cig.setImageURI(data.getData());
                    imageUri = data.getData();
                    cig.setImageURI(imageUri);
                } catch (Exception e){}
            }
        }

        @Override
        public void onAttach(@NonNull Context context) {
            super.onAttach(context);
            try {
                listener = (companyadminlistener) context;
            } catch (ClassCastException e) {
                throw new ClassCastException(context.toString() + " Must implement this Doctor_profile_Listener");
            }
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (!categories.get(position).equals("Please choose any product items")) {
                selectedCategory= categories.get(position);
            }
            else {
                Toast.makeText(getActivity(), "Please choose any valid category", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }

        public interface companyadminlistener {
            public void companyadminfields(String name1, String price, String detail, String uriPath, String selectedCategories, Uri imageUri, String quantity);
        }
}