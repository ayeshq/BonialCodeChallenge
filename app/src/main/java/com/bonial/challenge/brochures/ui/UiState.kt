package com.bonial.challenge.brochures.ui

import com.bonial.challenge.brochures.model.Brochure

data class UiState(
    val loadingState: LoadingState = LoadingState(),
    val brochures: List<Brochure> = listOf(),
)

data class LoadingState(
    val isLoading: Boolean = false,
)
