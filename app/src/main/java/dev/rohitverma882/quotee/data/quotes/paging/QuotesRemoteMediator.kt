package dev.rohitverma882.quotee.data.quotes.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction

import dev.rohitverma882.quotee.data.quotes.local.QuoteEntity
import dev.rohitverma882.quotee.data.quotes.local.QuoteRemoteKeysEntity
import dev.rohitverma882.quotee.data.quotes.local.QuotesDatabase
import dev.rohitverma882.quotee.data.quotes.remote.QuotesApi
import dev.rohitverma882.quotee.data.quotes.remote.toEntity

@OptIn(ExperimentalPagingApi::class)
class QuotesRemoteMediator(
    private val api: QuotesApi,
    private val database: QuotesDatabase,
) : RemoteMediator<Int, QuoteEntity>() {

    private val quoteDao = database.quoteDao()
    private val remoteKeysDao = database.quoteRemoteKeysDao()

    override suspend fun initialize(): InitializeAction {
        return if (quoteDao.count() > 0) {
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
                    remoteKeysDao.clear()
                    quoteDao.clear()
                }

                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endReached) null else page + 1

                val remoteKeys = response.quotes.map { quote ->
                    QuoteRemoteKeysEntity(
                        quoteId = quote.id,
                        prevKey = prevKey,
                        nextKey = nextKey,
                    )
                }

                val quotes = response.quotes.map { it.toEntity() }

                remoteKeysDao.insertAll(remoteKeys)
                quoteDao.insertAll(quotes)
            }

            MediatorResult.Success(endOfPaginationReached = endReached)
        } catch (error: Throwable) {
            MediatorResult.Error(error)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, QuoteEntity>): QuoteRemoteKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { remoteKeysDao.remoteKeys(it.id) }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, QuoteEntity>): QuoteRemoteKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { remoteKeysDao.remoteKeys(it.id) }
    }
}