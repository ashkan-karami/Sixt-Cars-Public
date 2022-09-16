package com.example.sixtcar.presentation

import com.example.sixtcar.domain.model.CarModel
import com.example.sixtcar.domain.usecase.GetCars
import com.example.sixtcar.presentation.view.carMap.CarMapViewModel
import com.example.sixtcar.util.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
internal class CarMapViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val getCars: GetCars = mock()
    private val viewModel: CarMapViewModel = CarMapViewModel(getCars)
    private val carList = mock<List<CarModel>>()

    init {
        runBlocking {
            whenever(getCars.invoke()).thenReturn(
                GetCars.Result.Success(carList)
            )
        }
    }

    @Test
    fun getCarList() = runTest{
        viewModel.getCarList()
        verify(getCars, times(1)).invoke()
    }

    @Test
    fun emitsCarListFromUseCase() = runTest {
        viewModel.getCarList()
        assertEquals(carList, viewModel.carsStateFlow.value)
    }
}