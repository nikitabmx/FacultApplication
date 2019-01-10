package com.example.nikita.facultapplication.Api;

import android.support.annotation.NonNull;

import com.example.nikita.facultapplication.models.GitHubRepoModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGeneraror {

    private static final String BASE_URL = "https://api.github.com/";
    private  ServerApi serverApi;


   public ServiceGeneraror(String base_url){



       OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {

           @NonNull
           @Override
           public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {

               okhttp3.Request.Builder builder = chain.request().newBuilder();

               return chain.proceed(builder.build());
           }
       }).readTimeout(60, TimeUnit.SECONDS).build();




       Gson gson = new GsonBuilder()
               .setLenient()
               .create();

       Retrofit retrofit = new Retrofit.Builder()
               .client(client)
               .baseUrl(base_url)
               .addConverterFactory(GsonConverterFactory.create(gson))
               .build();
         serverApi = retrofit.create(ServerApi.class);
   }






    public void getRepos(String userName, Callback<List<GitHubRepoModel>> callback) {
        serverApi.getReposForUser(userName).enqueue(callback);
   }



}
