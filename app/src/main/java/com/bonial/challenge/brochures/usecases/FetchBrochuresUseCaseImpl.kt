package com.bonial.challenge.brochures.usecases

import com.bonial.challenge.brochures.data.BrochuresRepository
import com.bonial.challenge.brochures.data.model.AdvertisementContentType
import com.bonial.challenge.brochures.data.model.BrochureContent
import com.bonial.challenge.brochures.model.Brochure
import com.bonial.challenge.brochures.model.toBrochure

internal class FetchBrochuresUseCaseImpl(
    private val brochuresRepository: BrochuresRepository
) : FetchBrochuresUseCase {

    private val brochureTypes = setOf(AdvertisementContentType.Brochure, AdvertisementContentType.BrochurePremium)

    override suspend fun invoke(): Result<List<Brochure>> {
        return brochuresRepository
            .fetchBrochures()
            .fold(
                onSuccess = { ads ->
                    val brochures = ads.filter {
                        it.contentType in brochureTypes
                    }.map { ad ->
                        (ad.content as BrochureContent).toBrochure(ad.contentType == AdvertisementContentType.BrochurePremium)
                    }

                    Result.success(brochures)
                },
                onFailure = {
                    Result.failure(it)
                },
            )
    }
}
