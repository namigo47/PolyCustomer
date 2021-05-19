package com.example.polycustomer.Model;

public class Notify {
    private String title,body;

    public Notify(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public Notify() {
    }

    @Override
    public String toString() {
        return title+body;
    }
}
