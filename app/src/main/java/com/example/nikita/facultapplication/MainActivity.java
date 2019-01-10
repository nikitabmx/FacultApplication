package com.example.nikita.facultapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.nikita.facultapplication.Fragments.ContactsFragment;
import com.example.nikita.facultapplication.Fragments.DeviceInfFragment;
import com.example.nikita.facultapplication.Fragments.MapFragment;
import com.example.nikita.facultapplication.Fragments.ReposFragment;
import com.example.nikita.facultapplication.Fragments.SensorsFragment;
import com.yandex.mapkit.MapKitFactory;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);










        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ReposFragment()).commit();
        navigationView.setCheckedItem(R.id.nav_github_repos);



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
}
