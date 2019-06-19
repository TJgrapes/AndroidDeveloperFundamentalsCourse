package com.example.roomwords;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 *
 * Class that will represent our data
 * (The data for the app is words)
 *
 * We annotate this class so Room can create a database table from it
 *
 * Annotations identify how each part of the com.example.roomwordssamplecopy.Word class relates to an entry in the database.
 *
 */

@Entity(tableName = "word_table")
public class Word {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "word")
    private String mWord;


    public Word(@NonNull String word) {
        this.mWord = word;
    }

    @NonNull
    public String getWord() {
        return this.mWord;
    }
}
