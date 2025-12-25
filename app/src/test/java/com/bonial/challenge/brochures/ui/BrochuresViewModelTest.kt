package com.bonial.challenge.brochures.ui

import app.cash.turbine.test
import com.bonial.challenge.MainDispatcherRule
import com.bonial.challenge.brochures.data.BrochuresRepository
import com.bonial.challenge.brochures.data.model.Advertisement
import com.bonial.challenge.brochures.data.model.AdvertisementContentType
import com.bonial.challenge.brochures.data.model.BrochureContent
import com.bonial.challenge.brochures.data.model.Publisher
import com.bonial.challenge.brochures.usecases.FetchBrochuresUseCase
import com.bonial.challenge.brochures.usecases.FetchBrochuresUseCaseImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class BrochuresViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(testDispatcher)

    private val brochuresRepository: BrochuresRepository = mockk()
    private lateinit var fetchBrochuresUseCase: FetchBrochuresUseCase

    private lateinit var viewModel: BrochuresViewModel

    @Test
    fun `init fetchBrochures onSuccess updates the uiState and filters by distance`() = runTest {
        fetchBrochuresUseCase = FetchBrochuresUseCaseImpl(brochuresRepository)

        coEvery {
            brochuresRepository.fetchBrochures()
        } coAnswers {
            delay(100)
            Result.success(Ads)
        }

        viewModel = BrochuresViewModel(fetchBrochuresUseCase)

        viewModel.uiState.test {
            val initialState = awaitItem()
            assertTrue(initialState.loadingState.isLoading)
            assertTrue(initialState.brochures.isEmpty())

            val finalState = awaitItem()
            assertFalse(finalState.loadingState.isLoading)
            assertFalse(finalState.brochures.isEmpty())
            assertTrue {
                finalState.brochures.count {
                    it.distance > 5.0
                } == 0
            }
        }
    }

    @Test
    fun `init fetchBrochures onError emits ShowSnackBar event and updates the uiState`() = runTest {
        fetchBrochuresUseCase = FetchBrochuresUseCaseImpl(brochuresRepository)

        val exception = RuntimeException("Api Error!")
        coEvery { brochuresRepository.fetchBrochures() } returns Result.failure(exception)

        viewModel = BrochuresViewModel(fetchBrochuresUseCase)

        viewModel.uiEvents.test {
            assertIs<BrochuresScreenEvent.ShowSnackBar>(awaitItem())
        }

        assertFalse(viewModel.uiState.value.loadingState.isLoading)
    }
}

private val Ads = listOf(
    Advertisement(
        contentType = AdvertisementContentType.Brochure,
        content = BrochureContent(
            id = 1,
            title = "Groceries",
            brochureImageUrl = "",
            publisher = Publisher("1", "REWE"),
            distance = 5.0,
        ),
    ),
    Advertisement(
        contentType = AdvertisementContentType.Brochure,
        content = BrochureContent(
            id = 2,
            title = "Supplies",
            brochureImageUrl = "",
            publisher = Publisher("2", "EDEKA"),
            distance = 4.0,
        )
    ),
    Advertisement(
        contentType = AdvertisementContentType.Brochure,
        content = BrochureContent(
            id = 3,
            title = "Supermarket",
            brochureImageUrl = "",
            publisher = Publisher("3", "KAUF"),
            distance = 6.0,
        )
    )
)
