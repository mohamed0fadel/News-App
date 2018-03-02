package com.example.android.newsfeed;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Event>>{

    private final String URL_QUERY =
            "http://content.guardianapis.com/search";
    private final int LOADER_ID = 1;
    private CustomAdapter customAdapter;
    private TextView emptystateTextView;
    private ProgressBar progressBar;
    private boolean isConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // check if there is internet connection
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        emptystateTextView = findViewById(R.id.txt_empty_state);
        progressBar = findViewById(R.id.progress);

        LoaderManager loaderManager = getLoaderManager();
        ListView listView = findViewById(R.id.news_list);
        customAdapter = new CustomAdapter(this, new ArrayList<Event>());
        listView.setAdapter(customAdapter);
        listView.setEmptyView(emptystateTextView);

        if(isConnected){
            loaderManager.initLoader(LOADER_ID, null, this);
        }else{
            emptystateTextView.setText(R.string.no_connection);
            progressBar.setVisibility(View.GONE);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Event currentEvent = customAdapter.getItem(i);
                openWebPage(currentEvent.getWebUrl());
            }
        });
    }

    @Override
    public Loader<List<Event>> onCreateLoader(int i, Bundle bundle) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        String minEventNumber = sharedPrefs.getString(
                getString(R.string.settings_min_news_key),
                getString(R.string.settings_min_news_default));

        String orderBy  = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );

        Uri baseUri = Uri.parse(URL_QUERY);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("show-tags", "contributor");
        uriBuilder.appendQueryParameter("page-size", minEventNumber);
        uriBuilder.appendQueryParameter("order-by", orderBy);
        uriBuilder.appendQueryParameter("api-key", getResources().getString(R.string.user_key));
        return new EventLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Event>> loader, List<Event> events) {
        progressBar.setVisibility(View.GONE);
        emptystateTextView.setText(R.string.no_data);
        customAdapter.clear();
        if (events != null && !events.isEmpty())
            customAdapter.addAll(events);
    }

    @Override
    public void onLoaderReset(Loader<List<Event>> loader) {
        customAdapter.clear();
    }

    /**
     * opens the event url in the users browser
     * @param url the url to open
     */
    public void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
