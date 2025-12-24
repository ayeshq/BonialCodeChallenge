package com.bonial.challenge.brochures.ui

import app.cash.turbine.test
import com.bonial.challenge.MainDispatcherRule
import com.bonial.challenge.brochures.model.Brochure
import com.bonial.challenge.brochures.usecases.FetchBrochuresUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class BrochuresViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(testDispatcher)

    private val fetchBrochuresUseCase: FetchBrochuresUseCase = mockk()

    private lateinit var viewModel: BrochuresViewModel

    @Test
    fun `init fetchBrochures onSuccess updates the uiState`() = runTest {
        val brochures = listOf<Brochure>(mockk(), mockk())
        coEvery {
            fetchBrochuresUseCase.invoke()
        } coAnswers {
            delay(100)
            Result.success(brochures)
        }

        viewModel = BrochuresViewModel(fetchBrochuresUseCase)

        viewModel.uiState.test {
            val initialState = awaitItem()
            assertTrue(initialState.loadingState.isLoading)
            assertTrue(initialState.brochures.isEmpty())

            val finalState = awaitItem()
            assertFalse(finalState.loadingState.isLoading)
            assertEquals(brochures, finalState.brochures)
        }
    }

    @Test
    fun `init fetchBrochures onError emits ShowSnackBar event and updates the uiState`() = runTest {
        val exception = RuntimeException("Api Error!")
        coEvery { fetchBrochuresUseCase() } returns Result.failure(exception)

        viewModel = BrochuresViewModel(fetchBrochuresUseCase)

        viewModel.uiEvents.test {
            assertIs<BrochuresScreenEvent.ShowSnackBar>(awaitItem())
        }

        assertFalse(viewModel.uiState.value.loadingState.isLoading)
    }
}
