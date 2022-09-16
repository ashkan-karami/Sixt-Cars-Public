package com.example.sixtcar.data.network.service

import com.example.sixtcar.data.network.URLs
import com.example.sixtcar.data.network.model.CarResponseModel
import retrofit2.http.GET

/**
 *  Usually I put all car-related services inside an interface in which they are all in the same category/package,
 *  here we have only one API.
 *  For other APIs from other categories we create another interface, for example, for Driver APIs we create
 *  DriverInterface consists of all related-APIs.
 */
internal interface CarListService {

    /**
     *  getCarList provides a list of cars by a GET-HTTP call from a known address.
     */
    @GET(URLs.CARS)
    suspend fun getCarList(): List<CarResponseModel>
}