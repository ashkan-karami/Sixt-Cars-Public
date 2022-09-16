package com.example.sixtcar.data.network.base

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T): ResultWrapper<T>()
    data class GenericError(val httpCode: Int? = null, val errorResponse : ErrorResponse?): ResultWrapper<Nothing>()
    data class ConnectionError(val errorType: Int ,val errorMessage : String): ResultWrapper<Nothing>()
    object NetworkError : ResultWrapper<Nothing>()
}