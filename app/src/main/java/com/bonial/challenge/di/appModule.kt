package com.bonial.challenge.di

import com.bonial.challenge.brochures.di.brochuresModule
import org.koin.dsl.module

val appModule = module {
    includes(brochuresModule)
}
