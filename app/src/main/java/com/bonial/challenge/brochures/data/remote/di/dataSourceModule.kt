package com.bonial.challenge.brochures.data.remote.di

import com.bonial.challenge.brochures.data.remote.BrochuresApi
import com.bonial.challenge.brochures.data.remote.BrochuresRemoteDataSource
import com.bonial.challenge.brochures.data.remote.BrochuresRemoteDataSourceImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val brochuresRemoteDataSourceModule = module {
    single<BrochuresRemoteDataSource> {
        BrochuresRemoteDataSourceImpl(get())
    }

    single<BrochuresApi> {
        val baseUrl = "https://mobile-s3-test-assets.aws-sdlc-bonial.com/"

        val httpClient: OkHttpClient =
            OkHttpClient
                .Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()

        Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BrochuresApi::class.java)
    }
}
