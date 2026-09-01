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

package dev.rohitverma882.quotee.presentation.quotes

import android.content.ClipData
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import dev.rohitverma882.quotee.R
import dev.rohitverma882.quotee.domain.quotes.model.Quote
import kotlinx.coroutines.launch

/**
 * Screen that displays a list of quotes.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuotesScreen(
    modifier: Modifier = Modifier,
    viewModel: QuotesViewModel = hiltViewModel(),
    onOpenSettings: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val quotes = viewModel.quotes.collectAsLazyPagingItems()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            QuotesTopBar(
                scrollBehavior = scrollBehavior,
                titleRes = R.string.quotes_title,
                openSettings = onOpenSettings
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        QuotesContent(
            quotes = quotes,
            snackbarHostState = snackbarHostState,
            innerPadding = innerPadding
        )
    }
}

/**
 * The top bar for the quotes screen.
 *
 * @param modifier The modifier to be applied to the top bar.
 * @param scrollBehavior The scroll behavior for the top bar.
 * @param titleRes The resource ID for the title text.
 * @param openSettings Callback when the settings button is clicked.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun QuotesTopBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
    titleRes: Int,
    openSettings: () -> Unit
) {
    LargeTopAppBar(
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        title = { Text(stringResource(titleRes)) },
        actions = {
            IconButton(onClick = openSettings) {
                Icon(Icons.Outlined.Settings, contentDescription = null)
            }
        }
    )
}

/**
 * The main content of the quotes screen.
 *
 * @param quotes The paging items of quotes.
 * @param snackbarHostState The state of the snackbar host.
 * @param innerPadding The padding values from the scaffold.
 * @param modifier The modifier to be applied to the content.
 */
@Composable
private fun QuotesContent(
    quotes: LazyPagingItems<Quote>,
    snackbarHostState: SnackbarHostState,
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    val clipboard = LocalClipboard.current
    val scope = rememberCoroutineScope()
    val layoutDirection = LocalLayoutDirection.current
    val listState = rememberLazyListState()

    // Enforce top-position anchor when first batch of items is loaded
    LaunchedEffect(quotes.itemCount) {
        if (quotes.itemCount > 0 && (listState.firstVisibleItemIndex == 0) && (listState.firstVisibleItemScrollOffset == 0)) {
            listState.scrollToItem(0)
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = innerPadding.calculateStartPadding(layoutDirection) + 16.dp,
                top = innerPadding.calculateTopPadding() + 16.dp,
                end = innerPadding.calculateEndPadding(layoutDirection) + 16.dp,
                bottom = innerPadding.calculateBottomPadding() + 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(
                count = quotes.itemCount,
                key = quotes.itemKey { it.id }
            ) { index ->
                quotes[index]?.let { quote ->
                    QuoteCard(
                        quote = quote,
                        onCopy = {
                            scope.launch {
                                val clipData = ClipData.newPlainText("Quote", quote.displayContent)
                                clipboard.setClipEntry(ClipEntry(clipData))
                                snackbarHostState.currentSnackbarData?.dismiss()
                                snackbarHostState.showSnackbar("Quote copied to clipboard")
                            }
                        }
                    )
                }
            }

            when (quotes.loadState.append) {
                is LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                is LoadState.Error -> {
                    item {
                        Button(
                            onClick = quotes::retry,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Retry")
                        }
                    }
                }

                is LoadState.NotLoading -> Unit
            }
        }

        // Handle initial/refresh loading
        val isInitialLoading = quotes.loadState.refresh is LoadState.Loading ||
                (quotes.loadState.mediator?.refresh is LoadState.Loading && quotes.itemCount == 0)

        if (isInitialLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

        // Handle error states
        if (quotes.loadState.refresh is LoadState.Error && quotes.itemCount == 0) {
            val errorState = quotes.loadState.refresh as LoadState.Error
            ErrorContent(
                error = errorState.error,
                onRetry = quotes::retry,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // Handle empty states - only show after loading has finished and itemCount is definitely 0
        val isNotLoading = quotes.loadState.refresh is LoadState.NotLoading &&
                (quotes.loadState.mediator?.refresh is LoadState.NotLoading || quotes.loadState.mediator == null)

        val isActuallyEmpty =
            isNotLoading && quotes.itemCount == 0 && quotes.loadState.append.endOfPaginationReached

        if (isActuallyEmpty) {
            Text(
                text = "No quotes available",
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

/**
 * The content to display when an error occurs.
 *
 * @param error The error that occurred.
 * @param onRetry Callback to retry the operation.
 * @param modifier The modifier to be applied to the error content.
 */
@Composable
private fun ErrorContent(
    error: Throwable,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = error.message ?: "Unable to load quotes",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
        )

        Button(
            onClick = onRetry,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Retry")
        }
    }
}