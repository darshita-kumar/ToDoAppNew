package com.example.todoapp;

public class Event
{
    String title, description, date, time, notification;

    public Event(String title, String desc, String date, String time, String notif)
    {
        this.time = time;
        this.title = title;
        this.date = date;
        this.description = desc;
        this.notification = notif;
    }
}
