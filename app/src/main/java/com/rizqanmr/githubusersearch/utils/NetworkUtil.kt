package com.rizqanmr.githubusersearch.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

suspend fun <T : Any> safeApiCall(
    apiCall: suspend () -> T,
) : com.rizqanmr.githubusersearch.data.Result<T> {
    return withContext(Dispatchers.IO) {
        try {
            val response = apiCall.invoke()
            com.rizqanmr.githubusersearch.data.Result.Success(response)
        } catch (throwable: Throwable) {
            when(throwable){
                is HttpException -> {
                    com.rizqanmr.githubusersearch.data.Result.Error(false, throwable.code(), throwable.message)
                }
                else -> {
                    com.rizqanmr.githubusersearch.data.Result.Error(true, null, throwable.message)
                }
            }
        }
    }
}