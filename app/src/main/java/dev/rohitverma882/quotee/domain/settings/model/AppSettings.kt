package dev.rohitverma882.quotee.domain.settings.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AppSettings(
    @SerialName("theme_mode")
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    @SerialName("dynamic_color")
    val dynamicColor: Boolean = true
)
