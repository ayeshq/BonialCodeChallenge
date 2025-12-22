package com.bonial.challenge.brochures.data.remote

import com.bonial.challenge.brochures.data.model.AddsWrapper
import com.bonial.challenge.brochures.data.model.Advertisement
import com.bonial.challenge.brochures.data.model.FetchBrochuresApiResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.koin.test.KoinTest
import kotlin.test.assertFailsWith

class BrochuresRemoteDataSourceTest : KoinTest {

    private val api = mockk<BrochuresApi>()
    private val dataSource = BrochuresRemoteDataSourceImpl(api)

    @Test
    fun `fetchBrochures success call should returns expected advertisements`() = runTest {
        val expectedAdds = listOf<Advertisement>(mockk(), mockk())
        val response = FetchBrochuresApiResponse(AddsWrapper(expectedAdds))
        coEvery { api.fetchBrochures() } returns response

        val result = dataSource.fetchBrochures()
        assertEquals(expectedAdds, result)
    }

    @Test
    fun `fetchBrochures should throw exception when api throws an exception`() = runTest {
        val expectedException = Exception("Api error")
        coEvery { api.fetchBrochures() } throws expectedException

        val thrownException = assertFailsWith<Exception> {
            dataSource.fetchBrochures()
        }
        assertEquals(expectedException, thrownException)
    }
}
