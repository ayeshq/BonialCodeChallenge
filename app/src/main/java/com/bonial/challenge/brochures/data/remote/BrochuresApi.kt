package com.bonial.challenge.brochures.data.remote

import com.bonial.challenge.brochures.data.model.FetchBrochuresApiResponse
import retrofit2.http.GET

interface BrochuresApi {

    @GET("shelf.json/")
    suspend fun fetchBrochures(): FetchBrochuresApiResponse
}
