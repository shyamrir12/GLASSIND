package com.example.awizom.glassind;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.awizom.glassind.Adapters.OrderAdapter;
import com.example.awizom.glassind.core.ImageCompressTask;
import com.example.awizom.glassind.listeners.IImageCompressTaskListener;
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
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class DrawingActivity extends AppCompatActivity {
    //a constant to track the file chooser intent
    private static final int PICK_IMAGE_REQUEST = 234;
    String filename,partyname,livefilepath;
    int  pino;
    //Buttons
    private Button buttonChoose;
    private Button buttonUpload;
    private TextView textview;
    File file;
    private ExecutorService mExecutorService = Executors.newFixedThreadPool(1);

    private ImageCompressTask imageCompressTask;
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
        getSupportActionBar().setTitle("Select Drawing File");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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
            //filePath = data.getData();
            //when from camera
           // filePath = getIntent().getData();


                Uri uri = data.getData();
                Cursor cursor = MediaStore.Images.Media.query(getContentResolver(), uri, new String[]{MediaStore.Images.Media.DATA});

                if(cursor != null && cursor.moveToFirst()) {
                    String path = cursor.getString( cursor.getColumnIndexOrThrow( MediaStore.Images.Media.DATA ) );

                    //Create ImageCompressTask and execute with Executor.
                    imageCompressTask = new ImageCompressTask( this, path, iImageCompressTaskListener );

                    mExecutorService.execute( imageCompressTask );
                   // Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                   // imageView.setImageBitmap(bitmap);
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
    //image compress task callback
    private IImageCompressTaskListener iImageCompressTaskListener = new IImageCompressTaskListener() {
        @Override
        public void onComplete(List<File> compressed) {
            //photo compressed. Yay!

            //prepare for uploads. Use an Http library like Retrofit, Volley or async-http-client (My favourite)

           file = compressed.get(0);

            Log.d("ImageCompressor", "New photo size ==> " + file.length()); //log new file size.

            imageView.setImageBitmap( BitmapFactory.decodeFile(file.getAbsolutePath()));
            filePath =Uri.fromFile(new File(file.getAbsolutePath()));
        }

        @Override
        public void onError(Throwable error) {
            //very unlikely, but it might happen on a device with extremely low storage.
            //log it, log.WhatTheFuck?, or show a dialog asking the user to delete some files....etc, etc
            Log.wtf("ImageCompressor", "Error occurred", error);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //clean up!
        mExecutorService.shutdown();

        mExecutorService = null;
        imageCompressTask = null;
    }

}
