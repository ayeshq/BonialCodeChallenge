package com.bonial.challenge.brochures.data.remote

import com.bonial.challenge.brochures.data.model.Advertisement
import com.bonial.challenge.brochures.data.model.FetchBrochuresApiResponse

interface BrochuresRemoteDataSource {

    suspend fun fetchBrochures(): List<Advertisement>
}
