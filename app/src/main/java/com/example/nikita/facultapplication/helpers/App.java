package com.example.nikita.facultapplication.helpers;

import android.app.Application;
import android.content.SharedPreferences;

import com.example.nikita.facultapplication.Api.ServiceGeneraror;
import com.example.nikita.facultapplication.R;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.transport.TransportFactory;

public class App extends Application {
        // важные инициализации
    private static ServiceGeneraror serviceGeneraror;
    private static SharedPreferences sharedPreferences;
    private static final String AUTH_URL = "https://github.com/";
    private static final String BASE_URL = "https://api.github.com/";
    private static final String App_ID = "964822";
    private static String USERNAME = "nikitabmx";



    //    АВТОРИЗАЦИЯ ГИТ
    //callback после аунтефикации
    private static final String callbackURI = "com.example.nikita.facultapplication://callback";
    //ClientId
    private static final String cliendID = "1e37b23b5b996ea9d76d";
    //ClientSecret
    private static final String clientSecret = "44c4cf2c598a6cf0a61d3f472db15205cc2b0fc6";




    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = getSharedPreferences(String.valueOf(R.string.prefs_name), MODE_PRIVATE);

        //получаем имя пользователя из сохраненных данных на устройстве
        USERNAME = sharedPreferences.getString(String.valueOf(R.string.username), null);


        if (sharedPreferences.getString(String.valueOf(R.string.token), null) != null) {

            setBaseServiceGenerator();
        } else {
            setAuthServiceGenerator();
        }

        //Вообще все важные инициализации надо проводить тут - где инициализация идет через статик методы

        // Yandex MapKit init's
        MapKitFactory.setApiKey("ac9f3d41-e06d-495e-bbd3-a233eb1a805f");
        MapKitFactory.initialize(this);
        TransportFactory.initialize(this);

    }




    public static ServiceGeneraror get_serviceGeneraror(){
        return serviceGeneraror;
    }



    public static void setAuthServiceGenerator() {
        serviceGeneraror = new ServiceGeneraror(AUTH_URL, null);
    }

    //А этот нетклиент для отправки запрос к апи
    public static void setBaseServiceGenerator() {
        serviceGeneraror = new ServiceGeneraror(BASE_URL, getAccessToken());
    }

    //Метод сохранения токена на устройстве
    public static void setAccessToken(String token) {
        sharedPreferences.edit().putString(String.valueOf(R.string.token), token).apply();
    }

    //Метод получения токена с устройства
    public static String getAccessToken() {
        return sharedPreferences.getString(String.valueOf(R.string.token), null);
    }





    public static String getCliendID() {
        return cliendID;
    }



    public static String getClientSecret() {
        return clientSecret;
    }



    public static String getRedirectURI() {
        return callbackURI;
    }



    //Метод получения
    public static String getUsername() {
        return USERNAME;
    }

    public static void setUsername(String username) {
        USERNAME = username;
        sharedPreferences.edit().putString(String.valueOf(R.string.username), username).apply();
    }

    public static String getAppId() {
        return App_ID;
    }

}
