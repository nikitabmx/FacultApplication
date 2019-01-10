package com.example.nikita.facultapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.nikita.facultapplication.helpers.App;
import com.example.nikita.facultapplication.models.AccessToken;
import com.example.nikita.facultapplication.models.User;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {


    private String LOG = "LOGIN_ACTIVITY";

    // ссылка для редиректа
    String urlRedirectStart = "https://github.com/login/oauth/authorize?client_id=";
    String urlRedirectEnd = "&scope=repo&redirect_uri=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button oauthbutton = findViewById(R.id.oauthbutton);
        Button skipbutton = findViewById(R.id.skipbutton);


        oauthbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                oauth();
            }
        });



    skipbutton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finishAffinity();
        }
    });

    }








    private void oauth(){

            String urlRedirectFULL = urlRedirectStart + App.getCliendID() +
                    urlRedirectEnd + App.getRedirectURI();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlRedirectFULL));
            startActivity(intent);
        }


    @Override
    protected void onResume() {
        super.onResume();

        Uri uri = getIntent().getData();
        if (uri != null && uri.toString().startsWith(App.getRedirectURI())) {

            String code = uri.getQueryParameter("code");

            App.get_serviceGeneraror().getToken(App.getCliendID(), App.getClientSecret(), code, new Callback<AccessToken>() {
                @Override
                public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Токен = " + response.body().getToken(), Toast.LENGTH_LONG).show();
                        App.setAccessToken(response.body().getToken());
                        App.setBaseServiceGenerator();
                        getUserName();
                    } else {
                        Log.d(LOG, "Код ошибки = " + response.code());
                        try {
                            Log.d(LOG, "Сообщение ошибки = " + response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<AccessToken> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }



    private void getUserName() {
        App.get_serviceGeneraror().getCurrentUser(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    App.setUsername(response.body().getLogin());
                    goMainActivity();
                } else {

                    try {
                        Log.d(LOG, "Сообщение ошибки = " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    //Метод для перехода на след активити - которое головное
    private void goMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finishAffinity();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


















}