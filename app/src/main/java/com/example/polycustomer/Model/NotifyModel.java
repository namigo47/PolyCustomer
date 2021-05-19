package com.example.polycustomer.Model;

import java.io.Serializable;

public class NotifyModel implements Serializable {
    private String token;
    private String notify;

    public NotifyModel(String token, String notify) {
        this.token = token;
        this.notify = notify;
    }

    public NotifyModel(String token, Notify notify) {
    }

}
