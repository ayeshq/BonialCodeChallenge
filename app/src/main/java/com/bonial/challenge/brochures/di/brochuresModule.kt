package com.bonial.challenge.brochures.di

import com.bonial.challenge.brochures.BrochuresViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val brochuresModule = module {
    viewModelOf(::BrochuresViewModel)
}
