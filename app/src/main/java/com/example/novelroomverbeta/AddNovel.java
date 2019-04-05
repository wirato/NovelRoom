package com.example.novelroomverbeta;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;


public class AddNovel extends AppCompatActivity {

    //-----------------------------------------
    // initialize variables
    //
    String Storage_Path = "Novel_images/";
    String Database_Path = "DataBook";
    android.widget.Button  bAdd;
    android.widget.EditText edName;
    android.widget.EditText edPrice ;
    android.widget.ImageView img;
    android.net.Uri FilePathUri;

    //uri to store file
    private Uri filePath;

    //firebase objects
    private StorageReference storageReference;
    private DatabaseReference mDatabase;

    // Image request code for onActivityResult() .
    int Image_Request_Code = 7;
    android.app.ProgressDialog progressDialog ;

    /*
    ==========
     onCreate
    ==========
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_novel);

        // initital UI
        bAdd = (Button) findViewById(R.id.bAdd);
        edName = (EditText) findViewById(R.id.edName);
        img = (ImageView) findViewById(R.id.img);

        edPrice = (EditText) findViewById(R.id.edPrice);

//        mDatabase = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);

        progressDialog = new android.app.ProgressDialog(AddNovel.this);

        // ปุ่มเลือกภาพ
        img.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                android.content.Intent intent = new android.content.Intent();
                // Setting intent type as image to select image from phone storage.
                intent.setType("image/*");
                intent.setAction(android.content.Intent.ACTION_GET_CONTENT);
                startActivityForResult(android.content.Intent.createChooser(intent, "Please Select Image"), Image_Request_Code);
            }
        });

        // ปุ่มอัพโหลดภาพ
        bAdd.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                uploadFile();

            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                // Getting selected image into Bitmap.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                // Setting up bitmap selected image into ImageView.
                img.setImageBitmap(bitmap);
                // After selecting image change choose button above text.

            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    public void uploadFile() {

        //checking if file is available
        if (filePath != null) {
            //displaying progress dialog while image is uploading
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            //getting the storage reference
            final StorageReference sRef = storageReference.child(Constants.STORAGE_PATH_UPLOADS + System.currentTimeMillis() + "." + getFileExtension(filePath));

            //adding the file to reference
            sRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            //dismissing the progress dialog
                            progressDialog.dismiss();

                            //displaying success toast
                            Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();

                            //----
                            // Download file From Firebase Storage ==>> store real path at Firebase Storage
                            sRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri downloadPhotoUrl) {
                                    DataRegister name = new DataRegister();
                                    String texe = name.getUsername();
                                    DataBook food = new DataBook(edName.getText().toString().trim(),

                                            edPrice.getText().toString().trim(),texe , downloadPhotoUrl.toString());

                                    //adding an upload to firebase database
                                    String uploadId = mDatabase.push().getKey();
                                    mDatabase.child(uploadId).setValue(food);
                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //displaying the upload progress
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });

        } else {
            android.widget.Toast.makeText(AddNovel.this, "Please Select Image or Add Image Name", android.widget.Toast.LENGTH_LONG).show();
        }
    }
}