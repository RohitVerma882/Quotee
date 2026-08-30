package dev.rohitverma882.quotee.domain.settings.usecase

import dev.rohitverma882.quotee.domain.settings.model.AppearanceSettings
import dev.rohitverma882.quotee.domain.settings.repository.SettingsRepository

import javax.inject.Inject

class UpdateAppearanceUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(transform: (AppearanceSettings) -> AppearanceSettings) {
        repository.updateAppearance(transform)
    }
}
