package com.example.FirmaAppSocial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;



public class SplashScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Log.d("SplashActivity","OnCreate");


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
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("SplashActivity","OnStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("SplashActivity","OnResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("SplashActivity","OnPause");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d("SplashActivity","OnStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("SplashActivity","OnDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("SplashActivity","OnRestart");
    }
}