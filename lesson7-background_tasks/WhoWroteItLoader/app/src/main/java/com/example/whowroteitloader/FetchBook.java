package com.example.whowroteitloader;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

// Class that handle connecting to the Google Books API and retrieve the Author for the book title in the main Activity
// Type parameters are <String, Void, String>; String because the query is a String (which will be the input to the doInBackground() method, Void because we are not updating progress, and String because the JSON response that we return will be a String from the Books API
public class FetchBook extends AsyncTask<String, Void, String> {

    private WeakReference<TextView> mTitleText;
    private WeakReference<TextView> mAuthorText;

    // Constructor for the class that includes TextView view from the MainActivity
    // The arguments to this constructor will be the textViews from the Main Activity

    public FetchBook(TextView titleText, TextView authorText) {
        this.mTitleText = new WeakReference<>(titleText);
        this.mAuthorText = new WeakReference<>(authorText);
    }


    /// Background Networking methods

    // The doInBackground method is automatically setup with the correct input parameter type
    @Override
    protected String doInBackground(String... strings) {

        // Call the getBookInfo method, passing in the first value in the strings array, which is the search term entered by the user
        return NetworkUtils.getBookInfo(strings[0]);
    }

    // The doInBackground() method returns the JSON response string, which is passed into the onPostExecute() method
    // Here, we parse the JSON response to extract the information we want to use in the UI
    // We also update the UI in onPostExecute
    // So in onPostExecute(), we process the response and also update the UI
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {

            // Obtain the JSON array of items from the JSON string
            JSONObject jsonObject = new JSONObject(s);
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            // Initialize the variables used for the parsing loop
            int i = 0;
            String title = null;
            String authors = null;

            // Iterate through the itemsArray, checking each book for title and author information.
            // With each loop, test to see if both an author and a title are found, and if so, exit the loop
            // This way only entries with both a title and author will be displayed

            while(i<itemsArray.length() && authors == null && title == null) {

                // Get the current item information
                JSONObject book = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                // Try to get the author and title from the current item, and catch if either field is empty and move on
                try {
                    title = volumeInfo.getString("title");
                    authors = volumeInfo.getString("authors");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Move on to the next item
                i++;

            }

            // If a matching response is found, update the UI with that response
            if(title != null && authors != null) {

                mTitleText.get().setText(title);
                mAuthorText.get().setText(authors);

            }

            // If there are no items with both a valid author and a valid title, set the title TextView to "no results" and clear the author TextView
            else {
                mTitleText.get().setText(R.string.no_results);
                mAuthorText.get().setText("");
            }

        // If onPostExecute does not receive a proper JSON string, update the UI to show failed results and print the error to the log
        } catch (JSONException e) {
            mTitleText.get().setText("onPostExecute did not receive a proper JSON string");
            mAuthorText.get().setText("");
            e.printStackTrace();
        }
    }


}
