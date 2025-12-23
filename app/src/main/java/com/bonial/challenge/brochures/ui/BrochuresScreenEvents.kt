package com.bonial.challenge.brochures.ui

import androidx.compose.material3.SnackbarDuration

sealed interface BrochuresScreenEvents {

    data class ShowSnackBar(
        val message: String,
        val duration: SnackbarDuration = SnackbarDuration.Short
    ) : BrochuresScreenEvents

}
