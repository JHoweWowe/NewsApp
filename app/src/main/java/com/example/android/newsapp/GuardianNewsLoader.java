package com.example.android.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by ultrajustin22 on 23/3/2017.
 */

public class GuardianNewsLoader extends AsyncTaskLoader <List<GuardianNewsItem>> {

    private static final String LOG_TAG = GuardianNewsLoader.class.getName();
    private String mURL;

    //Construct the new loader
    public GuardianNewsLoader(Context context, String url) {
        super(context);
        mURL = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<GuardianNewsItem> loadInBackground() {
        if (mURL == null) {
            return null;
        }
        //Perform network request, parse the JSON response, and show the response
        List<GuardianNewsItem> result = QueryUtils.fetchGuardianData(mURL);
        return result;
    }
}
