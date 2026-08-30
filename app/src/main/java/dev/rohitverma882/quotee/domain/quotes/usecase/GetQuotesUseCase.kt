package dev.rohitverma882.quotee.domain.quotes.usecase

import androidx.paging.PagingData

import dev.rohitverma882.quotee.domain.quotes.model.Quote
import dev.rohitverma882.quotee.domain.quotes.repository.QuotesRepository

import kotlinx.coroutines.flow.Flow

import javax.inject.Inject

class GetQuotesUseCase @Inject constructor(
    private val repository: QuotesRepository,
) {
    operator fun invoke(): Flow<PagingData<Quote>> {
        return repository.getQuotes()
    }
}