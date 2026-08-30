package dev.rohitverma882.quotee.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import dev.rohitverma882.quotee.data.quotes.repository.QuotesRepositoryImpl
import dev.rohitverma882.quotee.data.settings.SettingsRepositoryImpl
import dev.rohitverma882.quotee.domain.quotes.repository.QuotesRepository
import dev.rohitverma882.quotee.domain.settings.repository.SettingsRepository

import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindSettingsRepository(
        impl: SettingsRepositoryImpl
    ): SettingsRepository

    @Binds
    @Singleton
    abstract fun bindQuotesRepository(
        impl: QuotesRepositoryImpl
    ): QuotesRepository
}
