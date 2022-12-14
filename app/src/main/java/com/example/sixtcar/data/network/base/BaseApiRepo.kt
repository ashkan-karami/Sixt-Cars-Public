package com.example.sixtcar.data.network.base

import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException

open class BaseAPIRepo
{
    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): ResultWrapper<T>
    {
        return try
        {
            ResultWrapper.Success(apiCall.invoke())
        }
        catch (throwable: Throwable)
        {
            throwable.printStackTrace()
            when (throwable)
            {
                is IOException -> ResultWrapper.NetworkError
                is SocketTimeoutException ->
                {
                    ResultWrapper.ConnectionError(
                        ErrorCodes.SocketTimeOut.code,
                        getErrorMessage(ErrorCodes.SocketTimeOut.code, null)
                    )
                }
                is ConnectException ->
                {
                    ResultWrapper.ConnectionError(
                        ErrorCodes.ConnectionException.code,
                        getErrorMessage(ErrorCodes.ConnectionException.code, null)
                    )
                }
                is HttpException ->
                {
                    when (val code = throwable.code())
                    {
                        in 400..599 ->
                        {
                            val errorResponse = convertErrorBody(throwable)
                            if (errorResponse == null)
                            {
                                ResultWrapper.ConnectionError(
                                    ErrorCodes.ServerMissMatch.code,
                                    getErrorMessage(ErrorCodes.ServerMissMatch.code, null)
                                )
                            }
                            else
                            {
                                ResultWrapper.GenericError(code, errorResponse)
                            }
                        }
                        else ->
                        {
                            ResultWrapper.ConnectionError(
                                ErrorCodes.ServerMissMatch.code,
                                getErrorMessage(ErrorCodes.ServerMissMatch.code, null)
                            )
                        }
                    }
                }
                else -> ResultWrapper.ConnectionError(
                    ErrorCodes.ServerMissMatch.code,
                    getErrorMessage(ErrorCodes.ServerMissMatch.code, throwable.message)
                )
            }
        }
    }

    /**
     *  Based on the error response generated by the backend, we create a error-model class and by using
     *  of Moshi library we convert error-json into error-model class.
     *
     *  In this case, I couldn't find any error/failure call, so I generate a fake error class for test.
     */
    private fun convertErrorBody(throwable: HttpException): ErrorResponse?
    {
        return try
        {
            throwable.response()?.errorBody()?.source()?.let {
                val moshiAdapter = Moshi.Builder().build().adapter(ErrorResponse::class.java)
                val errorResponse = moshiAdapter.fromJson(it)
                errorResponse
            }
        }
        catch (exception: Exception)
        {
            null
        }
    }

    private fun getErrorMessage(code: Int?, message: String?): String
    {
        return when (code)
        {
            ErrorCodes.SocketTimeOut.code -> "Connection Error!"
            ErrorCodes.ConnectionException.code -> "Network connection error!"
            ErrorCodes.ServerMissMatch.code -> "Connection Error!"
            500 -> "Connection Error!"
            401 -> message ?: "Your token is expired. Please log in again"
            404 -> message ?: "Couldn't find address!"
            else -> "Something went wrong"
        }
    }

    enum class ErrorCodes(val code: Int)
    {
        SocketTimeOut(-1),
        ConnectionException(-100),
        ServerMissMatch(0)
    }
}