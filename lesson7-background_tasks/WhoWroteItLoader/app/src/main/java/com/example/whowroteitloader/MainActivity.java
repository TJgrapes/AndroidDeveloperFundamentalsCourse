package com.example.whowroteitloader;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private EditText mBookInput;
    private TextView mTitleText;
    private TextView mAuthorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBookInput = (EditText) findViewById(R.id.bookInput);
        mTitleText = (TextView) findViewById(R.id.titleText);
        mAuthorText = (TextView) findViewById(R.id.authorText);

        // Reconnect the loader on a configuration change
        if(getSupportLoaderManager().getLoader(0)!=null){
            getSupportLoaderManager().initLoader(0,null,this);
        }

    }

    public void searchBooks(View view) {

        // Get the search string from the input field
        String queryString = mBookInput.getText().toString();

        // Hide the keyboard when the user taps the search books button
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

        // Manage the network state and the empty search field case
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }

        //

        //Add a test around the call to the Loader and TextView updates to ensure that the network connection exists, that the network is connected, and that a query string is available.
        if (networkInfo != null && networkInfo.isConnected()
                && queryString.length() != 0) {

            Bundle queryBundle = new Bundle();
            queryBundle.putString("queryString", queryString);
            getSupportLoaderManager().restartLoader(0, queryBundle, this);
            // Show a loading message while the request is executing
            // Clear the text field from the previous search
            mAuthorText.setText("");
            mTitleText.setText(R.string.loading);

        } else {
            if (queryString.length() == 0) {
                mAuthorText.setText("");
                mTitleText.setText(R.string.no_search_term);
            } else {
                mAuthorText.setText("");
                mTitleText.setText(R.string.no_network);
            }


        }


    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int i, @Nullable Bundle bundle) {
        String queryString = "";

        if (bundle != null) {
            queryString = bundle.getString("queryString");
        }

        return new BookLoader(this, queryString);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {

        try {

            // Obtain the JSON array of items from the JSON string
            // The JSON string is a lengthy JSON response, that we must parse to extract only the data we want
            JSONObject jsonObject = new JSONObject(data);
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            // Initialize the variables used for the parsing loop
            int i = 0;
            String title = null;
            String authors = null;

            // Iterate through the itemsArray, checking each book for title and author information.
            // With each loop, test to see if both an author and a title are found, and if so, exit the loop
            // This way only entries with both a title and author will be displayed

            while (i < itemsArray.length() && authors == null && title == null) {

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
            if (title != null && authors != null) {

                mTitleText.setText(title);
                mAuthorText.setText(authors);

            }

            // If there are no items with both a valid author and a valid title, set the title TextView to "no results" and clear the author TextView
            else {
                mTitleText.setText(R.string.no_results);
                mAuthorText.setText("");
            }

            // If onPostExecute does not receive a proper JSON string, update the UI to show failed results and print the error to the log
        } catch (JSONException e) {
            mTitleText.setText("onPostExecute did not receive a proper JSON string");
            mAuthorText.setText("");
            e.printStackTrace();
        }
    }


    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

}

