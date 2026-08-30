package dev.rohitverma882.quotee.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import dev.rohitverma882.quotee.common.ApplicationScope
import dev.rohitverma882.quotee.common.IoDispatcher

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoroutineModule {
    @IoDispatcher
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope(
        @IoDispatcher dispatcher: CoroutineDispatcher
    ) = CoroutineScope(dispatcher + SupervisorJob())
}