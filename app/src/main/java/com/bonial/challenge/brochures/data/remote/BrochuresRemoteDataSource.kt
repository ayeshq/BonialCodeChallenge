package com.bonial.challenge.brochures.data.remote

import com.bonial.challenge.brochures.data.model.Advertisement

interface BrochuresRemoteDataSource {

    suspend fun fetchBrochures(): List<Advertisement>
}
