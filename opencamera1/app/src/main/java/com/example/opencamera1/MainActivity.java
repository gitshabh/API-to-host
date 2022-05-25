 package com.example.opencamera1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


 public class MainActivity extends AppCompatActivity {

    public static final int CAMERA_PERM_CODE = 101;
     public static final int CAMERA_REQUEST_CODE = 102;
     private ImageView imageView;
     private Button button,sexyButton;
     Bitmap image;
     String base64;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.capturedImage);
        button = findViewById(R.id.openCamera);
        sexyButton = findViewById(R.id.button);

        if(getSupportActionBar() !=null){
            getSupportActionBar().hide();
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askCameraPermission();
            }
        });

        sexyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncTask<Void, Void, String>() {
                    @Override
                    protected String doInBackground(Void... voids) {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        image.compress(Bitmap.CompressFormat.JPEG,100,baos);
                        byte[] b =baos.toByteArray();

                        String encodeImage = Base64.encodeToString(b,Base64.DEFAULT);
                        return encodeImage;
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        Log.d("base64",s);
                        base64 = s;

                    }
                }.execute();



            }
        });



    }

    private void askCameraPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ){
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.CAMERA},CAMERA_PERM_CODE);
        }else{
            openCamera();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull  String[] permissions, @NonNull  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == CAMERA_PERM_CODE){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                openCamera();
            }else{
                Toast.makeText(this, "Camera Permission required to use the camera ", Toast.LENGTH_SHORT).show();
            }
        }
    }

     private void openCamera() {
            Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(camera,CAMERA_REQUEST_CODE);
     }

     @Override
     protected void onActivityResult(int requestCode, int resultCode,Intent data) {
         super.onActivityResult(requestCode, resultCode, data);
         if(requestCode==CAMERA_REQUEST_CODE){
             image = (Bitmap) data.getExtras().get("data");
             imageView.setImageBitmap(image);


         }
     }
 }

