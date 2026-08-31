package dev.rohitverma882.quotee.presentation.quotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn

import dagger.hilt.android.lifecycle.HiltViewModel

import dev.rohitverma882.quotee.domain.quotes.model.Quote
import dev.rohitverma882.quotee.domain.quotes.repository.QuotesRepository

import kotlinx.coroutines.flow.Flow

import javax.inject.Inject

@HiltViewModel
class QuotesViewModel @Inject constructor(
    repository: QuotesRepository
) : ViewModel() {

    val quotes: Flow<PagingData<Quote>> = repository.getQuotes()
        .cachedIn(viewModelScope)
}