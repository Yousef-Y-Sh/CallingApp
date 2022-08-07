package com.example.callingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.callingapp.adapter.ContactAdapter;
import com.example.callingapp.moudle.Contact;
import com.example.callingapp.utils.Utils;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ArrayList<Contact> list;
    ArrayList<Contact> searchList;

    private androidx.appcompat.widget.SearchView search;
    private RecyclerView recycle;
    private EditText searchEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycle = (RecyclerView) findViewById(R.id.recycle);
        searchEditText = (EditText) findViewById(R.id.search);
        list = new ArrayList<>();
        searchList = new ArrayList<>();
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchList.clear();
                for (Contact contact : list){
                    if (contact.name.contains(charSequence.toString()) || contact.phone.contains(charSequence.toString())){
                        searchList.add(contact);
                    }
                    ContactAdapter adapter = new ContactAdapter(MainActivity.this, searchList);
                    recycle.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recycle.setAdapter(adapter);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        checkPermission();
    }

    void checkPermission() {
        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.CALL_PHONE
                ).withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            getContactList();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void getContactList() {
        list.clear();
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndexOrThrow(
                        ContactsContract.Contacts.DISPLAY_NAME));
                String photo = cur.getString(cur.getColumnIndexOrThrow(
                        ContactsContract.Contacts.PHOTO_THUMBNAIL_URI));

                if (cur.getInt(cur.getColumnIndexOrThrow(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndexOrThrow(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        list.add(new Contact(name, phoneNo, photo));
                    }
                    getData();
                    pCur.close();
                }
            }
        }
        if (cur != null) {
            cur.close();
        }
    }

    private void getData() {
        if (!list.isEmpty()) {
            Collections.sort(list, new Comparator<Contact>() {
                @Override
                public int compare(Contact contact, Contact t1) {
                    return contact.name.compareTo(t1.name);
                }
            });
            ContactAdapter adapter = new ContactAdapter(MainActivity.this, list);
            recycle.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            recycle.setAdapter(adapter);
        }
    }



}