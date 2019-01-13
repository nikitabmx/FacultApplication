package com.example.nikita.facultapplication.Api;

import com.example.nikita.facultapplication.models.Token;
import com.example.nikita.facultapplication.models.GitHubRepoModel;
import com.example.nikita.facultapplication.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ServerApi {


    @Headers("Accept: application/json")
    @POST("login/oauth/access_token")
    @FormUrlEncoded

    Call<Token> getToken(

            @Field("client_id") String clientId,
            @Field("client_secret") String secret,
            @Field("code") String code
    );


    // метод получения юзера по токену
    @GET("/user")
    Call<User>getCurrentUser();


    // и получаем его репозитории
    @GET("/users/{user}/repos")
    Call<List<GitHubRepoModel>>getReposForUser(@Path("user") String user);

    //получаем имя в био
    @GET("/users/{user}/")
    Call<User>getUserFullName();




}
