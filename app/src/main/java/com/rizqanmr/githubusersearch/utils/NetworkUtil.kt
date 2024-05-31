package com.rizqanmr.githubusersearch.utils

import com.rizqanmr.githubusersearch.data.network.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

suspend fun <T : Any> safeApiCall(
    apiCall: suspend () -> T,
) : Result<T> {
    return withContext(Dispatchers.IO) {
        try {
            val response = apiCall.invoke()
            Result.Success(response)
        } catch (throwable: Throwable) {
            when(throwable){
                is HttpException -> {
                    Result.Error(false, throwable.code(), throwable.message)
                }
                else -> {
                    Result.Error(true, null, throwable.message)
                }
            }
        }
    }
}