package dev.rohitverma882.quotee.di

import android.content.Context

import androidx.room.Room

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

import dev.rohitverma882.quotee.data.quotes.local.QuotesDatabase

import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideQuotesDatabase(
        @ApplicationContext context: Context,
    ) = Room.databaseBuilder(
        context = context,
        klass = QuotesDatabase::class.java,
        name = "quotes.db",
    ).build()

    @Provides
    fun provideQuotesDao(
        database: QuotesDatabase
    ) = database.quoteDao()

    @Provides
    fun provideQuoteRemoteKeysDao(
        database: QuotesDatabase
    ) = database.quoteRemoteKeysDao()
}