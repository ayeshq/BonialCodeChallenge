package com.bonial.challenge.brochures.data.model

import com.google.gson.annotations.SerializedName

enum class AdvertisementContentType{
    @SerializedName("brochure")
    Brochure,

    @SerializedName("brochurePremium")
    BrochurePremium,

    @SerializedName("superBannerCarousel")
    SuperBannerCarousel,
}
