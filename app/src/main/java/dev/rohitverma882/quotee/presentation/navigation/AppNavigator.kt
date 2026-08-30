package dev.rohitverma882.quotee.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack

@Stable
class AppNavigator(
    val backStack: NavBackStack<NavKey>
) {

    fun goTo(route: NavKey) {
        backStack.add(route)
    }

    fun goBack() {
        backStack.removeLastOrNull()
    }
}

@Composable
fun rememberAppNavigator(
    backStack: NavBackStack<NavKey> = rememberNavBackStack(AppNavKey.Quotes)
): AppNavigator {
    return remember(backStack) {
        AppNavigator(backStack = backStack)
    }
}
