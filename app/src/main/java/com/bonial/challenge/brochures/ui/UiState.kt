package com.bonial.challenge.brochures.ui

import com.bonial.challenge.brochures.data.model.BrochureContent

data class UiState(
    val loadingState: LoadingState = LoadingState(),
    val brochures: List<BrochureContent> = listOf(),
)

data class LoadingState(
    val isLoading: Boolean = false,
)
