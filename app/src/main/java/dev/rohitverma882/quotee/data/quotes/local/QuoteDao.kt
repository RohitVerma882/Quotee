package dev.rohitverma882.quotee.data.quotes.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface QuoteDao {
    @Query("SELECT * FROM quotes ORDER BY id ASC")
    fun pagingSource(): PagingSource<Int, QuoteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(quotes: List<QuoteEntity>)

    @Query("DELETE FROM quotes")
    suspend fun clear()

    @Query("SELECT COUNT(*) FROM quotes")
    suspend fun count(): Int
}