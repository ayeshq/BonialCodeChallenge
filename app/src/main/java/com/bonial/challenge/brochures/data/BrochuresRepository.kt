package com.bonial.challenge.brochures.data

import com.bonial.challenge.brochures.data.model.Advertisement

interface BrochuresRepository {

    suspend fun fetchBrochures(): Result<List<Advertisement>>

}
