package com.example.awizom.glassind.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.awizom.glassind.Adapters.OrderAdapter;
import com.example.awizom.glassind.R;
import com.example.awizom.glassind.TrackOrderActivity;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class Fragment_Drawing  extends android.support.v4.app.Fragment {
    private static final int PICK_IMAGE_REQUEST = 234;
    private OrderAdapter orderAdapter;
    //Buttons
    private Button buttonChoose;
    private Button buttonUpload;

    //ImageView
    private ImageView imageView;

    //a Uri object to store file path
    private Uri filePath;
    private  String filename="";
    View view;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       view = inflater.inflate(R.layout.fragment_drawing, container, false);

        buttonChoose = (Button)view. findViewById(R.id.buttonChoose);
        buttonUpload = (Button)view.  findViewById(R.id.buttonUpload);

        imageView = (ImageView) view.findViewById(R.id.imageView);

        //attaching listener
        buttonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                startActivityForResult(intent, PICK_IMAGE_REQUEST );
            }
        });
        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

       return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
