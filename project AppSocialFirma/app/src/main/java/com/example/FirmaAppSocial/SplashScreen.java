package com.example.FirmaAppSocial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //recupero utente loggato
        /*SharedPreferences sharedPreferences = SplashScreen.this.getPreferences(Context.MODE_PRIVATE);


        LoginResponse loginResponse = new Gson().fromJson(jsonLogin,LoginResponse.class);
        if (loginResponse == null) {
            Intent redirect = new Intent(SplashScreen.this, SignInActivity.class);
            startActivity(redirect);
        }*/

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }
}