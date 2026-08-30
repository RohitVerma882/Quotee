package dev.rohitverma882.quotee.presentation.main

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.util.Consumer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import dev.rohitverma882.quotee.presentation.QuoteeApp
import dev.rohitverma882.quotee.presentation.theme.QuoteeTheme
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var themeSettings by mutableStateOf(
            ThemeSettings(
                darkTheme = resources.configuration.isSystemInDarkTheme,
                dynamicColor = MainUiState.Loading.dynamicColor
            )
        )

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                combine(
                    isSystemInDarkTheme(),
                    viewModel.uiState
                ) { systemDark, uiState ->
                    ThemeSettings(
                        darkTheme = uiState.isDarkTheme(systemDark),
                        dynamicColor = uiState.dynamicColor
                    )
                }
                    .onEach { themeSettings = it }
                    .map { it.darkTheme }
                    .distinctUntilChanged()
                    .collect { darkTheme ->
                        enableEdgeToEdge(
                            statusBarStyle = SystemBarStyle.auto(
                                Color.TRANSPARENT,
                                Color.TRANSPARENT
                            ) { darkTheme },
                            navigationBarStyle = SystemBarStyle.auto(
                                Color.TRANSPARENT,
                                Color.TRANSPARENT
                            ) { darkTheme }
                        )
                    }
            }
        }

        splashScreen.setKeepOnScreenCondition { viewModel.shouldKeepSplashScreen }

        setContent {
            QuoteeTheme(
                darkTheme = themeSettings.darkTheme,
                dynamicColor = themeSettings.dynamicColor
            ) {
                QuoteeApp()
            }
        }
    }
}

private val Configuration.isSystemInDarkTheme
    get() = (uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES

private fun ComponentActivity.isSystemInDarkTheme() = callbackFlow {
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

@Immutable
data class ThemeSettings(
    val darkTheme: Boolean,
    val dynamicColor: Boolean
)
