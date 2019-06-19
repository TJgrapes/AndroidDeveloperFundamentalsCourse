package com.example.roomwords;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import java.util.List;

/**
 *
 * The DAO interface defines the SQL queries from the database
 *
 * Room will call methods from the DAO to perform queries on the SQLite database
 *
* The annotations, such as @Insert, generate queries and associate them with the method calls
 */

@Dao
public interface WordDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Word word);

    @Query("DELETE FROM word_table")
    void deleteAll();

    @Query("SELECT * from word_table ORDER BY word ASC")
    LiveData<List<Word>> getAllWords();

    // Method that will be called to detect whether the database contains any data; if the query returns nothing, the database is empty
    @Query("SELECT * from word_table LIMIT 1")
    Word[] getAnyWord();

    // Method that will be called to delete a single word upon swiping it away
    @Delete
    void deleteWord(Word word);

}
