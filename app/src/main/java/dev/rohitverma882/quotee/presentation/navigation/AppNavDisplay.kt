package dev.rohitverma882.quotee.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay

import dev.rohitverma882.quotee.presentation.quotes.QuotesScreen
import dev.rohitverma882.quotee.presentation.settings.SettingsScreen

@Composable
fun AppNavDisplay(
    modifier: Modifier = Modifier,
    navigator: AppNavigator = rememberAppNavigator()
) {
    NavDisplay(
        modifier = modifier,
        backStack = navigator.backStack,
        onBack = navigator::goBack,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<AppNavKey.Quotes> {
                QuotesScreen(
                    onOpenSettings = { navigator.goToSettings() }
                )
            }

            entry<AppNavKey.Settings> {
                SettingsScreen(
                    onBack = navigator::goBack
                )
            }
        }
    )
}
