package com.bonial.challenge.brochures.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonial.challenge.brochures.data.BrochuresRepository
import kotlinx.coroutines.launch

class BrochuresViewModel(
    val brochuresRepository: BrochuresRepository,
) : ViewModel() {

    init {
        fetchBrochures()
    }

    private fun fetchBrochures() {
        viewModelScope.launch {
            brochuresRepository
                .fetchBrochures()
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
