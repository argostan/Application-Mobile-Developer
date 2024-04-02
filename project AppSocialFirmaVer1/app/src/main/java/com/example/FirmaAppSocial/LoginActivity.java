package com.example.FirmaAppSocial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    Button signIn;
    TextView registrati;
    TextView forgot;
    ImageView logo;
    ProgressDialog dialog;

    final String regexEmail = "[a-zA-Z0-9_-]{1,}+@[a-zA-Z0-9_-]{2,}.[a-z]{2,3}";
    final String regexPassword = "[a-zA-Z0-9!@#?_=]{8,}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);  //modalità night disabilitato
        username = findViewById(R.id.emailaddress);
        password = findViewById(R.id.password);
        registrati = findViewById(R.id.registrati);
        signIn = findViewById(R.id.buttonsignin);
        forgot = findViewById(R.id.forgot);
        logo = findViewById(R.id.logo);

        Log.d("LoginActivity","OnCreate");

      signIn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              String inputEmail = username.getText().toString();
              String inputPassword = password.getText().toString();

              //controllo email
              if (!validateInput(regexEmail,inputEmail)) {
                  Toast.makeText(LoginActivity.this, R.string.error_email, Toast.LENGTH_LONG).show();

              //controllo password
              } else if (!validateInput(regexPassword,inputPassword)) {
                  Toast.makeText(LoginActivity.this, R.string.error_pass, Toast.LENGTH_LONG).show();

              } else {
                  //mostrare caricamento in corso
                  dialog = new ProgressDialog(LoginActivity.this);
                  dialog.setMessage("Caricamento in corso...");
                  dialog.show();
                  doLogin();
              }
              System.out.println("hai cliccato");
              Log.d("click","Hai cliccato");
          }
      });
        registrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent r = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(r);
                System.out.println("hai registrati");
                Log.d("click","Hai cliccato");
            }

        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent s = new Intent(LoginActivity.this, ForgotpassActivity.class);
                startActivity(s);
                System.out.println("hai cliccato forgot");
                Log.d("click","Hai cliccato");
            }
        });

        logo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(LoginActivity.this, getText(R.string.img_long), Toast.LENGTH_LONG).show();
                return false;
            }
        });

    }
    private boolean validateInput(String regex, String value) {
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
        final Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
    private void doLogin() {
        System.out.println("in do Login");
        IService loginService = RetrofitClientInstance.getRetrofitInstance().create(IService.class);
        Call<LoginResponse> call = loginService.login(username.getText().toString().trim(), password.getText().toString().trim());
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                dialog.dismiss();
                LoginResponse loginResponse = response.body();
                System.out.println(response.body());
                //salvataggio dati utente in locale tramite shared preference
                if (loginResponse.isStatus()) {
                    //login OK
                    System.out.println("login is status ok");
                    SharedPreferences sharedPref = LoginActivity.this.getSharedPreferences("Firma", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("LoginResponse", new Gson().toJson(loginResponse));
                    System.out.println(editor);
                    editor.apply();
                    //finish();  //blocca l'activity
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class); //redirect home activity
                    //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  //blocca l'activity
                    startActivity(intent);
                    Toast.makeText(LoginActivity.this, "Accesso eseguito con successo", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Username o password non corretti!!!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(LoginActivity.this, "Si sono verificati degli errori", Toast.LENGTH_LONG).show();
            }
            private boolean validateInput(String regex, String value) {
                final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
                final Matcher matcher = pattern.matcher(value);
                return matcher.matches();
            }
        });
    }



    @Override
    protected void onStart() {
        super.onStart();
        Log.d("LoginActivity","OnStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("LoginActivity","OnResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("LoginActivity","OnPause");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d("LoginActivity","OnStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("LoginActivity","OnDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("LoginActivity","OnRestart");
    }

}