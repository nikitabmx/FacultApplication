package com.example.nikita.facultapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.nikita.facultapplication.helpers.App;


public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (App.getAccessToken() != null) {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finishAffinity();

                } else {

                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finishAffinity();
                }

            }
        }, 1000);










    }
}
