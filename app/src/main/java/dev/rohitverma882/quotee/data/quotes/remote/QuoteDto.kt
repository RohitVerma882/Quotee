package dev.rohitverma882.quotee.data.quotes.remote

import dev.rohitverma882.quotee.data.quotes.local.QuoteEntity

import kotlinx.serialization.Serializable

@Serializable
data class QuotesResponseDto(
    val quotes: List<QuoteDto>,
    val total: Int,
    val skip: Int,
    val limit: Int,
)

@Serializable
data class QuoteDto(
    val id: Int,
    val quote: String,
    val author: String,
)

fun QuoteDto.toEntity() = QuoteEntity(
    id = id,
    content = quote,
    author = author,
)