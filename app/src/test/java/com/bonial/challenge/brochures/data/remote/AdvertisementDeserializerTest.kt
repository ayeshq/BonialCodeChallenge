package com.bonial.challenge.brochures.data.remote

import com.bonial.challenge.brochures.data.model.Advertisement
import com.bonial.challenge.brochures.data.model.AdvertisementContentType
import com.bonial.challenge.brochures.data.model.BrochureContent
import com.bonial.challenge.brochures.data.model.SuperBannerCarouselContent
import com.bonial.challenge.brochures.data.model.UnknownAdvertisementContent
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

private const val mockAdsJsonFile = "mockAdsJson.json"

class AdvertisementDeserializerTest {

    private lateinit var adsList: List<Advertisement>

    @Before
    fun setUp() {
        adsList = parseMockAdsJson()
    }

    @Test
    fun `deserializer parses 'brochure' and 'SuperBannerCarousel' content types`() = runTest {
        val expectedCarouselPageCount = 2

        assertTrue {
            adsList.any { it.content is SuperBannerCarouselContent }
        }

        val superBannerCarouselContent = adsList.first {
            it.contentType == AdvertisementContentType.SuperBannerCarousel
        }.content as SuperBannerCarouselContent

        assertEquals(expectedCarouselPageCount, superBannerCarouselContent.content.size)

        assertTrue {
            adsList.any { it.content is BrochureContent }
        }

        assertTrue {
            adsList.any { it.contentType == AdvertisementContentType.BrochurePremium }
        }

        assertTrue {
            adsList.any { it.contentType == AdvertisementContentType.Unknown }
        }

        assertTrue {
            adsList.any { it.content is UnknownAdvertisementContent }
        }
    }

    private fun parseMockAdsJson(): List<Advertisement> {
        val gson = GsonBuilder()
            .registerTypeAdapter(Advertisement::class.java, AdvertisementDeserializer())
            .create()
        val jsonString = this.javaClass.classLoader!!.getResource(mockAdsJsonFile).readText()
        val type = object : TypeToken<List<Advertisement>>() {}.type
        return gson.fromJson(jsonString, type)
    }
}
