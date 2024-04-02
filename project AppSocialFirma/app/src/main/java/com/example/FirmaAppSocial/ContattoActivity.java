package com.example.FirmaAppSocial;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContattoActivity extends AppCompatActivity {
    EditText nome;
    EditText cognome;
    EditText email;
    EditText messaggio;
    Button buttonInvia;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contatto);

        nome = findViewById(R.id.fieldNome);
        cognome = findViewById(R.id.fieldCognome);
        email = findViewById(R.id.fieldEmail);
        messaggio = findViewById(R.id.fieldMessaggio);
        buttonInvia = findViewById(R.id.buttonInvia);

       final String regexEmail = "[a-zA-Z0-9_-]{1,}+@[a-zA-Z0-9_-]{2,}.[a-z]{2,3}";
       final String regexName = "^[A-Za-z\\s]{3,50}$";
       final String regexSurname = "^[A-Za-z\\s]{3,50}$";
       final String regexMessaggio = "^.+";


       buttonInvia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeContatto = nome.getText().toString();
                String cognomeContatto = cognome.getText().toString();
                String emailContatto = email.getText().toString();
                String messaggioContatto = messaggio.getText().toString();


                /*Pattern pNome= Pattern.compile("^[A-Za-z]*$");
                Matcher mNome = pNome.matcher(nomeContatto);
                Matcher mCognome = pNome.matcher(cognomeContatto);
                boolean validateNome = mNome.matches();
                boolean validateCognome = mCognome.matches();
                boolean validateEmail = Patterns.EMAIL_ADDRESS.matcher(emailContatto).matches();*/

                //controllo campo nome
                if (!validateString(regexName,nomeContatto) ) {
                    Toast.makeText(ContattoActivity.this, R.string.error_Name, Toast.LENGTH_LONG).show();

                //controllo campo cognome
                } else if (!validateString(regexSurname,cognomeContatto)){
                    Toast.makeText(ContattoActivity.this, R.string.error_SurName, Toast.LENGTH_LONG).show();

                //controllo campo email
                } else if (!validateString(regexEmail,emailContatto)){
                    Toast.makeText(ContattoActivity.this, R.string.error_email, Toast.LENGTH_LONG).show();

                //controllo campo
                } else if (!validateString(regexMessaggio,messaggioContatto)){
                    Toast.makeText(ContattoActivity.this, R.string.error_text_messaggio, Toast.LENGTH_LONG).show();

                } else {
                        dialog = new ProgressDialog(ContattoActivity.this);
                        System.out.println("in doSendRichiesta");
                        IService contattoService = RetrofitClientInstance.getRetrofitInstance().create(IService.class);
                        Call<ContattoResponse> call = contattoService.contatto(nomeContatto.trim(), cognomeContatto.trim(), emailContatto.trim(), messaggioContatto.trim());
                        dialog.setMessage("Caricamento in corso...");
                        dialog.show();
                        call.enqueue(new Callback<ContattoResponse>() {
                            @Override
                            public void onResponse(Call<ContattoResponse> call, Response<ContattoResponse> response) {
                                dialog.dismiss();
                                ContattoResponse contattoResponse = response.body();
                                System.out.println(response.body());

                                if (contattoResponse.isStatus()) {
                                    System.out.println("contatto is status ok");
                                    resetForm();
                                    Toast.makeText(ContattoActivity.this, contattoResponse.getResult(), Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(ContattoActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(ContattoActivity.this, "La richiesta NON e' stata inviata, riprova piu' tardi", Toast.LENGTH_LONG).show();

                                }
                            }

                            @Override
                            public void onFailure(Call<ContattoResponse> call, Throwable t) {
                                dialog.dismiss();
                                Toast.makeText(ContattoActivity.this, "Si sono verificati degli errori,La richiesta non e' stata inviata, riprova piu' tardi ", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
            }
        });
    }
    private void resetForm() {
        nome.getText().clear();
        cognome.getText().clear();
        email.getText().clear();
        messaggio.getText().clear();
    }
    private boolean validateString(String regex, String value) {
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
        final Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
   /* private boolean validateContatto (String input, String regex){
       final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
       final Matcher matcher = pattern.matcher(input);
       return matcher.matches();
   }*/
}
