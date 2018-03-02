package com.example.android.newsfeed;

import java.net.URL;

/**
 * Created by MohamedFadel on 2/2/2018.
 */

public class Event {
    private String title;
    private String type;
    private String date;
    private String contriputer;
    private String webUrl;

    public Event(String title, String type, String date, String contriputer, String webUrl) {
        this.title = title;
        this.type = type;
        this.date = date;
        this.contriputer = contriputer;
        this.webUrl = webUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getContriputer() {
        return contriputer;
    }

    public String getWebUrl() {
        return webUrl;
    }
}
