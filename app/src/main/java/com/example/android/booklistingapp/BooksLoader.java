package com.example.android.booklistingapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by yubaarrami on 7/30/17.
 */

public class BooksLoader extends AsyncTaskLoader<List<BookStore>>{

    /** Query URL */
    private String mUrl;

    public BooksLoader(Context context, String url){
        super(context);

        mUrl = url;
    }


    @Override
    protected void onStartLoading() {
       forceLoad();

    }

    @Override
    public List<BookStore> loadInBackground() {
        if(mUrl == null) {
            return null;
        }

        List<BookStore> bookstore = QueryUtils.featchBookData(mUrl);

        return bookstore;
    }
}
