package com.example.nikita.facultapplication.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nikita.facultapplication.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import static android.os.Environment.getExternalStoragePublicDirectory;


public class SensorsFragment extends Fragment{

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private SensorEventListener listener;
    private float[] accelVal = new float[3];


    private Button takePhotoButton;
    private ImageView photoIV;
    private TextView accelometerTV;

    StringBuilder stringBuilder = new StringBuilder();
    String pathToFile;

    private static final int REQUEST_IMAGE_CAPTURE= 1;
    private static final int PERMISSION_REQUEST_CODE = 123;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sensors, container, false);

        takePhotoButton = view.findViewById(R.id.btnTakePhoto);
        photoIV = view.findViewById(R.id.imgViewScreen);
        accelometerTV = view.findViewById(R.id.tvAccelerometerTV);
        sensorManager = (SensorManager) (Objects.requireNonNull(getActivity())).getSystemService(Context.SENSOR_SERVICE);
        assert sensorManager != null;
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        // Check the SDK version and whether the permission is already granted or not.

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ActivityCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        }

        takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkPermission()) {
                    dispatchTakePictureIntent();
                } else{
                    allowPermission();
                }
            }
        });


        listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                System.arraycopy(event.values, 0, accelVal, 0, 3);
                showInfo();
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };
    }


    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(listener);
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(listener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(Objects.requireNonNull(getActivity()).getPackageManager()) != null) {
            File photofile;
            //Вызываем метод создания фотокарточки
            photofile = takePicture();
            if (photofile != null) {
                pathToFile = photofile.getAbsolutePath();
                Uri photoUri = FileProvider.getUriForFile(Objects.requireNonNull(getContext()),
                        "com.example.nikita.facultapplication.fileprovider", photofile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }


    private  File takePicture(){
        @SuppressLint("SimpleDateFormat") String fileName  =  new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File storageDir  = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = null;

        try {
            image = File.createTempFile(fileName, ".jpg", storageDir);
        }catch (IOException  e ){
            Log.d("SensorFragment", "Exception takePicture()");

        } return image;
    }

    private boolean checkPermission(){
        int res;
        String[] permissions = new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, };

        for (String perms : permissions) {
            res = Objects.requireNonNull(this.getContext()).checkCallingOrSelfPermission(perms);
            if (!(res == PackageManager.PERMISSION_GRANTED)) {
                return false;
            }
        }
        return true;
    }


    private void allowPermission(){
        String[] permissions = new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE){
            Bitmap bitmap = BitmapFactory.decodeFile(pathToFile);
            photoIV.setImageBitmap(bitmap);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        dispatchTakePictureIntent();
    }


    void showInfo() {
        stringBuilder.setLength(10);
        stringBuilder.append("Value: ").append(format(accelVal));
        accelometerTV.setText(stringBuilder);
    }


    @SuppressLint("DefaultLocale")
    String format(float values[]) {
        return String.format("%1$.1f\t\t%2$.1f\t\t%3$.1f", values[0], values[1],
                values[2]);
    }


}
