package com.bonial.challenge.brochures.usecases.di

import com.bonial.challenge.brochures.usecases.FetchBrochuresUseCase
import com.bonial.challenge.brochures.usecases.FetchBrochuresUseCaseImpl
import org.koin.dsl.module

val fetchBrochuresModule = module {
    factory<FetchBrochuresUseCase> {
        FetchBrochuresUseCaseImpl(get())
    }
}
