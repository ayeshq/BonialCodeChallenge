package com.bonial.challenge.brochures.data.remote

import com.bonial.challenge.brochures.data.model.Advertisement

class BrochuresRemoteDataSourceImpl(
    private val api: BrochuresApi
) : BrochuresRemoteDataSource {

    override suspend fun fetchBrochures(): List<Advertisement> =
        api.fetchBrochures().wrapper.adds
}
