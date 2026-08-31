package dev.rohitverma882.quotee.domain.settings.model

import androidx.annotation.StringRes

import dev.rohitverma882.quotee.R

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class ThemeMode(
    @param:StringRes
    val nameRes: Int
) {
    @SerialName("system")
    SYSTEM(R.string.settings_theme_system),

    @SerialName("light")
    LIGHT(R.string.settings_theme_light),

    @SerialName("dark")
    DARK(R.string.settings_theme_dark)
}
