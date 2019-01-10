package com.example.nikita.facultapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.nikita.facultapplication.models.Login;
import com.example.nikita.facultapplication.models.User;
import com.example.nikita.facultapplication.service.UserClient;

import java.io.IOException;
import java.util.Base64;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private String mEmail;
    private String mPassword;


    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create());
    Retrofit retrofit = builder.build();
    UserClient userClient = retrofit.create(UserClient.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginbutton = findViewById(R.id.loginbutton);
        Button tokenbutton = findViewById(R.id.tokenbutton);











        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //login();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        tokenbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { token(); }
        });



    }

    private  static  String token;
    private void  login(){

        Login login = new Login("nikitabmx", "200598aA");
        Call<User> call = userClient.login(login);


        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                 if (response.isSuccessful()){
                     Toast.makeText(LoginActivity.this, "Success! Token is: " + response.body().getToken(), Toast.LENGTH_SHORT).show();
                     token = response.body().getToken();


                 }else{
                     Toast.makeText(LoginActivity.this,"Login/ Pass isnt correct ", Toast.LENGTH_SHORT).show();
                 }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LoginActivity.this,"error", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void token(){
        Call<ResponseBody> call = userClient.getSecret(token);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        Toast.makeText(LoginActivity.this, "Success! Token is: " + response.body().string() , Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }else{
                    Toast.makeText(LoginActivity.this,"Login/ Pass isnt correct ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }











//    public class UserLoginTask extends AsyncTask<Void,Void, Boolean>{
//
//
//    UserLoginTask(String email, String password){
//        mEmail = email;
//        mPassword = password;
//
//    }
//
//
//    @Override
//    public Boolean doInBackground(Void... voids) {
//        Retrofit.Builder builder = new Retrofit.Builder()
//                .baseUrl("https://api.github.com/")
//                .addConverterFactory(GsonConverterFactory.create());
//        Retrofit retrofit = builder.build();
//        UserClient userClient = retrofit.create(UserClient.class);
//        String username = "nikitabmx";
//        String userpass = "200598aA";
//        String  base = username + ":" + userpass;
//
//        String authHeader = "Basic" + android.util.Base64.encodeToString(base.getBytes(), android.util.Base64.NO_WRAP);
//        Call<User> call = userClient.getUser(authHeader);
//        try {
//            Response<User> response = call.execute();
//
//            if (response.isSuccessful()){
//                return true;
//            }
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//        }
//
//    }
//



}