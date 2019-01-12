package com.example.nikita.facultapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.nikita.facultapplication.Fragments.ContactsFragment;
import com.example.nikita.facultapplication.Fragments.DeviceInfFragment;
import com.example.nikita.facultapplication.Fragments.MapFragment;
import com.example.nikita.facultapplication.Fragments.ReposFragment;
import com.example.nikita.facultapplication.Fragments.SensorsFragment;
import com.example.nikita.facultapplication.helpers.App;
import com.yandex.mapkit.MapKitFactory;

import java.util.Objects;

import static com.example.nikita.facultapplication.R.string.Token;
import static com.example.nikita.facultapplication.R.string.no_user_info;
import static java.util.Objects.*;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    TextView usernameTV;
    TextView usernameLabelTV;
    View headerView;
    private static final int PERMISSION_REQUEST_CODE = 123;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // проверяем все разрешения и даем добро
        checkPermission();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ReposFragment()).commit();
        navigationView.setCheckedItem(R.id.nav_github_repos);




        View head = navigationView.getHeaderView(0);
        usernameTV = (TextView)head.findViewById(R.id.headerFirstTV);
        usernameLabelTV = (TextView) head.findViewById(R.id.headerSndTV);

        if (App.getAccessToken() != null && App.getUsername() != null){
            usernameTV.setText(R.string.username + App.getUsername());
            usernameLabelTV.setText("валаделец аккаунта ^ ");
        }else {
            usernameTV.setText(R.string.no_user_info);
            usernameLabelTV.setText("");
        }

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }










    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();



        if (id == R.id.nav_github_repos) {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ReposFragment()).commit();

        } else if (id == R.id.nav_map) {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MapFragment()).commit();

        } else if (id == R.id.nav_contacts) {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ContactsFragment()).commit();

        } else if (id == R.id.nav_device_info) {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DeviceInfFragment()).commit();

        }else if (id == R.id.nav_sensors) {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SensorsFragment()).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);



        return true;
    }





    public void checkPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)

        {
            requestPermissions(new String[]{

                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_CONTACTS

                    }, 1);

        }

    }


}
