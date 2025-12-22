package com.bonial.challenge.brochures.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BrochuresRemoteDataSourceImpl : BrochuresRemoteDataSource {

    private val api = initClient()

    override suspend fun fetchBrochures() = api.fetchBrochures().wrapper.adds

    private fun initClient(): BrochuresApi {
        val baseUrl = "https://mobile-s3-test-assets.aws-sdlc-bonial.com/"

        val httpClient: OkHttpClient =
            OkHttpClient
                .Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()

        return Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BrochuresApi::class.java)
    }
}
