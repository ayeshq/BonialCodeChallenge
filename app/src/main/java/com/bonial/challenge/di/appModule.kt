package com.bonial.challenge.di

import com.bonial.challenge.brochures.data.di.brochuresRepositoryModule
import com.bonial.challenge.brochures.data.remote.di.brochuresRemoteDataSourceModule
import com.bonial.challenge.brochures.di.brochuresModule
import com.bonial.challenge.brochures.usecases.di.fetchBrochuresModule
import org.koin.dsl.module

val appModule = module {
    includes(
        brochuresModule,
        brochuresRemoteDataSourceModule,
        brochuresRepositoryModule,
        fetchBrochuresModule,
    )
}
