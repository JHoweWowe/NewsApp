package com.example.android.newsapp;

/**
 * Created by ultrajustin22 on 20/3/2017.
 */

public class GuardianNewsItem {

    private String mTitle;
    private String mAuthor;
    //I might have to change the primitive value of this variable into "long"
    private String mDate;
    private String mCategory;
    private String mWebUrl;

    public GuardianNewsItem(String title, String author, String date, String category, String webUrl) {
        mTitle = title;
        mAuthor = author;
        mDate = date;
        mCategory = category;
        mWebUrl = webUrl;
    }

    public String getTitle() {
        return mTitle;
    }
    public String getAuthor() {
        return mAuthor;
    }
    public String getDate() {
        return mDate;
    }
    public String getCategory() {
        return mCategory;
    }
    public String getWebUrl() { return mWebUrl; }
}
