package dev.rohitverma882.quotee.data.quotes.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface QuoteRemoteKeysDao {
    @Query(
        """
        SELECT *
        FROM quote_remote_keys
        WHERE quoteId = :quoteId
        """
    )
    suspend fun getById(quoteId: Int): QuoteRemoteKeysEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(keys: List<QuoteRemoteKeysEntity>)

    @Query("DELETE FROM quote_remote_keys")
    suspend fun clear()
}