package com.devinci.roomwordsample.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.devinci.roomwordsample.model.Word


@Dao
interface WordDao {
    // Permettre l'insertion du même mot plusieurs fois en passant
    // une stratégie de résolution de conflits.
    @Insert
    fun insert(word: Word)

    @Query("DELETE FROM word_table")
    fun deleteAll()

    @Query("SELECT * FROM word_table ORDER BY word ASC")
    fun getAlphabetizedWords(): LiveData<List<Word>>
}