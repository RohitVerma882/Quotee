package dev.rohitverma882.quotee.domain.settings.repository

import dev.rohitverma882.quotee.domain.settings.model.AppSettings
import dev.rohitverma882.quotee.domain.settings.model.AppearanceSettings

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val settings: Flow<AppSettings>

    suspend fun updateAppearance(transform: (AppearanceSettings) -> AppearanceSettings)
}
