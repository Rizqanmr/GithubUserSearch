package com.rizqanmr.githubusersearch.data

sealed class Result<out R> private constructor() {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(
        val isNetworkError: Boolean?,
        val errorCode: Int?,
        val errorBody: String?
    ) : Result<Nothing>()
}