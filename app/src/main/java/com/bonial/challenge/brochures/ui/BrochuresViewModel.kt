package com.bonial.challenge.brochures.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonial.challenge.brochures.usecases.FetchBrochuresUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BrochuresViewModel(
    val fetchBrochures: FetchBrochuresUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        loadBrochures()
    }

    private fun loadBrochures() {
        showHideLoadingIndicator(true)

        viewModelScope.launch {
            fetchBrochures()
                .fold(
                    onSuccess = { brochures ->
                        _uiState.update {
                            it.copy(brochures = brochures)
                        }
                        showHideLoadingIndicator(false)
                    },
                    onFailure = {
                        println(it)
                    },
                )
        }
    }

    private fun showHideLoadingIndicator(isLoading: Boolean) {
        _uiState.update {
            it.copy(
                loadingState = it.loadingState.copy(
                    isLoading = isLoading,
                )
            )
        }
    }
}
