package dev.rohitverma882.quotee.data.quotes.local

import androidx.room.Entity
import androidx.room.PrimaryKey

import dev.rohitverma882.quotee.domain.quotes.model.Quote

@Entity(tableName = "quotes")
data class QuoteEntity(
    @PrimaryKey
    val id: Int,
    val content: String,
    val author: String,
)

fun QuoteEntity.toDomain() = Quote(
    id = id,
    content = content,
    author = author,
)