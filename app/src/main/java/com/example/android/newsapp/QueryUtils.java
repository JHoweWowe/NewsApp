package com.example.android.newsapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ultrajustin22 on 20/3/2017.
 */

public final class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getName();

    private QueryUtils () {

    }

    public static List<GuardianNewsItem> fetchGuardianData(String requestUrl) {
        //Create URL object
        URL url = createURL(requestUrl);
        //Establish a HTTPConnection, if possible
        String jsonResponse = null;
        try {
            jsonResponse = makeHTTPRequest(url);
        }
        catch (Exception e) {
            Log.e(LOG_TAG,"Exception occurred " + e);
        }
        // Extract relevant fields from the JSON response and create a list of GuardianNewsItems
        List<GuardianNewsItem> guardianNewsItems = extractFeaturefromJson(jsonResponse);

        // Return the list of GuardianNewsItems
        return guardianNewsItems;
    }

    //Create a URL object, given the String url
    private static URL createURL(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        }
        catch (MalformedURLException e) {
            Log.e(LOG_TAG,"Error forming the URL" + e);
        }
        return url;
    }
    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHTTPRequest (URL url) throws Exception {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    //Takes byte code from the InputStream and makes the code more readable through the readFromStream helper method
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static List<GuardianNewsItem> extractFeaturefromJson (String guardianNewsItemJson) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(guardianNewsItemJson)) {
            return null;
        }
        // Create an empty ArrayList that we can start adding Google Books to
        List<GuardianNewsItem> guardianNewsItemAdapter = new ArrayList<>();

        //Parsing the JSON string
        try {
            JSONObject baseJsonObject = new JSONObject(guardianNewsItemJson);
            JSONObject responseJSONObject = baseJsonObject.getJSONObject("response");
            JSONArray resultsJSONArray = responseJSONObject.getJSONArray("results");

            for (int i = 0; i < resultsJSONArray.length();i++) {
                //Retrieve current Guardian news item
                JSONObject currentGuardianNewsItem = resultsJSONArray.getJSONObject(i);
                String titleHeadline = currentGuardianNewsItem.getString("webTitle");
                String sectionName = currentGuardianNewsItem.getString("sectionName");
                String datePublished = currentGuardianNewsItem.getString("webPublicationDate");
                String webUrl = currentGuardianNewsItem.getString("webUrl");
                JSONArray tagsJSONArray = currentGuardianNewsItem.getJSONArray("tags");

                for (int j = 0; j < tagsJSONArray.length(); j++) {
                    JSONObject currentGuardianNewsItemTag = tagsJSONArray.getJSONObject(j);
                    String author = currentGuardianNewsItemTag.getString("webTitle");
                    GuardianNewsItem guardianNewsItem = new GuardianNewsItem(titleHeadline,author,datePublished,sectionName,webUrl);
                    guardianNewsItemAdapter.add(guardianNewsItem);
                }
            }
        }
        catch (JSONException e) {
            Log.e(LOG_TAG, "JSON Exception occurred: " + e);
        }
        return guardianNewsItemAdapter;
    }
}
