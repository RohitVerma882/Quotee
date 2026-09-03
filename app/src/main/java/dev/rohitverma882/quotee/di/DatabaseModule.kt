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