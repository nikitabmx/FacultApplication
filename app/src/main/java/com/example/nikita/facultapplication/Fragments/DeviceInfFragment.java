package com.example.nikita.facultapplication.Fragments;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nikita.facultapplication.R;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Objects;


public class DeviceInfFragment extends Fragment {


    private TextView ip_TV;
    private TextView deviceRAM;
    private TextView device_model_TV;
    private TextView os_Version_TV;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_device_inf, container, false);



        ip_TV = v.findViewById(R.id.adressIpTV);
        device_model_TV = v.findViewById(R.id.tvModel);
        os_Version_TV = v.findViewById(R.id.tvVersion);
        deviceRAM = v.findViewById(R.id.ram);



        return v;

    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        ActivityManager manager = (ActivityManager) Objects.requireNonNull(getActivity()).getSystemService(Context.ACTIVITY_SERVICE);
        assert manager != null;
        manager.getMemoryInfo(info);

        float ram = info.totalMem;
        deviceRAM.append(" " + String.format("%1$.2f", ram / 1024 / 1024 / 1024) + "GB");
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onStart() {

        super.onStart();

        String version = Build.VERSION.RELEASE;
        String modelValue = Build.MODEL;

        ip_TV.setText("IP: " + getIpAddress());
        device_model_TV.setText("Model: " + modelValue);
        os_Version_TV.setText("Version: " + version);
    }


    public static String getIpAddress() {
        try {
            for (Enumeration en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intF = (NetworkInterface) en.nextElement();
                for (Enumeration enumIpAddress = intF.getInetAddresses(); enumIpAddress.hasMoreElements();) {
                    InetAddress inetAddress = (InetAddress) enumIpAddress.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }

        return null;
    }








}

