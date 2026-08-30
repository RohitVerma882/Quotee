package dev.rohitverma882.quotee.data.quotes.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        QuoteEntity::class,
        QuoteRemoteKeysEntity::class,
    ],
    version = 1,
    exportSchema = true,
)
abstract class QuotesDatabase : RoomDatabase() {
    abstract fun quoteDao(): QuoteDao

    abstract fun quoteRemoteKeysDao(): QuoteRemoteKeysDao
}