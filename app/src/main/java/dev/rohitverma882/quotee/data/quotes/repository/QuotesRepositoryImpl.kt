package dev.rohitverma882.quotee.data.quotes.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map

import dev.rohitverma882.quotee.data.quotes.local.QuoteDao
import dev.rohitverma882.quotee.data.quotes.local.toDomain
import dev.rohitverma882.quotee.data.quotes.paging.QuotesRemoteMediator
import dev.rohitverma882.quotee.domain.quotes.model.Quote
import dev.rohitverma882.quotee.domain.quotes.repository.QuotesRepository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class QuotesRepositoryImpl @Inject constructor(
    private val dao: QuoteDao,
    private val remoteMediator: QuotesRemoteMediator
) : QuotesRepository {

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