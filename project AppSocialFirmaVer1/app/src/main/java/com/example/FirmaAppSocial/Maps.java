package com.example.FirmaAppSocial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Maps extends AppCompatActivity {
    Button invio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps);
        invio = findViewById(R.id.maps);

        Uri location = Uri.parse("geo:0,0?q=1600+Amphitheatre+Parkway,+Mountain+View,+California");
       /* invio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent r = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(r);
            }
        });*/
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
        try {
            invio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = null;
                    startActivity(mapIntent);
                }
            });

        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        }
    }
}