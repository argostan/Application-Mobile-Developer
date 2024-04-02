package com.example.FirmaAppSocial;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContattoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContattoFragment extends Fragment {
    EditText nome;
    EditText cognome;
    EditText email;
    EditText messaggio;
    Button buttonInvia;
    ProgressDialog dialog2;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ContattoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContattoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContattoFragment newInstance(String param1, String param2) {
        ContattoFragment fragment = new ContattoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contatto, container, false);
    }
    //qui creiamo il metodo dove scriamo il codice dell'activity contatto
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //qui aggiungiamo view per agganciare l'elemento nel layout
        nome = view.findViewById(R.id.fieldNome);
        cognome = view.findViewById(R.id.fieldCognome);
        email = view.findViewById(R.id.fieldEmail);
        messaggio = view.findViewById(R.id.fieldMessaggio);
        buttonInvia = view.findViewById(R.id.buttonInvia);

        final String regexEmail = "[\\w.]+@[\\w.]+.[a-z]{2,3}";
        final String regexName = "[a-zA-Z]{2,}";



        buttonInvia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeContatto = nome.getText().toString();
                String cognomeContatto = cognome.getText().toString();
                String emailContatto = email.getText().toString();
                String messaggioContatto = messaggio.getText().toString();


                Pattern pNome= Pattern.compile("^[A-Za-z]*$");
                Matcher mNome = pNome.matcher(nomeContatto);
                Matcher mCognome = pNome.matcher(cognomeContatto);
                boolean validateNome = mNome.matches();
                boolean validateCognome = mCognome.matches();
                boolean validateEmail = Patterns.EMAIL_ADDRESS.matcher(emailContatto).matches();


                if (!validateNome || !validateCognome) {
                    Toast.makeText(getActivity(), R.string.error_Name, Toast.LENGTH_LONG).show();
                } else if (!validateEmail){
                    Toast.makeText(getActivity(), R.string.error_email, Toast.LENGTH_LONG).show();
                } else {
                    dialog2 = new ProgressDialog(getActivity());
                    System.out.println("in doSendRichiesta");
                    IService contattoService = RetrofitClientInstance.getRetrofitInstance().create(IService.class);
                    Call<ContattoResponse> call = contattoService.contatto(nomeContatto.trim(), cognomeContatto.trim(), emailContatto.trim(), messaggioContatto.trim());
                    dialog2.setMessage("Caricamento in corso...");
                    dialog2.show();
                    call.enqueue(new Callback<ContattoResponse>() {
                        @Override
                        public void onResponse(Call<ContattoResponse> call, Response<ContattoResponse> response) {
                            dialog2.dismiss();
                            ContattoResponse contattoResponse = response.body();
                            System.out.println(response.body());

                            if (contattoResponse.isStatus()) {
                                System.out.println("contatto is status ok");
                                resetForm();
                                Toast.makeText(getActivity(), contattoResponse.getResult(), Toast.LENGTH_LONG).show();
                                //getActivity() qui richiamiamo l'activity invece del nome dell'activity.this
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getActivity(), "La richiesta NON e' stata inviata, riprova piu' tardi", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ContattoResponse> call, Throwable t) {
                            dialog2.dismiss();
                            Toast.makeText(getActivity(), "Si sono verificati degli errori,La richiesta non e' stata inviata, riprova piu' tardi ", Toast.LENGTH_LONG).show();
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
    private boolean validateContatto (String input, String regex){
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
        final Matcher matcher = pattern.matcher(input);
        return matcher.matches();
   }
}
