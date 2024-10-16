package com.example.androidapp;

public class Reservation {
    private String date;
    private String time;
    private String service;

    public Reservation(String date, String time, String service) {
        this.date = date;
        this.time = time;
        this.service = service;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getService() {
        return service;
    }
}
