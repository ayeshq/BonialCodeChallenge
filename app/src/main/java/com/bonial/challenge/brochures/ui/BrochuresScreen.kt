package com.bonial.challenge.brochures.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bonial.challenge.R
import com.bonial.challenge.brochures.data.model.BrochureContent
import com.bonial.challenge.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun BrochuresScreen(
    modifier: Modifier = Modifier,
    viewModel: BrochuresViewModel = koinViewModel<BrochuresViewModel>(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    BrochuresScreenContent(
        uiState = uiState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BrochuresScreenContent(
    modifier: Modifier = Modifier,
    uiState: UiState = UiState(),
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { stringResource(R.string.app_name) },
            )
        }
    ) { internalPadding: PaddingValues ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(internalPadding).fillMaxSize()
        ) {
            BrochuresGrid(
                brochures = uiState.brochures,
            )

            if (uiState.loadingState.isLoading) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
private fun BrochuresGrid(
    brochures: List<BrochureContent>,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(integerResource(R.integer.grid_columns_count)),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        items(
            count = brochures.size,
            key = { index -> brochures[index].id },
        ) { index: Int ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
            ) {
                Text(
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterHorizontally),
                    text = "Brochure Number: $index",
                )
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
