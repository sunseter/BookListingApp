package com.example.android.booklistingapp;

/**
 * Created by yubaarrami on 8/1/17.
 */

public class BookStore {

    private String mTitle;
    private String mAuthor;

    /** constructor for the public class BookStore*/
    public BookStore(String title, String author) {

        mAuthor = author;
        mTitle = title;
    }

    /** getter method for mTitle*/
    public String getmTitle(){
        return mTitle;
    }

    /** getter message for mAuthor*/
    public String getmAuthor(){
        return mAuthor;
    }
}
