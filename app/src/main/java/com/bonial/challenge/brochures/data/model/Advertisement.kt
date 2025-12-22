package com.bonial.challenge.brochures.data.model

import com.google.gson.annotations.SerializedName

data class Advertisement(
    @SerializedName("contentType")
    val contentType: AdvertisementContentType,

    @SerializedName("content")
    val content: AdvertisementContent,
)
