package com.bonial.challenge.brochures.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bonial.challenge.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun BrochuresScreen(
    modifier: Modifier = Modifier,
    viewModel: BrochuresViewModel = koinViewModel<BrochuresViewModel>(),
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            modifier = Modifier,
            text = "Bonial App",
        )
    }
}

@Preview
@Composable
fun BrochuresScreenPreview() {
    AppTheme {
        BrochuresScreen()
    }
}

@Preview
@Composable
fun BrochuresScreenPreviewDark() {
    AppTheme(
        darkTheme = true,
    ) {
        BrochuresScreen()
    }
}
