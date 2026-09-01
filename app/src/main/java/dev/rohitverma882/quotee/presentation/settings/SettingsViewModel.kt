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

package dev.rohitverma882.quotee.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import dagger.hilt.android.lifecycle.HiltViewModel

import dev.rohitverma882.quotee.domain.settings.model.AppSettings
import dev.rohitverma882.quotee.domain.settings.model.ThemeMode
import dev.rohitverma882.quotee.domain.settings.repository.SettingsRepository

import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

import javax.inject.Inject

/**
 * ViewModel for [SettingsScreen].
 */
@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: SettingsRepository
) : ViewModel() {

    /**
     * The UI state for the settings screen.
     */
    val settings: StateFlow<AppSettings> = repository.settings
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AppSettings()
        )

    /**
     * Sets the theme mode.
     */
    fun setThemeMode(mode: ThemeMode) {
        viewModelScope.launch {
            repository.setThemeMode(mode)
        }
    }

    /**
     * Sets whether dynamic color is enabled.
     */
    fun setDynamicColor(enabled: Boolean) {
        viewModelScope.launch {
            repository.setDynamicColor(enabled)
        }
    }
}


