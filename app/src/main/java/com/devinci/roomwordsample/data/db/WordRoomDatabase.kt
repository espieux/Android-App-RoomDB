package com.devinci.roomwordsample.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.devinci.roomwordsample.model.Word
import java.util.concurrent.Executors

@Database(entities = [Word::class], version = 1, exportSchema = false)
abstract class WordRoomDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao?

    companion object {
        @Volatile
        private var INSTANCE: WordRoomDatabase? = null
        private const val NUMBER_OF_THREADS = 4
        val databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS)

        val sRoomDatabaseCallback: Callback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // Si vous souhaitez conserver les données au redémarrage de l'application
                // commentez le bloc suivant
                databaseWriteExecutor.execute {
                    // Remplir la base de données en arrière-plan
                    // Si vous voulez commencer avec plus de mots, il su it de les ajouter.
                    val dao = INSTANCE!!.wordDao()
                    dao!!.deleteAll()
                    var word =
                        Word(0, "Hello")
                    dao.insert(word)
                    word = Word(0, "World")
                    dao.insert(word)
                }
            }
        }

        fun getDatabase(context: Context): WordRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(WordRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = databaseBuilder(
                            context.applicationContext,
                            WordRoomDatabase::class.java,
                            "word_database"
                        ).fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }
}
