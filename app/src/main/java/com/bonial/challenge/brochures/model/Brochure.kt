package com.bonial.challenge.brochures.model

import androidx.compose.runtime.Immutable
import com.bonial.challenge.brochures.data.model.BrochureContent

@Immutable
data class Brochure(
    val id: Long,
    val title: String,
    val brochureImageUrl: String,
    val publisher: String,
    val isPremium: Boolean,
    val distance: Double,
)

fun BrochureContent.toBrochure(isPremium: Boolean): Brochure = Brochure (
    id = id,
    title = title,
    brochureImageUrl = brochureImageUrl,
    publisher = publisher.name,
    isPremium = isPremium,
    distance = distance,
)
