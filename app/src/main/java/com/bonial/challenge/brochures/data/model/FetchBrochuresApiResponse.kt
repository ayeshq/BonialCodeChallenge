package com.bonial.challenge.brochures.data.model

import com.google.gson.annotations.SerializedName

data class FetchBrochuresApiResponse(
    @SerializedName("_embedded")
    val wrapper: AddsWrapper,
)

data class AddsWrapper(
    @SerializedName("contents")
    val adds: List<Advertisement>
)
