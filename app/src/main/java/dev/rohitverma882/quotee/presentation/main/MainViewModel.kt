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

package dev.rohitverma882.quotee.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import dagger.hilt.android.lifecycle.HiltViewModel

import dev.rohitverma882.quotee.domain.settings.model.AppSettings
import dev.rohitverma882.quotee.domain.settings.model.ThemeMode
import dev.rohitverma882.quotee.domain.settings.repository.SettingsRepository

import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

import javax.inject.Inject

/**
 * ViewModel for [MainActivity].
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    repository: SettingsRepository
) : ViewModel() {

    /**
     * The UI state for the main screen.
     */
    val uiState: StateFlow<MainUiState> = repository.settings
        .map(MainUiState::Success)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MainUiState.Loading
        )

    /**
     * Whether the splash screen should remain visible.
     */
    val shouldKeepSplashScreen: Boolean
        get() = uiState.value is MainUiState.Loading
}

/**
 * UI state for the main activity.
 */
sealed interface MainUiState {
    /**
     * Whether dynamic color is enabled.
     */
    val dynamicColor: Boolean

    /**
     * Returns true if dark theme should be used.
     *
     * @param isSystemDark Whether the system is in dark theme.
     */
    fun shouldDarkTheme(isSystemDark: Boolean): Boolean

    /**
     * Initial loading state.
     */
    data object Loading : MainUiState {
        override val dynamicColor = true

        override fun shouldDarkTheme(isSystemDark: Boolean) = isSystemDark
    }

    /**
     * Success state with loaded settings.
     */
    data class Success(val settings: AppSettings) : MainUiState {
        override val dynamicColor = settings.dynamicColor

        override fun shouldDarkTheme(isSystemDark: Boolean) = when (settings.themeMode) {
            ThemeMode.SYSTEM -> isSystemDark
            ThemeMode.LIGHT -> false
            ThemeMode.DARK -> true
        }
    }
}
