package com.hrishi_3331.devstudio3331.diary;

import android.content.Context;

public class Event {

    private String title;
    private String filename;
    private String date;
    private String content;

    public Event(Context context, String title, String filename, String date) {
        this.title = title;
        this.filename = filename;
        this.date = date;
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
}
