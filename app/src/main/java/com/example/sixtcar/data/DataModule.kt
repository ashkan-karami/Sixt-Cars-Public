package com.example.sixtcar.data

import com.example.sixtcar.BuildConfig
import com.example.sixtcar.data.network.service.CarListService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 *  Hilt will handle the dependency injection and provide all requirements. Hilt is like a wrapper on
 *  Dagger in order to reduce the boilerplate code that was required for Dagger and make is easier.
 */

@Module
@InstallIn(SingletonComponent::class)
internal class DataModule {

    /**
     *  This function provides an instance of OkHttpClient to be used for Retrofit-Calls.
     *  Set some minor properties and settings for its connection the same as TimeOut.
     */

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient = OkHttpClient.Builder().apply {
        connectTimeout(30L, TimeUnit.SECONDS)
        writeTimeout(30L, TimeUnit.SECONDS)
        readTimeout(30L, TimeUnit.SECONDS)
        retryOnConnectionFailure(true)
    }.build()

    /**
     *  Moshi is converter factory to convert JSON data received form Retrofit into a Kotlin data-class.
     *  Compare to gson, Moshi is newer(written in Kotlin) and more compatible with Retrofit because they
     *  both use the same buffer.
     */

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().apply {
        add(KotlinJsonAdapterFactory())
    }.build()

    /**
     *  Retrofit will handle all Http request calls.
     */
    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient, moshi: Moshi): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
            .client(client)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

    /**
     *  Provides a singleton instance of CarListService.
     */
    @Provides
    @Singleton
    fun provideCarListService(retrofit: Retrofit): CarListService =
        retrofit.create(CarListService::class.java)
}