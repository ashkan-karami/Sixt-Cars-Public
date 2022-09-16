package com.example.sixtcar.domain.usecase

import com.example.sixtcar.data.network.base.ResultWrapper
import com.example.sixtcar.domain.model.CarModel
import com.example.sixtcar.domain.repository.CarRepository
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

/**
 *  GetCar use-case is in charge of providing a list of cars and its only responsibility is to
 *  ask repository for a list of cars and return a Result to the ViewModel.
 *  The repository will be injected via constructor by Hilt.
 *  GetCars use-case receives four types of response(a successful and three failure) that depends on
 *  situation convert them to the two types of response.
 *  The use-case only receives data from repository, doesn't care about the way that data is collected.
 */
internal class GetCars @Inject constructor(private val repository: CarRepository) {

    suspend operator fun invoke(): Result{
        return try{
            when(val response = repository.getCarList()) {
                is ResultWrapper.Success -> Result.Success(response.value)
                is ResultWrapper.ConnectionError -> Result.Error(response.errorMessage)
                is ResultWrapper.GenericError -> Result.Error(response.errorResponse?.message?:"Something went wrong!")
                else -> Result.Error("Server connection failed!")
            }
        }catch (e: Exception){
            e.printStackTrace()
            return Result.Error(e.message?:"Something went wrong!")
        }
    }

    /**
     *  This Result.kt class is a sealed class that wraps both Success and Failure response depend on the
     *  api call status. If the API-call success, a list of cars will be returned through Result.Success
     *  and in case it fails, an exception will be returned, GetCars use-case will handle it.
     */
    internal sealed interface Result{
        data class Success(val cars: List<CarModel>):Result
        data class Error(val error: String):Result
    }
}