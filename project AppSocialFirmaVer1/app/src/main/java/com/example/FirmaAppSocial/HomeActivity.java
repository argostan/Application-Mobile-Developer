package com.example.FirmaAppSocial;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

public class HomeActivity extends AppCompatActivity{ // implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;


    //recupero utente logato



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences sharedPref = HomeActivity.this.getSharedPreferences("Aura", Context.MODE_PRIVATE);
        String jsonLogin = sharedPref.getString("LoginResponse", "");
        LoginResponse loginResponse = new Gson().fromJson(jsonLogin, LoginResponse.class);
        if (loginResponse == null){
            //to login
            Intent redirect = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(redirect);
       }


        //associazione dei componenti di layout
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);


        //associazione menu con suo gestore delle funzionalita'
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.exit);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        //associare
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //associare il comportamento del click
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectDrawerItem(item);
                /*if (item.getItemId() == R.id.home){
                    Toast.makeText(HomeActivity.this, "home", Toast.LENGTH_LONG).show();
                } else if (item.getItemId() == R.id.reports) {
                    Intent redirect2 = new Intent(HomeActivity.this, ContattoActivity.class);
                    startActivity(redirect2);
                    //Toast.makeText(HomeActivity.this, "reports", Toast.LENGTH_LONG).show();
                } else if (item.getItemId() == R.id.products) {
                    Toast.makeText(HomeActivity.this, "products", Toast.LENGTH_LONG).show();
                } else if (item.getItemId() == R.id.logout) {
                    Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                }*/
                return true;
            }
        });
        Menu menu = navigationView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.profile);
        selectDrawerItem(menuItem);
        actionBarDrawerToggle.syncState();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if(actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void logout() {
        //svuoto la login in memoria
        SharedPreferences sharedPref = HomeActivity.this.getSharedPreferences("Aura", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("LoginResponse", "");
        editor.apply();

        //redirect alla pagina di login
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);
    }
    private  void selectDrawerItem(MenuItem menuItem){
        try {
            Fragment fragment = null;
            Class fragmentClass = null;
            String title ="";
            int itemId = menuItem.getItemId();
            if (itemId == R.id.post) {
                fragmentClass = PostFragment.class;
            } else if (itemId == R.id.Contatto) {
                Intent intent = new Intent(HomeActivity.this, ContattoActivity.class);
                startActivity(intent);
            } else if (itemId == R.id.profile) {
                fragmentClass = ProfileFragment.class;
            } else if (itemId == R.id.home) {
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
            } else if (itemId == R.id.logout) {
                logout();
                return;
            } else if (itemId == R.id.ReportsFragment) {
                fragmentClass = ReportFragment.class;
            } else if (itemId == R.id.SettingFragment) {
                fragmentClass = SettingFragment.class;
            }
            fragment =(Fragment) fragmentClass.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fl_container,fragment).commit();

            //cambio il titolo dell action bar in base al componente del menu che ho cliccato
            setTitle(menuItem.getTitle());

            //valorizzo la voce del menu
            menuItem.setChecked(true);
            drawerLayout.closeDrawers();
        }catch (Exception e){
            Log.e(TAG,"selectDrawerItem", e);
        }
    }

}