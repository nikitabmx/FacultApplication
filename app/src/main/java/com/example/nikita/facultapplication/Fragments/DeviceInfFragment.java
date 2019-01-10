package com.example.nikita.facultapplication.Fragments;
import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nikita.facultapplication.R;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;


public class DeviceInfFragment extends Fragment {


    private TextView wifiIp_TV;
    private TextView mobileIp_TV;
    private TextView device_model_TV;
    private TextView os_Version_TV;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_device_inf, container, false);
        wifiIp_TV = v.findViewById(R.id.wifiIpTV);
        mobileIp_TV = v.findViewById(R.id.mobileIpTV);
        device_model_TV = v.findViewById(R.id.tvModel);
        os_Version_TV = v.findViewById(R.id.tvVersion);


        return v;

    }


    public static String getMobileIPAddress() {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        return  addr.getHostAddress();
                    }
                }
            }
        } catch (Exception ex) {
            Log.d("Log", "Ошибка при получении адреса мобильной сети");
        }
        return "";
    }

    //Метод для получения адреса по вафле
    public static String getWifiIPAddress(Context context) {
        WifiManager wifiMgr = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        @SuppressLint("MissingPermission") WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        return  Formatter.formatIpAddress(ip);
//        return "kkk";
    }


    @Override
    public void onStart() {
        super.onStart();


        String mobileIP = getMobileIPAddress();
        String wifiIP = getWifiIPAddress(getContext());

        wifiIp_TV.setText("IPv4: " + wifiIP);
        mobileIp_TV.setText("IPv6: " + mobileIP);

        String modelValue = Build.MODEL;
        device_model_TV.setText("Model: " + modelValue);

        String version = Build.VERSION.RELEASE;
        os_Version_TV.setText("Version: " + version);
    }
}

