package com.example.sixtcar.domain.repository

import com.example.sixtcar.data.network.base.ResultWrapper
import com.example.sixtcar.domain.model.CarModel

/**
 *  CarRepository holds functions for car-related objects, introduced in domain and implemented in data
 *  module/package. Functions in repository will be called from use-cases to provide requested data.
 */
internal interface CarRepository {

    suspend fun getCarList(): ResultWrapper<List<CarModel>>
}