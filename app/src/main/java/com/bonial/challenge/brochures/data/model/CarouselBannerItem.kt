package com.bonial.challenge.brochures.data.model

import com.google.gson.annotations.SerializedName

data class CarouselBannerItem(
    @SerializedName("id")
    val id: String,

    @SerializedName("publisher")
    val publisher: Publisher,

    @SerializedName("imageUrl")
    val imageUrl: String,
)

data class CarouselBannerItemWrapper(
    @SerializedName("content")
    val content: CarouselBannerItem
)
