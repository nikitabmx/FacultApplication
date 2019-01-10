package com.example.nikita.facultapplication.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nikita.facultapplication.MainActivity;
import com.example.nikita.facultapplication.R;
import com.example.nikita.facultapplication.adapters.ContactsAdapter;
import com.example.nikita.facultapplication.models.ContactsModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class ContactsFragment extends Fragment {


    private static final int PERMISSION_REQUEST_CODE = 1;
    private RecyclerView recyclerViewContacts;
    private LinearLayoutManager linearLayoutManager;

    public ContactsFragment(){

    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        recyclerViewContacts = view.findViewById(R.id.recycleViewContacts);


        linearLayoutManager = new LinearLayoutManager(getContext());
        RecyclerView.LayoutManager layoutManager = linearLayoutManager;
        recyclerViewContacts.setLayoutManager(layoutManager);


        ContactsAdapter contactsAdapter = new ContactsAdapter(getContext(), getContacts());

        recyclerViewContacts.setAdapter(contactsAdapter);





        return  view;
    }



    private List<ContactsModel> getContacts(){

        List<ContactsModel> list = new ArrayList<>();



        Cursor cursor = Objects.requireNonNull(getActivity()).getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null,
                ContactsContract.Contacts.DISPLAY_NAME );
        assert cursor != null;
        cursor.moveToFirst();

        while (cursor.moveToFirst()){

            list.add(new ContactsModel(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)),

                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))));

        }

        return null;
    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if (requestCode == 1) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Permission is granted
//
//            } else {
//                Toast.makeText(getActivity(), "Until you grant the permission, we cannot display the " +
//                        "names", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }






}
