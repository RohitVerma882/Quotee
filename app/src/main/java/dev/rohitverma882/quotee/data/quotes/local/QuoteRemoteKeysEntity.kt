package dev.rohitverma882.quotee.data.quotes.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quote_remote_keys")
data class QuoteRemoteKeysEntity(
    @PrimaryKey
    val quoteId: Int,
    val prevKey: Int?,
    val nextKey: Int?,
)