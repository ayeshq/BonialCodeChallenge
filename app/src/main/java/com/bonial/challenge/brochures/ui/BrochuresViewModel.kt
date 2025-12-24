package com.bonial.challenge.brochures.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonial.challenge.brochures.usecases.FetchBrochuresUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BrochuresViewModel(
    val fetchBrochures: FetchBrochuresUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEvents = Channel<BrochuresScreenEvent?>(Channel.UNLIMITED)
    val uiEvents: Flow<BrochuresScreenEvent?> = _uiEvents.receiveAsFlow()

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
                    onFailure = { error ->
                        _uiEvents.send(BrochuresScreenEvent.ShowSnackBar(
                            message = "Error fetching brochures:\n${error.message}"
                        ))

                        showHideLoadingIndicator(false)
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
