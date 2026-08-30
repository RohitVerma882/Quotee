package dev.rohitverma882.quotee.presentation.features.quotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.rohitverma882.quotee.domain.quotes.model.Quote
import dev.rohitverma882.quotee.domain.quotes.usecase.GetQuotesUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class QuotesViewModel @Inject constructor(
    getQuotes: GetQuotesUseCase,
) : ViewModel() {

    val quotes: Flow<PagingData<Quote>> =
        getQuotes()
            .cachedIn(viewModelScope)
}