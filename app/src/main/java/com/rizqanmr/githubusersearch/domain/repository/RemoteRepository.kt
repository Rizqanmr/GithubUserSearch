package com.rizqanmr.githubusersearch.domain.repository

import com.rizqanmr.githubusersearch.data.network.ApiService
import com.rizqanmr.githubusersearch.data.network.Result
import com.rizqanmr.githubusersearch.data.network.models.UserDetailNetwork
import com.rizqanmr.githubusersearch.data.network.models.UserSearchResult
import com.rizqanmr.githubusersearch.data.network.models.UserNetwork
import com.rizqanmr.githubusersearch.utils.safeApiCall
import javax.inject.Inject

class RemoteRepository @Inject constructor(
    private val apiService: ApiService
) : RemoteDataSource {

    override suspend fun getListUser(): Result<List<UserNetwork>> = safeApiCall {
        apiService.getListUsers()
    }

    override suspend fun getUserDetail(username: String): Result<UserDetailNetwork> = safeApiCall {
        apiService.getUserDetail(username)
    }

    override suspend fun searchUsers(query: String): Result<UserSearchResult> = safeApiCall {
        apiService.searchUsers(query)
    }
}