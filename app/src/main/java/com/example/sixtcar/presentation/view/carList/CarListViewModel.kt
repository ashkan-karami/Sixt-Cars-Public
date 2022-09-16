package com.example.sixtcar.presentation.view.carList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sixtcar.domain.model.CarModel
import com.example.sixtcar.domain.usecase.GetCars
import com.example.sixtcar.presentation.view.carList.models.CarItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CarListViewModel @Inject constructor(
    private val getCars: GetCars
) : ViewModel() {

    private val _carsStateFlow = MutableStateFlow<List<CarItem>?>(null)
    val carsStateFlow: StateFlow<List<CarItem>?> = _carsStateFlow
    val carListError = MutableStateFlow<String?>(null)

    fun getCarList() = viewModelScope.launch {
        getCars.invoke().apply {
            when (this) {
                is GetCars.Result.Success -> {
                    emitCarList(this.cars)
                }
                is GetCars.Result.Error -> {
                    carListError.emit(this.error)
                }
            }
        }
    }

    private fun emitCarList(cars: List<CarModel>) {
        _carsStateFlow.tryEmit(cars.map {
            CarItem(
                id = it.id,
                name = it.name,
                modelName = it.modelName,
                group = it.group,
                color = it.color ?: "",
                series = it.series ?: "",
                icon = it.carImageUrl
            )
        })
    }
}