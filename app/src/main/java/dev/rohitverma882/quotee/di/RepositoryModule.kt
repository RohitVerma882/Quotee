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

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import dev.rohitverma882.quotee.data.quotes.repository.QuotesRepositoryImpl
import dev.rohitverma882.quotee.data.settings.SettingsRepositoryImpl
import dev.rohitverma882.quotee.domain.quotes.repository.QuotesRepository
import dev.rohitverma882.quotee.domain.settings.repository.SettingsRepository

import javax.inject.Singleton

/**
 * Module for binding repository implementations to their interfaces.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    /**
     * Binds [SettingsRepositoryImpl] to [SettingsRepository].
     */
    @Binds
    @Singleton
    abstract fun bindSettingsRepository(
        impl: SettingsRepositoryImpl
    ): SettingsRepository

    /**
     * Binds [QuotesRepositoryImpl] to [QuotesRepository].
     */
    @Binds
    @Singleton
    abstract fun bindQuotesRepository(
        impl: QuotesRepositoryImpl
    ): QuotesRepository
}
