package com.bonial.challenge.brochures.usecases

import com.bonial.challenge.brochures.data.BrochuresRepository
import com.bonial.challenge.brochures.data.model.Advertisement
import com.bonial.challenge.brochures.data.model.AdvertisementContentType

internal class FetchBrochuresUseCaseImpl(
    private val brochuresRepository: BrochuresRepository
) : FetchBrochuresUseCase {

    override suspend fun invoke(): Result<List<Advertisement>> {
        return brochuresRepository
            .fetchBrochures()
            .fold(
                onSuccess = { ads ->
                    Result.success(
                        value = ads.filter {
                            it.contentType == AdvertisementContentType.Brochure
                                    || it.contentType == AdvertisementContentType.BrochurePremium
                        }
                    )
                },
                onFailure = {
                    Result.failure(it)
                },
            )
    }
}
