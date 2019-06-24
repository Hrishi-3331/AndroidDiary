package com.hrishi_3331.devstudio3331.diary;

import android.content.Context;

public class Event {

    private String title;
    private String filename;
    private String date;
    private String content;
    private String time;

    public Event(Context context, String title, String filename, String date, String time) {
        this.title = title;
        this.filename = filename;
        this.date = date;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
