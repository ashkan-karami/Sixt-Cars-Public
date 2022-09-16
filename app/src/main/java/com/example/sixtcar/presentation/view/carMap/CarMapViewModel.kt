package com.example.sixtcar.presentation.view.carMap

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sixtcar.domain.model.CarModel
import com.example.sixtcar.domain.usecase.GetCars
import com.example.sixtcar.presentation.view.carMap.model.MapItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *  CarMapViewModel is specially generated to collaborate with the CarMapFragment. This will be added
 *  to the Hilt for dependency injection and prevent from creating new object directly.
 *  Also an instance of GetCars use case will be injected to this ViewModel for getting required data.
 */
@HiltViewModel
internal class CarMapViewModel @Inject constructor(
    private val getCars: GetCars
): ViewModel() {
    private val _carsStateFlow = MutableStateFlow<List<MapItem>?>(null)
    val carsStateFlow: StateFlow<List<MapItem>?> = _carsStateFlow
    val carListError = MutableStateFlow<String?>(null)

    /**
     *  This function will fetch the data by helping of use-case. Tow types of response(Success/Failure)
     *  will be received that must be handled.
     */
    fun getCarList() = viewModelScope.launch {
        getCars.invoke().apply {
            when(this){
                is GetCars.Result.Success -> {
                    generateLocations(this.cars)
                }
                is GetCars.Result.Error -> {
                    carListError.emit(this.error)
                }
            }
        }
    }

    /**
     *  When the data(list of cars) is fully received, we can convert it to the MapModel for adding to the
     *  map by using of .map() function.
     */
    private fun generateLocations(cars: List<CarModel>){
        _carsStateFlow.tryEmit(cars.map {MapItem(lat=it.latitude, lng=it.longitude, title=it.name, icon=it.carImageUrl?:"")})
    }
}