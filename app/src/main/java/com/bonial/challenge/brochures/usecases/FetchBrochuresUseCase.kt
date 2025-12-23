package com.bonial.challenge.brochures.usecases

import com.bonial.challenge.brochures.data.model.BrochureContent

interface FetchBrochuresUseCase {

    suspend operator fun invoke(): Result<List<BrochureContent>>
}
