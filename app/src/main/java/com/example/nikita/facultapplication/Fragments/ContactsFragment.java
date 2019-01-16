package com.example.nikita.facultapplication.Fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.nikita.facultapplication.MainActivity;
import com.example.nikita.facultapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@SuppressWarnings("ALL")
public class ContactsFragment extends Fragment {


    MainActivity mainActivity;
    ListView listViewContacts;
    HashMap<String, String> nameNumberHashMap = new HashMap<>();
    private static final int PERMISSION_REQUEST_CODE = 123;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contacts, container, false);

        mainActivity = (MainActivity) getActivity();
        listViewContacts = rootView.findViewById(R.id.contactsLV);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (checkPermissions()) {
            showAllContacts();
        } else {
            setPermission();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        showAllContacts();
    }

    private void showAllContacts() {
        Cursor cursor = mainActivity.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[] {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.Phone.NUMBER},
                null ,
                null,
                null);
        mainActivity.startManagingCursor(cursor);

        if (Objects.requireNonNull(cursor).getCount() > 0) {

            while (cursor.moveToNext()) { nameNumberHashMap.put(cursor.getString(0), cursor.getString(1)); }

            List<HashMap<String, String>> hashMapList = new ArrayList<>();
            SimpleAdapter adapter = new SimpleAdapter(

                    this.getContext(), hashMapList, R.layout.item_contact,
                    new String[]{"Name Line", "Number Line"},
                    new int[]{R.id.itemContactNameTV, R.id.itemContactNumberTV}

                    );

            for (Object o : nameNumberHashMap.entrySet()) {
                HashMap<String, String> resultHashMap = new HashMap<>();
                Map.Entry pair = (Map.Entry) o;
                resultHashMap.put("Name Line", pair.getKey().toString());
                resultHashMap.put("Number Line", pair.getValue().toString());
                hashMapList.add(resultHashMap);
            }

            listViewContacts.setAdapter(adapter);
        }
    }

    private boolean checkPermissions() {
        int res;
        String[] permissions = new String[] {Manifest.permission.READ_CONTACTS};

        for (String perms : permissions) {
            res = Objects.requireNonNull(this.getContext()).checkCallingOrSelfPermission(perms);
            if (res != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void setPermission() {
        String[] permissions = new String[] {Manifest.permission.READ_CONTACTS};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, PERMISSION_REQUEST_CODE);
        }
    }





}


