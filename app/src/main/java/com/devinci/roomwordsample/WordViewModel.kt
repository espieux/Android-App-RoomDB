package com.devinci.roomwordsample

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.devinci.roomwordsample.data.db.WordRoomDatabase
import com.devinci.roomwordsample.model.Word

class WordViewModel(application: Application) : AndroidViewModel(application) {
    private val mRepository: WordRepository
    val mAllWords: LiveData<List<Word>>

    init {
        mRepository = WordRepository(application)
        mAllWords = mRepository.mAllWords
    }

    fun insert(word: Word?) {
        mRepository.insert(word)
    }

    fun deleteWord(word: Word?) {
        mRepository.deleteWord(word)
    }

    fun getAllWords(): LiveData<List<Word>> {
        return mAllWords
    }
}
