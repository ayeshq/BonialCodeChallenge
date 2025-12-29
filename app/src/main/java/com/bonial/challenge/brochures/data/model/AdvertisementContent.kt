package com.bonial.challenge.brochures.data.model

import com.google.gson.annotations.SerializedName

sealed class AdvertisementContent

data class BrochureContent(
    @SerializedName("id")
    val id: Long,

    @SerializedName("title")
    val title: String,

    @SerializedName("brochureImage")
    val brochureImageUrl: String,

    @SerializedName("publisher")
    val publisher: Publisher,

    @SerializedName("distance")
    val distance: Double,
): AdvertisementContent()

data class SuperBannerCarouselContent(
    @SerializedName("content")
    val content: List<CarouselBannerItemWrapper>
): AdvertisementContent()

data object UnknownAdvertisementContent: AdvertisementContent()
