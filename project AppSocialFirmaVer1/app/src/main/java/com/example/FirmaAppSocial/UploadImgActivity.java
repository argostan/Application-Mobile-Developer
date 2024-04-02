package com.example.FirmaAppSocial;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_MEDIA_IMAGES;
import static android.Manifest.permission.READ_MEDIA_VIDEO;
import static android.Manifest.permission_group.CAMERA;
import static android.Manifest.permission_group.READ_MEDIA_AURAL;

import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class UploadImgActivity extends AppCompatActivity {

    Button upload = findViewById(R.id.buttonUpload);
    ImageView image = findViewById(R.id.imageUpload);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);


    }
    private boolean checkPermission() {
        int resultCamera = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);

        if (Build.VERSION.SDK_INT>=33){
            int resultImg = ContextCompat.checkSelfPermission(getApplicationContext(), READ_MEDIA_IMAGES);
            int resultVideo = ContextCompat.checkSelfPermission(getApplicationContext(), READ_MEDIA_VIDEO);

            return resultCamera == PackageManager.PERMISSION_GRANTED
                    && resultImg == PackageManager.PERMISSION_GRANTED
                    && resultVideo == PackageManager.PERMISSION_GRANTED;
        }else {
            int resultStorage = ContextCompat.checkSelfPermission(getApplicationContext(),READ_EXTERNAL_STORAGE);
            return resultCamera == PackageManager.PERMISSION_GRANTED && resultStorage == PackageManager.PERMISSION_GRANTED;

        }



    }

}