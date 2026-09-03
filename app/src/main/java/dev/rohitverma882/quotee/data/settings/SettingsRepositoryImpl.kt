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

package dev.rohitverma882.quotee.data.settings

import androidx.datastore.core.DataStore

import dev.rohitverma882.quotee.domain.settings.model.AppSettings
import dev.rohitverma882.quotee.domain.settings.model.ThemeMode
import dev.rohitverma882.quotee.domain.settings.repository.SettingsRepository

import kotlinx.coroutines.flow.Flow

import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<AppSettings>,
) : SettingsRepository {
    override val settings: Flow<AppSettings> = dataStore.data

    override suspend fun setThemeMode(mode: ThemeMode) {
        dataStore.updateData { currentSettings ->
            currentSettings.copy(themeMode = mode)
        }
    }

    override suspend fun setDynamicColor(enabled: Boolean) {
        dataStore.updateData { currentSettings ->
            currentSettings.copy(dynamicColor = enabled)
        }
    }
}
