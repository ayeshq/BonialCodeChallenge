package com.bonial.challenge.brochures.data.di

import com.bonial.challenge.brochures.data.BrochuresRepository
import com.bonial.challenge.brochures.data.BrochuresRepositoryImpl
import org.koin.dsl.module

val brochuresRepositoryModule = module {
    single<BrochuresRepository> {
        BrochuresRepositoryImpl(brochuresRemoteDataSource = get())
    }
}
