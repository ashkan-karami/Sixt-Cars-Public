package com.example.sixtcar.data

import com.example.sixtcar.data.network.base.BaseAPIRepo
import com.example.sixtcar.data.network.base.ResultWrapper
import com.example.sixtcar.data.network.model.toDomainModel
import com.example.sixtcar.data.network.service.CarListService
import com.example.sixtcar.domain.model.CarModel
import com.example.sixtcar.domain.repository.CarRepository
import javax.inject.Inject

/**
 *  CarRepositoryImpl implements CarRepository introduced in domain module.
 */
internal class CarRepositoryImpl @Inject constructor(
    private val service: CarListService
) : BaseAPIRepo(), CarRepository {

    /**
     *  The GetCarList function provides a list of cars either from data-base or from server. Generally it
     *  checks the data-base and if any data exists, passes it and if not, checks the API.
     *  Other classes don't care about the way the list is collected, only use it.
     */
    override suspend fun getCarList(): ResultWrapper<List<CarModel>> =
        safeApiCall {
            service.getCarList().map { it.toDomainModel() }
        }
}