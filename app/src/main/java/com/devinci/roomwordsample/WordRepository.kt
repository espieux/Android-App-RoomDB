package com.devinci.roomwordsample

import android.app.Application
import androidx.lifecycle.LiveData
import com.devinci.roomwordsample.data.db.WordDao
import com.devinci.roomwordsample.data.db.WordRoomDatabase
import com.devinci.roomwordsample.model.Word

class WordRepository(application: Application?) {
    private val mWordDao: WordDao?

    // Room exécute toutes les requêtes sur un thread distinct.
    // Les données LiveData observées avertiront l'observateur lorsque les données auront changé.
    val mAllWords: LiveData<List<Word>>

    init {
        val db = WordRoomDatabase.getDatabase(application!!)
        mWordDao = db!!.wordDao()
        mAllWords = mWordDao!!.getAlphabetizedWords()
    }

    // Vous devez appeler cela sur un thread non-UI ou votre application lancera une exception.
    // Room garantit que vous n'effectuez aucune opération longue sur le thread principal, bloquant l'interface utilisateur.
    fun insert(word: Word?) {
        WordRoomDatabase.databaseWriteExecutor.execute {
            mWordDao!!.insert(word!!)
        }
    }

    // Room exécute toutes les requêtes sur un thread distinct.
    // Les données LiveData observées avertiront l'observateur lorsque les données auront changé.
    fun getAllWords(): LiveData<List<Word>> {
        return mAllWords
    }

}
