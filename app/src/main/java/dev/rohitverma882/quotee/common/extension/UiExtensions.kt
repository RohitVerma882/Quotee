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

package dev.rohitverma882.quotee.common.extension

import android.content.res.Configuration

import androidx.activity.ComponentActivity
import androidx.core.util.Consumer

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.distinctUntilChanged

/**
 * Returns true if the system is currently in dark theme.
 */
val Configuration.isSystemInDarkTheme
    get() = (uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES

/**
 * Returns a [Flow] that emits true if the system is in dark theme, and updates when the configuration changes.
 */
fun ComponentActivity.isSystemInDarkTheme2() = callbackFlow {
    val listener = Consumer<Configuration> { newConfig ->
        trySend(newConfig.isSystemInDarkTheme)
    }

    trySend(resources.configuration.isSystemInDarkTheme)

    addOnConfigurationChangedListener(listener)
    awaitClose {
        removeOnConfigurationChangedListener(listener)
    }
}
    .distinctUntilChanged()
    .conflate()
