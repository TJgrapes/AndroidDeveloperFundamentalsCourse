package com.example.whowroteitloader;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

// A helper class that will open the internet connection and query the Books API
public class NetworkUtils {

    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();


    // Base URL for Books API.
    private static final String BOOK_BASE_URL =  "https://www.googleapis.com/books/v1/volumes?";
    // Parameter for the search string.
    private static final String QUERY_PARAM = "q";
    // Parameter that limits search results.
    private static final String MAX_RESULTS = "maxResults";
    // Parameter to filter by print type.
    private static final String PRINT_TYPE = "printType";

    // Method that takes the search term and returns the JSON String response
    static String getBookInfo(String queryString) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String bookJSONString = null;

        // Build the URI and issue the query

        try {

            //// Open connection and request String from Google Books API to find the author of a book

            /// Build the Uri to use in the request and convert it to a Url to use in the request

            // Build the uri using the base url and appending the search query (the string entered by the user) and append other search parameters
            Uri builtURI = Uri.parse(BOOK_BASE_URL).buildUpon().appendQueryParameter(QUERY_PARAM,queryString ).appendQueryParameter(MAX_RESULTS,"10" ).appendQueryParameter(PRINT_TYPE,"books" ).build();

            // Convert the Uri to a Url
            // The Url object takes a String as the input paramter
            URL requestURL = new URL(builtURI.toString());


            /// Open the connection to the url

            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            /// Set up to read the response from the url

            InputStream inputStream = urlConnection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            // Use a StringBuilder to hold the incoming response
            StringBuilder builder = new StringBuilder();

            // Read the input line by line while there is still input

            String line;
            while ((line = reader.readLine()) != null) {

                builder.append(line);
                // Since it's JSON, adding a newline isn't necessary (it won't
                // affect parsing) but it does make debugging a *lot* easier
                // if you print out the completed buffer for debugging.
                builder.append("\n");

                // Check the string to see if there is existing response content
                if(builder.length() == 0) {

                    return null;

                }

                // Convert the stringBuilder object to a String
                bookJSONString = builder.toString();

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            /// Close the connection and the buffered reader

            if(urlConnection != null) {
                urlConnection.disconnect();
            }
            if(reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // Print the JSON response to the Log
        Log.d(LOG_TAG,bookJSONString );

        return bookJSONString;
    }

}
