package dev.rohitverma882.quotee.data.settings

import androidx.datastore.core.DataStore
import dev.rohitverma882.quotee.domain.settings.model.AppSettings
import dev.rohitverma882.quotee.domain.settings.model.AppearanceSettings
import dev.rohitverma882.quotee.domain.settings.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<AppSettings>
) : SettingsRepository {
    override val settings: Flow<AppSettings> = dataStore.data

    override suspend fun updateAppearance(transform: (AppearanceSettings) -> AppearanceSettings) {
        dataStore.updateData { currentSettings ->
            currentSettings.copy(
                appearance = transform(currentSettings.appearance)
            )
        }
    }
}
