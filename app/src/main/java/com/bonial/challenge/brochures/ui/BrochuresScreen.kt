package com.bonial.challenge.brochures.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.bonial.challenge.R
import com.bonial.challenge.brochures.model.Brochure
import com.bonial.challenge.ui.theme.AppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun BrochuresScreen(
    modifier: Modifier = Modifier,
    viewModel: BrochuresViewModel = koinViewModel<BrochuresViewModel>(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val uiEvents = viewModel.uiEvents

    BrochuresScreenContent(
        uiState = uiState,
        uiEvents = uiEvents,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BrochuresScreenContent(
    modifier: Modifier = Modifier,
    uiState: UiState = UiState(),
    uiEvents: Flow<BrochuresScreenEvent?> = flowOf(null),
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.app_name))
                },
            )
        },
    ) { internalPadding: PaddingValues ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(internalPadding)
                .fillMaxSize()
        ) {
            BrochuresGrid(
                brochures = uiState.brochures,
            )

            if (uiState.loadingState.isLoading) {
                CircularProgressIndicator()
            }
        }
    }

    HandleUiEvents(
        uiEvents = uiEvents,
        snackbarHostState = snackbarHostState,
    )
}

@Composable
private fun BrochuresGrid(
    brochures: List<Brochure>,
    modifier: Modifier = Modifier,
) {
    val gridColumns = GridCells.Fixed(integerResource(R.integer.grid_columns_count))
    val errorPainter = painterResource(R.drawable.outline_broken_image_24)

    LazyVerticalGrid(
        modifier = modifier
            .padding(start = 8.dp, end = 8.dp),
        columns = gridColumns,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            count = brochures.size,
            key = { index -> brochures[index].id },
        ) { index: Int ->
            Card(
                modifier = Modifier
                    .height(300.dp),
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    AsyncImage(
                        modifier = Modifier.fillMaxSize(),
                        model = brochures[index].brochureImageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        clipToBounds = true,
                        error = errorPainter,
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .background(MaterialTheme.colorScheme.surfaceContainer)
                            .align(Alignment.BottomStart),
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(8.dp)
                                .align(Alignment.BottomStart),
                            text = brochures[index].publisher.name,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HandleUiEvents(
    uiEvents: Flow<BrochuresScreenEvent?>,
    snackbarHostState: SnackbarHostState,
) {
    LaunchedEffect(Unit) {
        uiEvents.collectLatest { event ->
            when (event) {
                is BrochuresScreenEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = event.duration
                    )
                }

                else -> Unit
            }
        }
    }
}

@Preview
@Composable
fun BrochuresScreenPreview() {
    AppTheme {
        BrochuresScreenContent()
    }
}

@Preview
@Composable
fun BrochuresScreenPreviewDark() {
    AppTheme(
        darkTheme = true,
    ) {
        BrochuresScreenContent()
    }
}
