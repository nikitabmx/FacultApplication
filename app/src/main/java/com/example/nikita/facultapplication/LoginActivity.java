package com.example.nikita.facultapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.nikita.facultapplication.helpers.App;
import com.example.nikita.facultapplication.models.Token;
import com.example.nikita.facultapplication.models.User;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private String LOG = "LOGIN_ACTIVITY";





    //    АВТОРИЗАЦИЯ ГИТ
    private static final String cliendID = "1e37b23b5b996ea9d76d";
    private static final String clientSecret = "44c4cf2c598a6cf0a61d3f472db15205cc2b0fc6";
    //на всякий случай  private static final String App_ID = "964822";

    // ссылки для редиректа и колбека
    private static final String callbackURI = "com.example.nikita.facultapplication://callback";
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

               // отправляемся в браузер покорять Апи гитхаба
                String urlRedirectFULL = urlRedirectStart + cliendID + urlRedirectEnd + callbackURI;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlRedirectFULL));
                startActivity(intent);
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











    @Override
    protected void onResume() {
        super.onResume();

        Uri uri = getIntent().getData();
        if (uri != null && uri.toString().startsWith(callbackURI)) {

            String code = uri.getQueryParameter("code");
            App.get_serviceGeneraror().getToken(cliendID, clientSecret, code, new Callback<Token>() {



                @Override
                public void onResponse(@NonNull Call<Token> call, @NonNull Response<Token> response) {

                    if (response.isSuccessful()) {

                        assert response.body() != null;
                        Toast.makeText(LoginActivity.this, "Токен = " + response.body().getToken(), Toast.LENGTH_LONG).show();
                        App.setAccessToken(response.body().getToken());
                        App.setBaseServiceGenerator();
                        getUserName();

                    } else {

                        Log.d(LOG, "ошибка получения токена" + response.code());
                        try {

                            assert response.errorBody() != null;
                            Log.d(LOG, "ошибка получения токена " + response.errorBody().string());

                        } catch (IOException e) { e.printStackTrace(); }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Token> call, @NonNull Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }





    private void getUserFullName(){

        App.get_serviceGeneraror().getUserFullName(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {


            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {

            }
        });
    }








    private void getUserName() {


        App.get_serviceGeneraror().getCurrentUser(new Callback<User>() {

            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {

                if (response.isSuccessful()) {
                    assert response.body() != null;
                    App.setUsername(response.body().getLogin());

                    // если все ок, то отправляемся в мэйнактивити
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finishAffinity();

                } else {

                    try {

                        assert response.errorBody() != null;
                        Log.d(LOG, "Ошибка получения имени" + response.errorBody().string());

                    } catch (IOException e) { e.printStackTrace(); } }


            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) { t.printStackTrace(); }

        });
    }

















}