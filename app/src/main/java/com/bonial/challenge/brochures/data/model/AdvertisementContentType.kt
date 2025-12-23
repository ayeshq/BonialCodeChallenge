package com.bonial.challenge.brochures.data.model

import com.google.gson.annotations.SerializedName

enum class AdvertisementContentType {
    @SerializedName("brochure")
    Brochure,

    @SerializedName("brochurePremium")
    BrochurePremium,

    @SerializedName("superBannerCarousel")
    SuperBannerCarousel,

    Unknown,
}

fun String.toAdvertisementContentType(): AdvertisementContentType {
    return when(this) {
        "brochure" -> AdvertisementContentType.Brochure
        "brochurePremium" -> AdvertisementContentType.BrochurePremium
        "superBannerCarousel" -> AdvertisementContentType.SuperBannerCarousel
        else -> AdvertisementContentType.Unknown
    }
}
