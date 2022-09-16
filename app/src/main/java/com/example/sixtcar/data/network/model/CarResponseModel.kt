package com.example.sixtcar.data.network.model

import com.example.sixtcar.domain.model.CarModel
import com.squareup.moshi.Json

/**
 *  The response model for car-list API.
 */
internal data class CarResponseModel(
    @Json(name = "id")
    val id:String,
    @Json(name = "modelIdentifier")
    val modelIdentifier: String,
    @Json(name = "modelName")
    val modelName: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "make")
    val make: String,
    @Json(name = "group")
    val group: String,
    @Json(name = "color")
    val color: String?,
    @Json(name = "series")
    val series: String?,
    @Json(name = "fuelType")
    val fuelType: String?,
    @Json(name = "fuelLevel")
    val fuelLevel: Float,
    @Json(name = "transmission")
    val transmission: String?,
    @Json(name = "licensePlate")
    val licensePlate: String?,
    @Json(name = "latitude")
    val latitude: Double,
    @Json(name = "longitude")
    val longitude: Double,
    @Json(name = "innerCleanliness")
    val innerCleanliness: String,
    @Json(name = "carImageUrl")
    val carImageUrl: String?
)

/**
 *  toDomainModel() function converts received response into the model that is known for domain layer.
 */
internal fun CarResponseModel.toDomainModel() = CarModel(
    id = this.id,
    modelIdentifier = this.modelIdentifier,
    modelName = this.modelName,
    name = this.name,
    make = this.make,
    group = this.group,
    color = this.color,
    series = this.series,
    fuelType = this.fuelType,
    fuelLevel = this.fuelLevel,
    transmission = this.transmission,
    licensePlate = this.licensePlate,
    latitude = this.latitude,
    longitude = this.longitude,
    innerCleanliness = this.innerCleanliness,
    carImageUrl = this.carImageUrl
)