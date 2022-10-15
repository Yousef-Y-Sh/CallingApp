package com.example.callingapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.callingapp.moudle.Contact;

import java.util.ArrayList;

public class MyDataBase extends SQLiteOpenHelper {
    final static String DB_NAME = "history_db";
    final static int DB_VERSION = 1;
    final static String TABLE_NAME = "history_tbl";
    final static String TABLE_ID = "id";
    final static String TABLE_CONTACT_NAME = "name";
    final static String TABLE_CONTACT_NUMBER = "color";

    public MyDataBase(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + TABLE_CONTACT_NAME + " TEXT , " + TABLE_CONTACT_NUMBER + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + "");
        this.onCreate(db);
    }

    public boolean INSERT_Contact(Contact contact) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_CONTACT_NAME, contact.name);
        values.put(TABLE_CONTACT_NUMBER, contact.number);
        long result = sqLiteDatabase.insert(TABLE_NAME, null, values);
        return result != -1;
    }

    public boolean UPDATE_Contact(String name, String number, String id) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_CONTACT_NAME, name);
        values.put(TABLE_CONTACT_NUMBER, number);
        String args[] = {id + ""};
        long res = sqLiteDatabase.update(TABLE_NAME, values, "" + TABLE_ID + "=?", args);
        return res > 0;
    }

    public boolean DELETE_Contact(int id) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String args[] = {id + ""};
        long res = sqLiteDatabase.delete(TABLE_NAME, "" + TABLE_ID + "=?", args);
        return res != -1;
    }

    public ArrayList<Contact> GETALLContact() {
        ArrayList<Contact> contacts = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + "", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(TABLE_CONTACT_NAME));
                String number = cursor.getString(cursor.getColumnIndexOrThrow(TABLE_CONTACT_NUMBER));
                Contact contact = new Contact(id, name, number);
                contacts.add(contact);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return contacts;
    }

    public Contact GETContact(int contactID) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String args[] = {contactID + ""};
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + TABLE_ID + "=?", args);
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(TABLE_CONTACT_NAME));
            String number = cursor.getString(cursor.getColumnIndexOrThrow(TABLE_CONTACT_NUMBER));
            Contact contact = new Contact(id, name, number);
            cursor.close();
            return contact;
        }
        return null;
    }
}
