package dev.rohitverma882.quotee.domain.quotes.repository

import androidx.paging.PagingData

import dev.rohitverma882.quotee.domain.quotes.model.Quote

import kotlinx.coroutines.flow.Flow

interface QuotesRepository {
    fun getQuotes(): Flow<PagingData<Quote>>
}