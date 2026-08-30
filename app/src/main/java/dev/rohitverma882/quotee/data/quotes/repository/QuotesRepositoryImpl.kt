package dev.rohitverma882.quotee.data.quotes.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map

import dev.rohitverma882.quotee.data.quotes.local.QuotesDatabase
import dev.rohitverma882.quotee.data.quotes.local.toDomain
import dev.rohitverma882.quotee.data.quotes.paging.QuotesRemoteMediator
import dev.rohitverma882.quotee.data.quotes.remote.QuotesApi
import dev.rohitverma882.quotee.domain.quotes.model.Quote
import dev.rohitverma882.quotee.domain.quotes.repository.QuotesRepository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class QuotesRepositoryImpl @Inject constructor(
    private val api: QuotesApi,
    private val database: QuotesDatabase,
) : QuotesRepository {

    override fun getQuotes(): Flow<PagingData<Quote>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                initialLoadSize = 20,
                prefetchDistance = 1,
                enablePlaceholders = false,
            ),
            remoteMediator = QuotesRemoteMediator(
                api = api,
                database = database,
            ),
            pagingSourceFactory = {
                database.quoteDao().pagingSource()
            },
        ).flow.map { pagingData ->
            pagingData.map { entity ->
                entity.toDomain()
            }
        }
    }
}