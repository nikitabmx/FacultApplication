package com.example.nikita.facultapplication.helpers;

import android.app.Application;

import com.example.nikita.facultapplication.Api.ServiceGeneraror;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.transport.TransportFactory;

public class App extends Application {

    private static ServiceGeneraror serviceGeneraror;

    @Override
    public void onCreate() {
        super.onCreate();


        // Yandex MapKit init's
        MapKitFactory.setApiKey("ac9f3d41-e06d-495e-bbd3-a233eb1a805f");
        MapKitFactory.initialize(this);
        TransportFactory.initialize(this);

    }




    public static ServiceGeneraror get_serviceGeneraror(){
        return serviceGeneraror;
    }
}
