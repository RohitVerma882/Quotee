package dev.rohitverma882.quotee.presentation.navigation

import androidx.navigation3.runtime.NavKey

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed interface AppNavKey : NavKey {
    @Serializable
    @SerialName("quotes")
    data object Quotes : NavKey

    @Serializable
    @SerialName("settings")
    data object Settings : NavKey
}

sealed interface SettingsNavKey : AppNavKey {
    @Serializable
    @SerialName("appearance")
    data object Appearance : SettingsNavKey
}
