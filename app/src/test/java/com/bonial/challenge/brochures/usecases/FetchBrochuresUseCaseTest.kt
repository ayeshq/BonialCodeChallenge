package com.bonial.challenge.brochures.usecases

import com.bonial.challenge.brochures.data.BrochuresRepository
import com.bonial.challenge.brochures.data.model.Advertisement
import com.bonial.challenge.brochures.data.model.AdvertisementContentType
import com.bonial.challenge.brochures.data.model.BrochureContent
import com.bonial.challenge.brochures.data.model.SuperBannerCarouselContent
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class FetchBrochuresUseCaseTest {

    private lateinit var repository: BrochuresRepository
    private lateinit var fetchBrochures: FetchBrochuresUseCase

    private lateinit var allAds: List<Advertisement>

    @Before
    fun setUp() {
        repository = mockk()
        fetchBrochures = FetchBrochuresUseCaseImpl(repository)
        val brochureContent: BrochureContent = mockk()
        val superBannerCarouselContent: SuperBannerCarouselContent = mockk()
        allAds = listOf(
            Advertisement(AdvertisementContentType.Brochure, brochureContent),
            Advertisement(AdvertisementContentType.BrochurePremium, brochureContent),
            Advertisement(AdvertisementContentType.SuperBannerCarousel, superBannerCarouselContent),
        )
    }

    @Test
    fun `invoke onSuccess returns adsList and filters out SuperBannerCarousel`() = runTest {
        coEvery { repository.fetchBrochures() } returns Result.success(allAds)

        val result = fetchBrochures()

        assertTrue(result.isSuccess)
        val ads = result.getOrNull()
        assertNotNull(ads)
        assertEquals(0, ads?.count { it.contentType == AdvertisementContentType.SuperBannerCarousel })
    }

    @Test
    fun `invoke returns failure result when repository fetch fails`() = runTest {
        val expectedException = Exception("Error!")
        coEvery { repository.fetchBrochures() } returns Result.failure(expectedException)

        val result = fetchBrochures()

        assertTrue(result.isFailure)
        assertEquals(expectedException, result.exceptionOrNull())
    }
}
