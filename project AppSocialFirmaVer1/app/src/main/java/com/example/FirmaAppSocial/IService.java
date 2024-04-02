package com.example.FirmaAppSocial;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface IService {

    @FormUrlEncoded
    @POST("registrazione_augusto.php")
    Call<SignUpResponse> registrazione(@Field("nome") String inputNome, @Field("cognome") String inputCognome, @Field("data_nascita") String inputBdDate,@Field("sesso") String sesso, @Field("telefono") String inputTel, @Field("email") String inputEmail, @Field("password") String inputPass);
    @FormUrlEncoded
    @POST("login_augusto_script.php")
    Call<LoginResponse> login(@Field("username") String username, @Field("password") String password);
    @FormUrlEncoded
    @POST("contatto.php")
    Call<ContattoResponse> contatto(@Field("nome") String nome, @Field("cognome") String cognome, @Field("email") String email, @Field("telefono") String phone,@Field("messaggio") String messaggio);

    @FormUrlEncoded
    @POST("reset_password_augusto.php")
    Call<ForgotResponse> reset_password_augusto(@Field("email") String emailF);
    @GET("get_posts_augusto.php")
    Call<ArrayList<Post>> getPosts();
    /*@GET("get_posts_augusto.php")
    Call<ArrayList<Post>> getPosts();*/

}
