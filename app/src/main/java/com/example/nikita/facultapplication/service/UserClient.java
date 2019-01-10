package com.example.nikita.facultapplication.service;
import com.example.nikita.facultapplication.models.Login;
import com.example.nikita.facultapplication.models.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserClient {

    @POST("login")
    Call<User> login(@Body Login login);

    @GET("secretinfo")
    Call<ResponseBody> getSecret(@Header("Authtorization") String authToken);


}
