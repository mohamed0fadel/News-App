package com.example.android.newsfeed;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.net.URL;
import java.util.List;

/**
 * Created by MohamedFadel on 2/2/2018.
 */

public class EventLoader extends AsyncTaskLoader<List<Event>> {
    private String url;
    public EventLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Event> loadInBackground() {
        if(url == null)
            return null;
        List<Event> eventList = QueryUtils.fetchEarthquakeData(url);
        return eventList;
    }
}
