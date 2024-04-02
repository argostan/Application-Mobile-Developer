package com.example.FirmaAppSocial;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignupActivity extends AppCompatActivity {
    EditText nome;
    EditText cognome;
    RadioGroup genderRg;
    RadioButton gender;
    EditText tel;
    EditText email;
    EditText pass;
    EditText repeatPass;
    CheckBox check;
    Button continua;
    EditText data_nascita;
    final String regexText = "^[A-Za-z\\s]{3,50}$";
    final String regexTel = "^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$";
    final String regexEmail = "[a-zA-Z0-9_-]{1,}+@[a-zA-Z0-9_-]{2,}.[a-z]{2,3}";
    final String regexPassword = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$";
    //    String mess = "message";
    final String regexDataNascita = "^(0?[1-9]|[12][0-9]|3[01])[\\/\\-](0?[1-9]|1[012])[\\/\\-]\\d{4}$";

    ProgressDialog dialogSigup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
//        Log.e("TITOLOLOG", "Log da onCreate");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nome = findViewById(R.id.nome);
        cognome = findViewById(R.id.cognome);
        data_nascita= findViewById(R.id.bdDate);
        genderRg = findViewById(R.id.gender);
        tel = findViewById(R.id.tel);
        email = findViewById(R.id.emailaddress);
        pass = findViewById(R.id.password);
        repeatPass = findViewById(R.id.repeatPassword);
        check = findViewById(R.id.checkBox);
        continua = findViewById(R.id.continua);


        data_nascita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();

                // on below line we are getting
                // our day, month and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(SignupActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        data_nascita.setText(day + "/" + month + "/" + year);
                    }
                }, year, month, day);
                // at last we are calling show to
                // display our date picker dialog.
                d.show();
            }
        });

        continua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputNome = nome.getText().toString();
                String inputCognome = cognome.getText().toString();
                String inputDataNascita = "23/11/1990";
                //String inputDataNascita = data_nascita.getText().toString();
                int selectedGender = genderRg.getCheckedRadioButtonId();
                String inputTel = tel.getText().toString();
                String inputEmail = email.getText().toString();
                String inputPassword = pass.getText().toString();
                String inputRepeatPassword = repeatPass.getText().toString();
                boolean checkValue = check.isChecked();
//                gender = (RadioButton) findViewById(selectedGender);
                String sesso;
                if (selectedGender == R.id.M) {
                    sesso = "M";
                } else if (selectedGender == R.id.F) {
                    sesso = "F";
                } else sesso = "A";

                    //controllo nome
                if (!validateInput(inputNome,regexText)) {
                    Toast.makeText(SignupActivity.this, R.string.error_Name, Toast.LENGTH_LONG).show();

                    //controllo cognome
                } else if (!validateInput(inputCognome,regexText)) {
                    Toast.makeText(SignupActivity.this, R.string.error_SurName, Toast.LENGTH_LONG).show();

                    //controllo data di nascita
                /*} else if (!validateInput(inputDataNascita,regexDataNascita)) {
                    Toast.makeText(SignupActivity.this, R.string.validate_data_nascita, Toast.LENGTH_LONG).show();*/

                /*} else if (!validateInput(inputTel,regexTel)) {
                    Toast.makeText(SignupActivity.this, R.string.error_gender, Toast.LENGTH_LONG).show();*/

                    //controllo numero di telefono
                } else if (!validateInput(inputTel,regexTel)) {
                    Toast.makeText(SignupActivity.this, R.string.error_phone, Toast.LENGTH_LONG).show();

                    //controllo email
                } else if (!validateInput(inputEmail, regexEmail )) {
                    Toast.makeText(SignupActivity.this, R.string.error_email, Toast.LENGTH_LONG).show();

                    //controllo password
                } else if (!validateInput(inputPassword,regexPassword)) {
                    Toast.makeText(SignupActivity.this, R.string.error_pass, Toast.LENGTH_LONG).show();

                    //controllo ripeti password
                } else if (!inputRepeatPassword.equals(inputPassword)) {
                    Toast.makeText(SignupActivity.this, R.string.pass_norepeat, Toast.LENGTH_LONG).show();

                    //controllo accetta condizioni
                } else if (!checkValue) {
                    Toast.makeText(SignupActivity.this, R.string.accept_cond, Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(SignupActivity.this, "Tutto ok!!", Toast.LENGTH_LONG).show();

                    dialogSigup = new ProgressDialog(SignupActivity.this);
                    System.out.println("in do signup Richiesta");
                    IService signupService = RetrofitClientInstance.getRetrofitInstance().create(IService.class);
                    Call<SignUpResponse> call = signupService.registrazione(inputNome.trim(), inputCognome.trim(), inputDataNascita.trim(),sesso.trim(), inputTel.trim(), inputEmail.trim(), inputPassword.trim());
                    dialogSigup.setMessage("Caricamento in corso...");
                    dialogSigup.show();
                    call.enqueue(new Callback<SignUpResponse>() {
                        @Override
                        public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                            dialogSigup.dismiss();
                            SignUpResponse signupResponse = response.body();
                            System.out.println(response.body());

                            if (signupResponse.isStatus()) {
                                System.out.println("contatto i status ok");
                                //resetForm();
                                Toast.makeText(SignupActivity.this, signupResponse.getResult(), Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(SignupActivity.this, "La richiesta NON e' stata inviata, riprova piu' tardi", Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<SignUpResponse> call, Throwable t) {
                            dialogSigup.dismiss();
                            Toast.makeText(SignupActivity.this, "Si sono verificati degli errori,La richiesta non e' stata inviata, riprova piu' tardi ", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }
    private boolean validateInput(String input, String regex) {
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
        final Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    private void resetForm() {
        nome.getText().clear();
        cognome.getText().clear();
        data_nascita.getText().clear();
        tel.getText().clear();
        email.getText().clear();
        pass.getText().clear();
        repeatPass.getText().clear();
        check.setSelected(false);
        Toast.makeText(SignupActivity.this, R.string.delete_form_ok, Toast.LENGTH_LONG).show();
    }
}

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.intro_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId() == R.id.delete_menu) {
//            AlertDialog.Builder confirmReset = new AlertDialog.Builder(SignupActivity.this);
//            confirmReset.setTitle(R.string.attention);
//            confirmReset.setMessage(R.string.confirm_question);
//            confirmReset.setPositiveButton(R.string.positive, new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    resetForm();
//                }
//            });
//            confirmReset.setNegativeButton(R.string.negative, new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                }
//            });
//            AlertDialog dialog = confirmReset.create();
//            dialog.show();
//            return true;
//        }
//        if (item.getItemId() == android.R.id.home) {
//            finish();
//        }
//        return super.onOptionsItemSelected(item);
//    }
//




