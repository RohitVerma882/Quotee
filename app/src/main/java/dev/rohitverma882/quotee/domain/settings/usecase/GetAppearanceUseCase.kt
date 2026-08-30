package dev.rohitverma882.quotee.domain.settings.usecase

import dev.rohitverma882.quotee.domain.settings.model.AppearanceSettings
import dev.rohitverma882.quotee.domain.settings.repository.SettingsRepository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

import javax.inject.Inject

class GetAppearanceUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    operator fun invoke(): Flow<AppearanceSettings> = repository.settings.map { it.appearance }
}
