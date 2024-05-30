package com.rizqanmr.githubusersearch.domain.repository

import com.rizqanmr.githubusersearch.data.ApiService
import com.rizqanmr.githubusersearch.data.Result
import com.rizqanmr.githubusersearch.data.models.Users
import com.rizqanmr.githubusersearch.utils.safeApiCall
import javax.inject.Inject

class RemoteRepository @Inject constructor(
    private val apiService: ApiService
) : RemoteDataSource {

    override suspend fun getListUser(): Result<List<Users>> = safeApiCall {
        apiService.getListUsers()
    }
}