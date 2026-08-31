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

package dev.rohitverma882.quotee.data.quotes.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction

import dev.rohitverma882.quotee.data.quotes.local.QuoteDao
import dev.rohitverma882.quotee.data.quotes.local.QuoteEntity
import dev.rohitverma882.quotee.data.quotes.local.QuoteRemoteKeysDao
import dev.rohitverma882.quotee.data.quotes.local.QuoteRemoteKeysEntity
import dev.rohitverma882.quotee.data.quotes.local.QuotesDatabase
import dev.rohitverma882.quotee.data.quotes.mapper.toEntity
import dev.rohitverma882.quotee.data.quotes.remote.QuotesApi

import javax.inject.Inject

/**
 * Remote mediator for paginating quotes from the network to the local database.
 */
@OptIn(ExperimentalPagingApi::class)
class QuotesRemoteMediator @Inject constructor(
    private val api: QuotesApi,
    private val database: QuotesDatabase,
    private val dao: QuoteDao,
    private val remoteKeysDao: QuoteRemoteKeysDao
) : RemoteMediator<Int, QuoteEntity>() {

    override suspend fun initialize(): InitializeAction {
        return if (dao.count() > 0) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, QuoteEntity>,
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> {
                val keys = getRemoteKeyForFirstItem(state)
                keys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = keys != null)
            }

            LoadType.APPEND -> {
                val keys = getRemoteKeyForLastItem(state)
                keys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = keys != null)
            }
        }

        return try {
            val pageSize = state.config.pageSize
            val skip = (page - 1) * pageSize

            val response = api.getQuotes(limit = pageSize, skip = skip)
            val endReached =
                response.quotes.isEmpty() || (response.skip + response.quotes.size) >= response.total

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    dao.clear()
                    remoteKeysDao.clear()
                }

                val quotes = response.quotes.map { it.toEntity() }

                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endReached) null else page + 1

                val remoteKeys = response.quotes.map { quote ->
                    QuoteRemoteKeysEntity(
                        quoteId = quote.id,
                        prevKey = prevKey,
                        nextKey = nextKey,
                    )
                }

                dao.insertAll(quotes)
                remoteKeysDao.insertAll(remoteKeys)
            }

            MediatorResult.Success(endOfPaginationReached = endReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, QuoteEntity>): QuoteRemoteKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { remoteKeysDao.getById(it.id) }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, QuoteEntity>): QuoteRemoteKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { remoteKeysDao.getById(it.id) }
    }
}