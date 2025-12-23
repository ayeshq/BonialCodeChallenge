package com.bonial.challenge.brochures.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonial.challenge.brochures.usecases.FetchBrochuresUseCase
import kotlinx.coroutines.launch

class BrochuresViewModel(
    val fetchBrochures: FetchBrochuresUseCase,
) : ViewModel() {

    init {
        loadBrochures()
    }

    private fun loadBrochures() {
        viewModelScope.launch {
            fetchBrochures()
                .fold(
                    onSuccess = {
                        println(it)
                    },
                    onFailure = {
                        println(it)
                    },
                )
        }
    }
}
