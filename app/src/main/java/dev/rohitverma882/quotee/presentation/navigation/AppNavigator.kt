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

package dev.rohitverma882.quotee.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack

/**
 * Navigator class to handle application navigation using Navigation 3.
 */
@Stable
class AppNavigator(
    val backStack: NavBackStack<NavKey>
) {
    private fun goTo(key: NavKey) {
        if (backStack.lastOrNull() != key) {
            backStack.add(key)
        }
    }

    /**
     * Navigates back.
     */
    fun goBack() {
        if (backStack.size > 1) {
            backStack.removeLastOrNull()
        }
    }

    /**
     * Navigates to the settings screen.
     */
    fun goToSettings() {
        goTo(AppNavKey.Settings)
    }
}

/**
 * Creates and remembers an [AppNavigator].
 */
@Composable
fun rememberAppNavigator(
    backStack: NavBackStack<NavKey> = rememberNavBackStack(AppNavKey.Quotes)
): AppNavigator {
    return remember(backStack) { AppNavigator(backStack) }
}
