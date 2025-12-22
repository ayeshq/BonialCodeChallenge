package com.bonial.challenge.brochures.data.remote

class BrochuresRemoteDataSourceImpl(
    private val api: BrochuresApi
) : BrochuresRemoteDataSource {

    override suspend fun fetchBrochures() = api.fetchBrochures().wrapper.adds
}
