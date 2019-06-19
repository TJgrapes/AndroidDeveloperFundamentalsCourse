package com.example.simpleasynctask;

import android.os.AsyncTask;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

public class SimpleAsyncTask extends AsyncTask<Void,Void,String> {

    private WeakReference<TextView> mTextView;


    SimpleAsyncTask(TextView tv) {
        mTextView = new WeakReference<>(tv);
    }


    @Override
    protected String doInBackground(Void... voids) {

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "Awake at last after sleeping for 5 seconds";

    }

    @Override
    protected void onPostExecute(String result) {
        mTextView.get().setText(result);
    }


}
