package com.example.roomwords;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * Room is a database layer on top of the SQLite database
 * <p>
 * Uses the DAO to issue queries to its database
 */

@Database(entities = {Word.class}, version = 1, exportSchema = false)
public abstract class WordRoomDatabase extends RoomDatabase {


    // Define the DAOs that work with the database
    // Provide an abstract "getter" method for each DAO; This connects the DAOs to Room
    public abstract WordDao wordDao();

    private static WordRoomDatabase INSTANCE;

    public static WordRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (WordRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WordRoomDatabase.class, "word_database")
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        /**
         *
         * Delete alll the content in the database and repopulate it whenever the app is started
         *
         * @param db
         */

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        // Get a Dao instance to call the methods to delete and repopulate the database
        private final WordDao mDao;

        // Create data that will be used to initialize/populate the database
        String[] words = {"dolphin", "crocodile", "cobra"};


        // Constructor which initializes the WordDao
        public PopulateDbAsync(WordRoomDatabase db) {

            mDao = db.wordDao();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Only initialize the database with words if it is empty
            if (mDao.getAnyWord().length < 1) {
                // Delete the data in the database and repopulate it every time the app is opened
                mDao.deleteAll();

                for (int i = 0; i < words.length; i++) {

                    Word word = new Word(words[i]);

                    mDao.insert(word);

                }

            }

            return null;

        }
    }
}


