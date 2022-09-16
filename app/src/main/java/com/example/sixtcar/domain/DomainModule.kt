package com.example.sixtcar.domain

import com.example.sixtcar.data.CarRepositoryImpl
import com.example.sixtcar.data.network.service.CarListService
import com.example.sixtcar.domain.repository.CarRepository
import com.example.sixtcar.domain.usecase.GetCars
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 *  Hilt will handle the dependency injection and provide all requirements. Hilt is like a wrapper on
 *  Dagger in order to reduce the boilerplate code that was required for Dagger and make is easier.
 */

@Module
@InstallIn(SingletonComponent::class)
internal class DomainModule {

    /**
     *  Provides and instance of CarRepository in order to be injected into GetCar use-case.
     */
    @Provides
    @Singleton
    fun provideCarRepository(
        service: CarListService
    ): CarRepository =
        CarRepositoryImpl(
            service
        )

    /**
     *  Provides GetCar use-case to be injected into the ViewModel to be invoked directly from there.
     */
    @Provides
    @Singleton
    fun provideGetCarUseCase(repository: CarRepository): GetCars = GetCars(repository)
}