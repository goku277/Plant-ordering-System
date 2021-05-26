package com.goku277.plantorder.Components;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.goku277.plantorder.Database.Profiledb;
import com.goku277.plantorder.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShowCreatedProfileDialog extends DialogFragment {
    CircleImageView cig;

    TextView name, mobile;

    Uri imageUri;

    Profiledb pf;

    TextView checkLocation;

    private ShowProfileCreateListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        pf= new Profiledb(getActivity());

        AlertDialog.Builder profileDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.showcreatedprofilelayput, null);

        profileDialog.setView(view)
                .setTitle("Create profile")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String Name = name.getText().toString().trim();

                        String Mobile = mobile.getText().toString().trim();

                        listener.applyShowProfileCreateFields(Name, Mobile, imageUri);
                    }
                });

        name = (TextView) view.findViewById(R.id.input_name_id);
        //  specialization= (EditText) view.findViewById(R.id.specialization_inputfield_id);
        mobile = (TextView) view.findViewById(R.id.input_mobile_id);

     //   cig = (CircleImageView) view.findViewById(R.id.profile_pic_upload_id);

        SQLiteDatabase db= pf.getWritableDatabase();

        String query = "select * from profile";
        Cursor c1 = db.rawQuery(query, null);

        if (c1!= null && c1.getCount() > 0) {
            if (c1.moveToFirst()) {
                name.setText(c1.getString(0));
                mobile.setText(c1.getString(1));
                String path= c1.getString(2);
             //   imageUri= Uri.parse(path);
            //    cig.setImageURI(imageUri);
            }
        }
        return profileDialog.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (ShowProfileCreateListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " Must implement this Doctor_profile_Listener");
        };
    }

    public interface ShowProfileCreateListener {
        public void applyShowProfileCreateFields(String name1, String mobile1, Uri imageUri);
    }
}