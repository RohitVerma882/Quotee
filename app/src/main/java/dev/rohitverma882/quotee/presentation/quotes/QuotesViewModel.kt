/*
 * Copyright (C) 2026  Rohit Verma
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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