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

package dev.rohitverma882.quotee.data.quotes.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map

import dev.rohitverma882.quotee.data.quotes.local.QuoteDao
import dev.rohitverma882.quotee.data.quotes.mapper.toDomain
import dev.rohitverma882.quotee.data.quotes.paging.QuotesRemoteMediator
import dev.rohitverma882.quotee.domain.quotes.model.Quote
import dev.rohitverma882.quotee.domain.quotes.repository.QuotesRepository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

import javax.inject.Inject

/**
 * Implementation of [QuotesRepository] using Paging 3.
 */
@OptIn(ExperimentalPagingApi::class)
class QuotesRepositoryImpl @Inject constructor(
    private val dao: QuoteDao,
    private val remoteMediator: QuotesRemoteMediator
) : QuotesRepository {

    /**
     * Returns a [Flow] of paginated [Quote]s.
     */
    override fun getQuotes(): Flow<PagingData<Quote>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 1,
                enablePlaceholders = false,
            ),
            remoteMediator = remoteMediator,
            pagingSourceFactory = {
                dao.pagingSource()
            }
        )
            .flow.map { pagingData ->
                pagingData.map { entity ->
                    entity.toDomain()
                }
            }
    }
}