package dev.rohitverma882.quotee.presentation.features.settings

import androidx.compose.runtime.Immutable
import dev.rohitverma882.quotee.domain.settings.model.ThemeMode

@Immutable
data class SettingsState(
    val appearance: AppearanceState = AppearanceState()
)

@Immutable
data class AppearanceState(
    val theme: ThemeMode = ThemeMode.SYSTEM,
    val dynamicColor: Boolean = true,
)
