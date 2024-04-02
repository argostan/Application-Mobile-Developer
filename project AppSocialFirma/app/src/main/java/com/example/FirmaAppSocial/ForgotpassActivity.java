package com.example.FirmaAppSocial;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotpassActivity extends AppCompatActivity {
    EditText emailForgot;
    Button buttonForgot;
    final String regexEmail = "[\\w.]+@[\\w.]+.[a-z]{2,3}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpass);
        emailForgot = findViewById(R.id.inputEmailForgot);
        buttonForgot = findViewById(R.id.buttonForgot);


        buttonForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailF = emailForgot.getText().toString();
                String vuoto = "";

                //control/validate email
                if (!validate(emailF, regexEmail)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(ForgotpassActivity.this);
                    builder.setTitle(R.string.attention);
                    builder.setMessage(R.string.email_noValid);
                    builder.setPositiveButton(R.string.edit, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    emailForgot.setText(vuoto);
                                    dialog.dismiss();
                                }
                            });
                    builder.setNegativeButton(getString(R.string.close), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                            Intent r = new Intent(ForgotpassActivity.this, LoginActivity.class);
                            startActivity(r);
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    IService forgotService = RetrofitClientInstance.getRetrofitInstance().create(IService.class);
                    Call <ForgotResponse> call = forgotService.reset_password_augusto(emailF.trim());
                    call.enqueue(new Callback<ForgotResponse>() {
                        @Override
                        public void onResponse(Call<ForgotResponse> call, Response<ForgotResponse> response) {
                            ForgotResponse forgotResponse = response.body();
                            System.out.println(response.body());
                            Toast.makeText(ForgotpassActivity.this, forgotResponse.getResult(), Toast.LENGTH_LONG).show();


                            if (forgotResponse.isStatus()) {
                                System.out.println("forgot isstatus ok");
                                AlertDialog.Builder builder = new AlertDialog.Builder(ForgotpassActivity.this);
                                builder.setMessage(R.string.control_email);
                                builder.setTitle(R.string.success_sending);
                                builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                        Intent r = new Intent(ForgotpassActivity.this, LoginActivity.class);
                                        startActivity(r);
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            } else {
                                Toast.makeText(ForgotpassActivity.this, forgotResponse.getResult(), Toast.LENGTH_LONG).show();
                            }
                        }


                        public void onFailure(Call<ForgotResponse> call, Throwable t) {
                            Toast.makeText(ForgotpassActivity.this, "Si sono verificati degli errori,La richiesta non e' stata inviata, riprova piu' tardi ", Toast.LENGTH_LONG).show();
                        }
                    });



                }
            }
        });
    }
        private boolean validate (String input, String regex){
            final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
            final Matcher matcher = pattern.matcher(input);
            return matcher.matches();
        }
    }
