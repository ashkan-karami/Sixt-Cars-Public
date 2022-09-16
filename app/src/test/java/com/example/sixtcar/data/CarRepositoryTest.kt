package com.example.sixtcar.data

import com.example.sixtcar.data.network.service.CarListService
import com.example.sixtcar.util.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@OptIn(ExperimentalCoroutinesApi::class)
class CarRepositoryTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val service: CarListService = mock()
    private val carRepositoryImpl: CarRepositoryImpl = CarRepositoryImpl(service)

    @Test
    fun getCarList() = runTest {
        carRepositoryImpl.getCarList()
        verify(service, times(1)).getCarList()
    }
}