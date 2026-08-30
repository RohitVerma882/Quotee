package dev.rohitverma882.quotee.presentation.features.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.rohitverma882.quotee.domain.settings.model.AppearanceSettings
import dev.rohitverma882.quotee.domain.settings.model.ThemeMode
import dev.rohitverma882.quotee.domain.settings.usecase.GetAppearanceUseCase
import dev.rohitverma882.quotee.domain.settings.usecase.UpdateAppearanceUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    getAppearanceUseCase: GetAppearanceUseCase,
    private val updateAppearanceUseCase: UpdateAppearanceUseCase
) : ViewModel() {

    val uiState: StateFlow<SettingsState> = getAppearanceUseCase()
        .map { appearance ->
            SettingsState(
                appearance = AppearanceState(
                    theme = appearance.themeMode,
                    dynamicColor = appearance.dynamicColor,
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SettingsState()
        )

    fun setTheme(theme: ThemeMode) {
        updateAppearance { it.copy(themeMode = theme) }
    }

    fun setDynamicColor(enabled: Boolean) {
        updateAppearance { it.copy(dynamicColor = enabled) }
    }

    private fun updateAppearance(transform: (AppearanceSettings) -> AppearanceSettings) {
        viewModelScope.launch {
            updateAppearanceUseCase(transform)
        }
    }
}
