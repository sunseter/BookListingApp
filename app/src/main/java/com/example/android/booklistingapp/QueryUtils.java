package com.example.android.booklistingapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by yubaarrami on 7/29/17.
 */

public class QueryUtils {

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils(){

    }

    public static List<BookStore> featchBookData(String requestUrl){

        URL url = createUrl(requestUrl);

        String jsonResponse = null;

        try{
            jsonResponse = makeHttpRequest(url);
        }catch (IOException e){

        }

        // Extract relevant fields from the JSON response and create a list of
        List<BookStore> bookStore = extractFeatureFromJson(jsonResponse);

        // Return the list of
        return bookStore;
    }


    private static URL createUrl(String stringUrl){

        URL url = null;
        try {
            url = new URL(stringUrl);
        }catch (MalformedURLException e){
        }

        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */

    private static String makeHttpRequest(URL url) throws IOException{

        String JSONResponse = "";

        // If the URL is null, then return early.
        if(url == null){
            return  JSONResponse;
        }

        HttpsURLConnection urlConnection = null;
        InputStream inputStream = null;

        try{
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setReadTimeout(1000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                JSONResponse = readFromStream(inputStream);
            }else{
                System.out.print("Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return JSONResponse;

    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
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

    private static List<BookStore> extractFeatureFromJson(String booksJson) {
        // If the JSON string is empty or null, then return early.
        if(TextUtils.isEmpty(booksJson)){
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        List<BookStore> bookStore = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try{
            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(booksJson);

            // Extract the JSONArray associated with the key called "features",
            // which represents a list of features (or earthquakes).
            JSONArray booksArray = baseJsonResponse.getJSONArray("items");

           /** for(int i = 0; i < booksArray.length(); i++) {

                // Get a single earthquake at position i within the list of earthquakes
                JSONObject currentBook = booksArray.getJSONObject(i);

                JSONObject items = currentBook.getJSONObject("items");

                String author = items.getString("author");

                String title = items.getString("title");

                BookStotre booKStore = new BookStotre(author,title);

                bookStore.add(booKStore);
            }*/

           for(int i =0; i < booksArray.length(); i++) {
               JSONObject currentBook = booksArray.getJSONObject(i);

               JSONObject volumeInfo = currentBook.getJSONObject("volumeInfo");
               String title = volumeInfo.getString("title");
               String authors = volumeInfo.getString("authors");

               Log.i("QueryUtils", authors + " " + "==>" + title);

               BookStore booKStore = new BookStore(authors,title);

               bookStore.add(booKStore);
           }

        }catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the GOOGLE books JSON results", e);
        }

        return bookStore;
    }

}
