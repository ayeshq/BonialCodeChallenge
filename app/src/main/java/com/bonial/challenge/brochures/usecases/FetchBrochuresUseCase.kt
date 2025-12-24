package com.bonial.challenge.brochures.usecases

import com.bonial.challenge.brochures.model.Brochure

interface FetchBrochuresUseCase {

    suspend operator fun invoke(): Result<List<Brochure>>
}
