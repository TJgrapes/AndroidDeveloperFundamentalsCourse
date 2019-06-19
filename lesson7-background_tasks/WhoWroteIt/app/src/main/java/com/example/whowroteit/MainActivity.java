package com.example.whowroteit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText mBookInput;
    private TextView mTitleText;
    private TextView mAuthorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBookInput = (EditText)findViewById(R.id.bookInput);
        mTitleText = (TextView)findViewById(R.id.titleText);
        mAuthorText = (TextView)findViewById(R.id.authorText);

    }

    public void searchBooks(View view) {

        // Get the search string from the input field
        String queryString = mBookInput.getText().toString();

        // Hide the keyboard when the user taps the search books button
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        if(inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS );
        }

        // Manage the network state and the empty search field case
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }

        // Launch the background task using the FetchBook class and calling execute(), passing in the queryString
        // Here, we pass in the text views from the Main Activity to use them in the FetchBook class
        // This will start another thread of execution, so we can add code after this line to continue doing work on the main (UI) thread

        //Add a test around the call to the FetchBook task and TextView updates to ensure that the network connection exists, that the network is connected, and that a query string is available.
        if (networkInfo != null && networkInfo.isConnected()
                && queryString.length() != 0) {

            new FetchBook(mTitleText, mAuthorText).execute(queryString);
            // Show a loading message while the request is executing
            // Clear the text field from the previous search
            mAuthorText.setText("");
            mTitleText.setText(R.string.loading);

        }

        else {
            if (queryString.length() == 0) {
                mAuthorText.setText("");
                mTitleText.setText(R.string.no_search_term);
            } else {
                mAuthorText.setText("");
                mTitleText.setText(R.string.no_network);
            }


        }


    }
}
