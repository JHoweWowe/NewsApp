package com.example.android.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * For every GuardianNewsItem article information that is displayed on the screen, it will create a list item layout
 * for that particular article
 */

public class GuardianNewsItemAdapter extends ArrayAdapter<GuardianNewsItem> {

    /*
     * Creates a GuardianNewsItemAdapter object
     * @param context of the app
     * @param googleBooks is the list of GuardianNewsItem, which is the data source of the adapter
     */
    public GuardianNewsItemAdapter(Context context, List<GuardianNewsItem> guardianNewsItems) {
        super(context,0,guardianNewsItems);
    }
    /**
     * Returns a list item view that displays information about the Google Book at the given position
     * in the list of googleBooks.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.guardian_news_list_item, parent, false);
        }
        //Find the current GuardianNewsItem object the given position of the googleBooks list
        GuardianNewsItem currentGuardianNewsItem = getItem(position);

        TextView guardianNewsItem_title_textView = (TextView) listItemView.findViewById(R.id.news_headline);
        guardianNewsItem_title_textView.setText(currentGuardianNewsItem.getTitle());

        TextView guardianNewsItem_author_textView = (TextView) listItemView.findViewById(R.id.news_author);
        guardianNewsItem_author_textView.setText(currentGuardianNewsItem.getAuthor());

        TextView guardianNewsItem_date_textView = (TextView) listItemView.findViewById(R.id.news_date);
        guardianNewsItem_date_textView.setText(currentGuardianNewsItem.getDate());

        TextView guardianNewsItem_category_textView = (TextView) listItemView.findViewById(R.id.news_category);
        guardianNewsItem_category_textView.setText(currentGuardianNewsItem.getCategory());

        return listItemView;
    }
}
