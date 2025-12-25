package com.bonial.challenge.brochures.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bonial.challenge.R
import com.bonial.challenge.brochures.data.model.Publisher
import com.bonial.challenge.brochures.model.Brochure
import com.bonial.challenge.ui.theme.AppTheme
import kotlinx.coroutines.flow.Flow
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

    val columnsCount = integerResource(R.integer.grid_columns_count)
    val gridColumns = remember(columnsCount) { GridCells.Fixed(columnsCount) }
    val errorPainter = painterResource(R.drawable.outline_broken_image_24)

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
                gridColumns = gridColumns,
                fullGridSpan = GridItemSpan(columnsCount),
                singleColumnSpan = GridItemSpan(1),
                errorPainter = errorPainter,
                modifier = Modifier.align(Alignment.TopCenter),
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
    gridColumns: GridCells,
    fullGridSpan: GridItemSpan,
    singleColumnSpan: GridItemSpan,
    errorPainter: Painter,
    modifier: Modifier = Modifier,
) {
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
            span = { index ->
                if (brochures[index].isPremium) {
                    fullGridSpan
                } else {
                    singleColumnSpan
                }
            }
        ) { index: Int ->
            BrochureCard(
                brochure = brochures[index],
                errorPainter = errorPainter,
                modifier = Modifier
                    .height(300.dp),
            )
        }
    }
}

@Composable
private fun HandleUiEvents(
    uiEvents: Flow<BrochuresScreenEvent?>,
    snackbarHostState: SnackbarHostState,
) {
    LaunchedEffect(Unit) {
        uiEvents.collect { event ->
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

private val brochures = listOf(
    Brochure(
        id = 1,
        title = "Groceries",
        brochureImageUrl = "",
        publisher = Publisher(name = "REWE", id = "1"),
        isPremium = false,
    ),
    Brochure(
        id = 2,
        title = "Supplies",
        brochureImageUrl = "",
        publisher = Publisher(name = "EDEKA", id = "2"),
        isPremium = false,
    ),
    Brochure(
        id = 3,
        title = "Supermarket",
        brochureImageUrl = "",
        publisher = Publisher(name = "KAUF", id = "3"),
        isPremium = true,
    )
)

@Preview
@Composable
fun BrochuresScreenPreview() {
    AppTheme {
        BrochuresScreenContent(
            uiState = UiState().copy(
                brochures = brochures,
            ),
        )
    }
}

@PreviewScreenSizes
@Composable
fun BrochuresScreenPreviewDark() {
    AppTheme(
        darkTheme = true,
    ) {
        BrochuresScreenContent(
            uiState = UiState().copy(
                brochures = brochures,
            ),
        )
    }
}
