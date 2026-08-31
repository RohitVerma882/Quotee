package dev.rohitverma882.quotee.presentation.navigation

import androidx.navigation3.runtime.NavKey

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed interface AppNavKey : NavKey {
    @Serializable
    @SerialName("quotes")
    data object Quotes : AppNavKey

    @Serializable
    @SerialName("settings")
    data object Settings : AppNavKey
}
