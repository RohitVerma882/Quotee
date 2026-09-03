/*
 * Copyright (C) 2026  Rohit Verma
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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
