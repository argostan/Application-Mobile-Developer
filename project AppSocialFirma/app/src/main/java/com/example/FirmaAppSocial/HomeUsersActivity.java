package com.example.FirmaAppSocial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class  HomeUsersActivity extends AppCompatActivity {

    ActionBar actionBar;
    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_users);
        actionBar = getSupportActionBar();
        actionBar.setTitle("Utente Loggato");
        navigationView = findViewById(R.id.home_users_navigation);
        navigationView.setOnNavigationItemSelectedListener(selectedListener);

    }
    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment = null;
            Class fragmentClass = null;

            if (menuItem.getItemId() == R.id.home) {
                actionBar.setTitle("Utente Loggato");
                Intent intent = new Intent(HomeUsersActivity.this, HomeUsersActivity.class);
                startActivity(intent);
                return true;

            } else if (menuItem.getItemId() == R.id.email) {
                actionBar.setTitle("Email");
                fragmentClass = EmailFragment.class;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                }
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content,fragment).commit();
                return true;

            } else if (menuItem.getItemId() == R.id.post) {
                actionBar.setTitle("Post");
                fragmentClass = PostFragment.class;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                }
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content,fragment).commit();
                return true;
            } else if (menuItem.getItemId() == R.id.setting) {
                actionBar.setTitle("Setting");
                Intent intent = new Intent(HomeUsersActivity.this, SettingActivity.class);
                startActivity(intent);
                return true;
            }
            return false;
        }
    };
}