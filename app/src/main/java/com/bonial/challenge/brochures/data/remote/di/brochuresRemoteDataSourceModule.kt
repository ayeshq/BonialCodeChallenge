package com.bonial.challenge.brochures.data.remote.di

import com.bonial.challenge.brochures.data.remote.BrochuresRemoteDataSource
import com.bonial.challenge.brochures.data.remote.BrochuresRemoteDataSourceImpl
import org.koin.dsl.module

val brochuresRemoteDataSourceModule = module {
    single<BrochuresRemoteDataSource> {
        BrochuresRemoteDataSourceImpl()
    }
}
