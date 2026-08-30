package dev.rohitverma882.quotee.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import dev.rohitverma882.quotee.data.quotes.remote.QuotesApi

import kotlinx.serialization.json.Json

import okhttp3.MediaType.Companion.toMediaType

import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideQuotesRetrofit(json: Json): Retrofit = Retrofit.Builder()
        .baseUrl(QuotesApi.BASE_URL)
        .addConverterFactory(
            json.asConverterFactory("application/json".toMediaType())
        ).build()

    @Provides
    @Singleton
    fun provideQuotesApi(retrofit: Retrofit): QuotesApi {
        return retrofit.create(QuotesApi::class.java)
    }
}