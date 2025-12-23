package com.bonial.challenge.brochures.usecases

import com.bonial.challenge.brochures.data.BrochuresRepository
import com.bonial.challenge.brochures.data.model.AdvertisementContentType
import com.bonial.challenge.brochures.data.model.BrochureContent

internal class FetchBrochuresUseCaseImpl(
    private val brochuresRepository: BrochuresRepository
) : FetchBrochuresUseCase {

    override suspend fun invoke(): Result<List<BrochureContent>> {
        return brochuresRepository
            .fetchBrochures()
            .fold(
                onSuccess = { ads ->
                    Result.success(
                        value = ads.filter {
                            it.contentType == AdvertisementContentType.Brochure
                                    || it.contentType == AdvertisementContentType.BrochurePremium
                        }.map { ad ->
                            ad.content as BrochureContent
                        }
                    )
                },
                onFailure = {
                    Result.failure(it)
                },
            )
    }
}
