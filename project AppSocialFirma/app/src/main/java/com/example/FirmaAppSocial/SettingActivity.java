package com.example.FirmaAppSocial;

import static com.example.FirmaAppSocial.R.id.nav_setting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SettingActivity extends AppCompatActivity {
    TextView gestioneAccount;
    TextView impostazioniApp;
    TextView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        gestioneAccount = findViewById(R.id.nav_setting);
        impostazioniApp = findViewById(R.id.nav_impostazioni_app);
        logout = findViewById(R.id.nav_logout);

        gestioneAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent click = new Intent(SettingActivity.this, ProfileActivity.class);
                startActivity(click);
            }
        });

        impostazioniApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent click = new Intent(SettingActivity.this, SettingAppActivity.class);
                startActivity(click);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent click = new Intent(SettingActivity.this, LoginActivity.class);
                startActivity(click);
            }
        });
    }
}