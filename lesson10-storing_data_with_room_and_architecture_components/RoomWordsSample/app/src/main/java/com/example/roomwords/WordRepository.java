package com.example.roomwords;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 *
 * The repository class handles multiple data source
 *
 * The repository makes the calls to the DAO by using the DAO instances
 *
 * When calling the DAO methods, it is important to note that unless the method returns a LiveData instance (which automatically runs queries on a separate thread), the operation cannot be performed on the app's main thread.
 *
 * So we add wrappers to the methods in this class that call the DAO methods to perform those methods in the background
 *
 */

public class WordRepository {

    private WordDao mWordDao;
    // Store all words from the Word table in the database
    private LiveData<List<Word>> mAllWords;

    WordRepository(Application application) {
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getAllWords();
    }

    LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    /**
     *
     * Use AsyncTask to execute the insert method on a separate thread
     *
     * @param word
     */

    public void insert (Word word) {
        new insertAsyncTask(mWordDao).execute(word);
    }

    private static class insertAsyncTask extends AsyncTask<Word, Void, Void> {

        private WordDao mAsyncTaskDao;

        insertAsyncTask(WordDao dao) {
            mAsyncTaskDao = dao;
        }

        /**
         *
         * Here, in the doInBackground method, we see how the repository makes the method calls to the DAO, and then the DAO will execute the appropriate SQL query
         *
         * @param params
         * @return
         */

        @Override
        protected Void doInBackground(final Word... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void deleteAll() {

        new deleteAllWordsAsyncTask(mWordDao).execute();

    }

    private static class deleteAllWordsAsyncTask extends AsyncTask<Void,Void,Void> {

        private WordDao mAsyncTaskDao;

        deleteAllWordsAsyncTask(WordDao dao) {

            mAsyncTaskDao = dao;

        }


        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }


    public void deleteWord(Word word) {

        new deleteWordAsyncTask(mWordDao).execute(word);

    }

    private static class deleteWordAsyncTask extends AsyncTask<Word, Void, Void> {

        private WordDao mAsyncTaskDao;


        public deleteWordAsyncTask(WordDao dao) {

            mAsyncTaskDao = dao;

        }

        @Override
        protected Void doInBackground(Word... words) {

            mAsyncTaskDao.deleteWord(words[0]);

            return null;
        }
    }
}


