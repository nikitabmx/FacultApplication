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
    private static SharedPreferences sharPrefs;
    private static final String AUTH_URL = "https://github.com/";
    private static final String BASE_URL = "https://api.github.com/";
    private static String username;







    @Override
    public void onCreate() {
        super.onCreate();

        // Yandex MapKit init's
        MapKitFactory.setApiKey("ac9f3d41-e06d-495e-bbd3-a233eb1a805f");
        MapKitFactory.initialize(this);
        TransportFactory.initialize(this);

        sharPrefs = getSharedPreferences(String.valueOf(R.string.prefs_name), MODE_PRIVATE);


        //получаем имя пользователя из сохраненных данных на устройстве
        username = sharPrefs.getString(String.valueOf(R.string.username), null);

        if (sharPrefs.getString(String.valueOf(R.string.token), null) != null) {

            setBaseServiceGenerator();
        } else {
            setAuthServiceGenerator();
        }



    }


    public static ServiceGeneraror get_serviceGeneraror(){
        return serviceGeneraror;
    }

    // cеттеры и геттеры юернейма. Достаем данные
    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        App.username = username;
        sharPrefs.edit().putString(String.valueOf(R.string.username), username).apply();
    }

    public static void setAuthServiceGenerator() {
        serviceGeneraror = new ServiceGeneraror(AUTH_URL, null);
    }


    public static void setBaseServiceGenerator() {
        serviceGeneraror = new ServiceGeneraror(BASE_URL, getAccessToken());
    }

    // сохраняем токен на устройстве
    public static void setAccessToken(String token) {
        sharPrefs.edit().putString(String.valueOf(R.string.token), token).apply();
    }

    // забираем токен с утройства
    public static String getAccessToken() {
        return sharPrefs.getString(String.valueOf(R.string.token), null);
    }





}
