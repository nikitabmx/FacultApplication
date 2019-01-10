package com.example.nikita.facultapplication.Api;

import com.example.nikita.facultapplication.models.GitHubRepo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ServerApi {



   // получаем репозитории
    @GET("/users/{user}/repos")
    Call<List<GitHubRepo>> getReposForUser(@Path("user") String user);


}
