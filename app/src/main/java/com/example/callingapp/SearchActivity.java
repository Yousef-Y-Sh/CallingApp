package com.example.callingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.callingapp.adapter.ContactAdapter;
import com.example.callingapp.moudle.Contact;
import com.example.callingapp.utils.Utils;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private TextInputLayout textInputLayout;
    private EditText editText;
    private ImageView imageView;
    private TextView textView2;
    private RecyclerView recyclerView;
    ArrayList<Contact> searchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        textInputLayout = (TextInputLayout) findViewById(R.id.textInputLayout);
        editText = (EditText) findViewById(R.id.editText);
        imageView = (ImageView) findViewById(R.id.imageView);
        textView2 = (TextView) findViewById(R.id.textView2);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        searchList = new ArrayList<>();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                search(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN)) {
                    // Perform action on key press
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    if (searchList.isEmpty()){

                    }
                    return true;
                }
                return false;
            }
        });
    }

    void search(String text) {
        searchList.clear();
        for (Contact contact : MainActivity.list) {
            if (contact.name.contains(text) || contact.number.contains(text)) {
                searchList.add(contact);
            }
            ContactAdapter adapter = new ContactAdapter(SearchActivity.this, searchList);
            recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
            recyclerView.setAdapter(adapter);
        }
    }
}