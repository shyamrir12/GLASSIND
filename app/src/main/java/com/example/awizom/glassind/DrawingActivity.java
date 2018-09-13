package com.example.awizom.glassind;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.awizom.glassind.Adapters.OrderAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

public class DrawingActivity extends AppCompatActivity {
    //a constant to track the file chooser intent
    private static final int PICK_IMAGE_REQUEST = 234;
    String filename,partyname,livefilepath;
    int  pino;
    //Buttons
    private Button buttonChoose;
    private Button buttonUpload;
    private TextView textview;
    //ImageView
    private ImageView imageView;

    //a Uri object to store file path
    private Uri filePath;
    private DatabaseReference mDatabase;


    private StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);
        filename=getIntent().getStringExtra("filename");
        livefilepath=getIntent().getStringExtra("livefilepath");
        partyname=getIntent().getStringExtra("partyname");
       // pino=getIntent().getStringExtra("pino");
        pino=getIntent().getIntExtra("pino",0);
        buttonChoose =  findViewById(R.id.buttonChoose);
        buttonUpload =  findViewById(R.id.buttonUpload);
        textview=findViewById(R.id.textView);
        textview.setText("Party Name :"+partyname+" PI No :"+pino);
        imageView = findViewById(R.id.imageView);

        if (livefilepath.trim().length()==0)
        {
            Glide.with(this).load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRA0vf_EXkL0RKmM5718bM1M7742qvMsRCEwvoLbOeiBTACc4kJYA").into(imageView);
        }
        else
        {
            Glide.with(this).load(livefilepath).into(imageView);

        }
        mDatabase = FirebaseDatabase.getInstance().getReference("orders");
        //attaching listener
        buttonChoose.setOnClickListener(new View. OnClickListener() {
            @Override
            public void onClick(View v) {
                openChooser();
            }
        });
        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
            }
        });
        storageReference =FirebaseStorage.getInstance().getReference();
    }
    public void openChooser() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture "), PICK_IMAGE_REQUEST);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            //when from camera
           // filePath = getIntent().getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void downlodeFile(){
        storageReference = FirebaseStorage.getInstance().getReference().child("images/"+filename+".jpg");
        storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                String downloadUrl = task.getResult().toString();

                mDatabase.child(filename).child("drawing").setValue(downloadUrl);
                // downloadurl will be the resulted answer
            }
        });
    }
    private void uploadFile() {
        //if there is a file to upload
        if (filePath != null) {


            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference riversRef = storageReference.child("images/"+filename+".jpg");
            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //if the upload is successfull
                            //hiding the progress dialog


                            //updating artist


                            downlodeFile();
                            progressDialog.dismiss();
                            //and displaying a success toast
                            Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying error message
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                  ;
        }
        //if there is not any file
        else {
            //you can display an error toast
        }


    }

}
