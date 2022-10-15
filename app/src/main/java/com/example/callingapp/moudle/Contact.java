package com.example.callingapp.moudle;

public class Contact {
    public int id;
    public String name;
    public String number;

    public Contact(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public Contact(int id, String name, String number) {
        this.name = name;
        this.number = number;
        this.id = id;
    }
}
