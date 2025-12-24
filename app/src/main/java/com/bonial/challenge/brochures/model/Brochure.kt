package com.bonial.challenge.brochures.model

import com.bonial.challenge.brochures.data.model.BrochureContent
import com.bonial.challenge.brochures.data.model.Publisher

data class Brochure(
    val id: Long,
    val title: String,
    val brochureImageUrl: String,
    val publisher: Publisher,
    val isPremium: Boolean,
)

fun BrochureContent.toBrochure(isPremium: Boolean): Brochure = Brochure (
    id = id,
    title = title,
    brochureImageUrl = brochureImageUrl,
    publisher = publisher,
    isPremium = isPremium,
)
