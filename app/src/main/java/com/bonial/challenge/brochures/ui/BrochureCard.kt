package com.bonial.challenge.brochures.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.bonial.challenge.R
import com.bonial.challenge.brochures.data.model.Publisher
import com.bonial.challenge.brochures.model.Brochure
import com.bonial.challenge.ui.theme.AppTheme

@Composable
internal fun BrochureCard(
    modifier: Modifier = Modifier,
    brochure: Brochure,
    errorPainter: Painter = painterResource(R.drawable.outline_broken_image_24),
) {

    var contentScale by remember { mutableStateOf(ContentScale.Crop) }

    Card(
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = brochure.brochureImageUrl,
                contentDescription = null,
                contentScale = contentScale,
                clipToBounds = true,
                error = errorPainter,
                onLoading = {
                    contentScale = ContentScale.Inside
                },
                onError = {
                    contentScale = ContentScale.Inside
                },
                onSuccess = {
                    contentScale = ContentScale.Crop
                }
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(MaterialTheme.colorScheme.inversePrimary)
                    .align(Alignment.BottomStart),
            ) {
                Text(
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.BottomStart),
                    text = brochure.publisher.name,
                )
            }
        }
    }
}

@Preview()
@Composable
private fun BrochureCardPreview() {
    AppTheme {
        BrochureCard(
            modifier = Modifier
                .height(250.dp)
                .padding(8.dp),
            brochure = Brochure(
                id = 1,
                title = "Groceries",
                brochureImageUrl = "",
                publisher = Publisher(name = "REWE", id = "1"),
                isPremium = false,
            ),
        )
    }
}
