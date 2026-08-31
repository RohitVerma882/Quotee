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

/**
 * Module for providing network-related dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    /**
     * Provides the [Retrofit] instance for quotes API.
     */
    @Provides
    @Singleton
    fun provideQuotesRetrofit(json: Json): Retrofit = Retrofit.Builder()
        .baseUrl(QuotesApi.BASE_URL)
        .addConverterFactory(
            json.asConverterFactory("application/json".toMediaType())
        ).build()

    /**
     * Provides the [QuotesApi] instance.
     */
    @Provides
    @Singleton
    fun provideQuotesApi(retrofit: Retrofit): QuotesApi {
        return retrofit.create(QuotesApi::class.java)
    }
}