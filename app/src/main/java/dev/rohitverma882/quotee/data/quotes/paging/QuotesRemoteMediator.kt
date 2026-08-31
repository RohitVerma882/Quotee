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
import dev.rohitverma882.quotee.data.quotes.remote.QuotesApi
import dev.rohitverma882.quotee.data.quotes.remote.toEntity

import javax.inject.Inject

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
        } catch (exception: Exception) {
            MediatorResult.Error(exception)
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