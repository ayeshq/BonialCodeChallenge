package com.bonial.challenge.brochures.data

import com.bonial.challenge.brochures.data.model.AddsWrapper
import com.bonial.challenge.brochures.data.model.Advertisement
import com.bonial.challenge.brochures.data.model.FetchBrochuresApiResponse
import com.bonial.challenge.brochures.data.remote.BrochuresApi
import com.bonial.challenge.brochures.data.remote.BrochuresRemoteDataSource
import com.bonial.challenge.brochures.data.remote.BrochuresRemoteDataSourceImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.io.IOException
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class BrochuresRepositoryTest {

    private lateinit var api: BrochuresApi
    private lateinit var dataSource: BrochuresRemoteDataSource
    private lateinit var repository: BrochuresRepository

    @Before
    fun setUp() {
        api = mockk<BrochuresApi>()
        dataSource = BrochuresRemoteDataSourceImpl(api)
        repository = BrochuresRepositoryImpl(dataSource)
    }

    @Test
    fun `fetchBrochures onSuccess returns success result with advertisement list`() = runTest {
        val expectedAdds = listOf<Advertisement>(mockk(), mockk())
        val apiResponse = FetchBrochuresApiResponse(AddsWrapper(expectedAdds))
        coEvery { api.fetchBrochures() } returns apiResponse

        val result = repository.fetchBrochures()

        assertTrue(result.isSuccess)
        assertEquals(expectedAdds, result.getOrNull())
    }

    @Test
    fun `fetchBrochures oFailure returns result with exception`() = runTest {
        val expectedException = IOException("Network Error")
        coEvery { api.fetchBrochures() } throws expectedException

        val result = repository.fetchBrochures()

        assertTrue(result.isFailure)
        assertEquals(expectedException, result.exceptionOrNull())
    }
}
