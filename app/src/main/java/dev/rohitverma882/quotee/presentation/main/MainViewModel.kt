package dev.rohitverma882.quotee.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.rohitverma882.quotee.domain.settings.model.AppearanceSettings
import dev.rohitverma882.quotee.domain.settings.model.ThemeMode
import dev.rohitverma882.quotee.domain.settings.usecase.GetAppearanceUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getAppearanceUseCase: GetAppearanceUseCase
) : ViewModel() {

    val uiState: StateFlow<MainUiState> =
        getAppearanceUseCase()
            .map(MainUiState::Success)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = MainUiState.Loading
            )

    val shouldKeepSplashScreen get() = uiState.value is MainUiState.Loading
}

sealed interface MainUiState {
    val dynamicColor get() = false
    val themeMode get() = ThemeMode.SYSTEM

    fun isDarkTheme(isSystemInDarkTheme: Boolean) = isSystemInDarkTheme

    data object Loading : MainUiState

    data class Success(val appearance: AppearanceSettings) : MainUiState {
        override val dynamicColor get() = appearance.dynamicColor
        override val themeMode get() = appearance.themeMode

        override fun isDarkTheme(isSystemInDarkTheme: Boolean) = when (themeMode) {
            ThemeMode.SYSTEM -> isSystemInDarkTheme
            ThemeMode.LIGHT -> false
            ThemeMode.DARK -> true
        }
    }
}
