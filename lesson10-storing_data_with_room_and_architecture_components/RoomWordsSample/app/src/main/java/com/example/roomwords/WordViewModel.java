package com.example.roomwords;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

/**
 *
 * ViewModel class the is responsible for holding the data for the UI
 *
 * Use the Repository to get data for this class
 *
 */

public class WordViewModel extends AndroidViewModel {


    // Reference to the Repository
    private  WordRepository mRepository;

    private LiveData<List<Word>> mAllWords;

    public WordViewModel (Application application) {
        super(application);
        mRepository = new WordRepository(application);
        mAllWords = mRepository.getAllWords();
    }


    // Cache the list of words from the Repository
    LiveData<List<Word>> getAllWords() { return mAllWords; }


    // Create a wrapper insert() method that calls the Repository's insert() method;
    // This way, the implementation of insert() is completely hidden from the UI
    public void insert(Word word) { mRepository.insert(word); }

    public void deleteAll() {mRepository.deleteAll();}

    public void deleteWord(Word word) {mRepository.deleteWord(word);}

}
