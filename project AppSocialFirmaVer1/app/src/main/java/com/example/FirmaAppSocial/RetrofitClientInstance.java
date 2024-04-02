package com.example.FirmaAppSocial;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import android.util.Base64;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {

    private static Retrofit retrofit;
    public static final String BASE_URL = "https://sviluppo.mariorastelli.com/corsoapp/";
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .client(client)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
//implementazione del token
    public static String token(){
        String token ="pippo:pluto";
        byte[] data = new byte[0];
        try {
            data = (token).getBytes("UTF-8");
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        //implementazione del basic64 autentication
        return Base64.encodeToString(data, Base64.NO_WRAP);
    }


    static OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {

            Request newRequest = chain.request().newBuilder()
                    .addHeader("Authorization","Basic " + token())
                    .build();
            return chain.proceed(newRequest);
        }

    }).build();
}
