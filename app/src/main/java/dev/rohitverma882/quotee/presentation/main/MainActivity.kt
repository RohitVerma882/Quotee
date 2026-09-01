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

package dev.rohitverma882.quotee.presentation.main

import android.graphics.Color
import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle

import dagger.hilt.android.AndroidEntryPoint

import dev.rohitverma882.quotee.common.extension.isSystemInDarkTheme2
import dev.rohitverma882.quotee.presentation.QuoteeApp
import dev.rohitverma882.quotee.presentation.theme.QuoteeTheme

import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

import timber.log.Timber

/**
 * The main activity of the application.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        Timber.d("onCreate")

        splashScreen.setKeepOnScreenCondition {
            viewModel.shouldKeepSplashScreen
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                combine(
                    isSystemInDarkTheme2(),
                    viewModel.uiState
                ) { isSystemDark, uiState ->
                    uiState.shouldDarkTheme(isSystemDark)
                }
                    .distinctUntilChanged()
                    .collectLatest { darkTheme ->
                        Timber.d("Applying edge-to-edge (darkTheme=$darkTheme)")
                        enableEdgeToEdge(
                            statusBarStyle = SystemBarStyle.auto(
                                Color.TRANSPARENT,
                                Color.TRANSPARENT,
                            ) { darkTheme },
                            navigationBarStyle = SystemBarStyle.auto(
                                Color.TRANSPARENT,
                                Color.TRANSPARENT,
                            ) { darkTheme },
                        )
                    }
            }
        }

        setContent {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            QuoteeTheme(
                darkTheme = uiState.shouldDarkTheme(isSystemInDarkTheme()),
                dynamicColor = uiState.dynamicColor
            ) {
                QuoteeApp()
            }
        }
    }
}