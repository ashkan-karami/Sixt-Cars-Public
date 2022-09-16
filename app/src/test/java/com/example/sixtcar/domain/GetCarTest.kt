package com.example.sixtcar.domain

import com.example.sixtcar.data.network.base.ResultWrapper
import com.example.sixtcar.domain.model.CarModel
import com.example.sixtcar.domain.repository.CarRepository
import com.example.sixtcar.domain.usecase.GetCars
import com.example.sixtcar.util.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class GetCarTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val repository: CarRepository = mock()
    private val getCars = GetCars(repository)
    private val carList = mock<List<CarModel>>()

    init {
        runTest {
            whenever(repository.getCarList()).thenReturn(
                ResultWrapper.Success(carList)
            )
        }
    }

    @Test
    fun invoke() = runTest {
        getCars.invoke()
        verify(repository, times(1)).getCarList()
    }

    @Test
    fun invokeReturnsSuccess() = runTest {
        val response = getCars.invoke()
        assertEquals(GetCars.Result.Success(carList), response)
    }

    @Test
    fun invokeReturnsFailure() = runTest {
        val response = getCars.invoke()
        assertEquals(GetCars.Result.Error("Nothing found!"), response)
    }
}