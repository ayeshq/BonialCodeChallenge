package com.bonial.challenge.brochures.data

import com.bonial.challenge.brochures.data.model.Advertisement
import com.bonial.challenge.brochures.data.remote.BrochuresRemoteDataSource

internal class BrochuresRepositoryImpl(
    private val brochuresRemoteDataSource: BrochuresRemoteDataSource
) : BrochuresRepository {

    override suspend fun fetchBrochures(): Result<List<Advertisement>> = runCatching {
        brochuresRemoteDataSource.fetchBrochures()
    }
}
