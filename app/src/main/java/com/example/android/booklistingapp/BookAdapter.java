package com.example.android.booklistingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yubaarrami on 7/30/17.
 */

public class BookAdapter extends ArrayAdapter<BookStore> {



    public BookAdapter( Context context,List<BookStore> books) {

        super(context, 0, books);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_items, parent, false);
        }

        BookStore currentBook = getItem(position);


        TextView author =  listItemView.findViewById(R.id.author_text);

        author.setText(currentBook.getmAuthor());

        TextView title =  listItemView.findViewById(R.id.title_text);

        title.setText(currentBook.getmTitle());
        return listItemView;
    }
}
