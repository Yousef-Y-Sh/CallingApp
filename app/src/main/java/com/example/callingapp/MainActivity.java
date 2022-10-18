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
import android.view.View;
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
    public static ArrayList<Contact> list;
    private RecyclerView recycle;
    private ImageView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycle = (RecyclerView) findViewById(R.id.recycle);
        search = (ImageView) findViewById(R.id.search);
        list = new ArrayList<>();
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils._Intent(MainActivity.this, SearchActivity.class);
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
        Cursor cur = cr.query(Uri.parse("content://icc/adn"),  //sim contact
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String name = cur.getString(cur.getColumnIndexOrThrow("name"));
                String number = cur.getString(cur.getColumnIndexOrThrow("number"));
                Contact contact = new Contact(name, number);
                list.add(contact);
            }
        }
        getData();
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