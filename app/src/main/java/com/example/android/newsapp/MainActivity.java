package com.example.android.newsapp;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<GuardianNewsItem>>{

    private String guardianAPI = "https://content.guardianapis.com/search?q=computer&api-key=test&show-tags=contributor";
    private static final int EARTHQUAKE_LOADER_ID = 1;
    private GuardianNewsItemAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find a reference to the {@link ListView} in the layout
        ListView guardianNewsListView = (ListView) findViewById(R.id.list);

        // Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = new GuardianNewsItemAdapter(this, new ArrayList<GuardianNewsItem>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        guardianNewsListView.setAdapter(mAdapter);

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(EARTHQUAKE_LOADER_ID,null,this);

        //DELETE IF NECESSARY- I need to ensure when the user clicks on one of the list items, it will create an intent to the corresponding link
        guardianNewsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Find the current News that is being clicked on
                GuardianNewsItem currentGuardianNewsItem = mAdapter.getItem(position);
                Uri guardianNewsItemUri = Uri.parse(currentGuardianNewsItem.getWebUrl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, guardianNewsItemUri);
                startActivity(websiteIntent);
            }
        });
    }

    @Override
    public Loader<List<GuardianNewsItem>> onCreateLoader(int id, Bundle args) {
        return new GuardianNewsLoader(this,guardianAPI);
    }

    @Override
    public void onLoadFinished(Loader<List<GuardianNewsItem>> loader, List<GuardianNewsItem> data) {
        //Clear the list of news articles first
        mAdapter.clear();
        //Show the data list
        if ((data != null) && (!data.isEmpty())) {
            mAdapter.addAll(data);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<GuardianNewsItem>> loader) {
        //Loader supposedly needs to be reset, so clear data anyways
        mAdapter.clear();

    }
}
