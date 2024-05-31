package com.rizqanmr.githubusersearch.domain.repository

import com.rizqanmr.githubusersearch.data.ApiService
import com.rizqanmr.githubusersearch.data.Result
import com.rizqanmr.githubusersearch.data.models.UserDetail
import com.rizqanmr.githubusersearch.data.models.UserSearchResult
import com.rizqanmr.githubusersearch.data.models.Users
import com.rizqanmr.githubusersearch.utils.safeApiCall
import javax.inject.Inject

class RemoteRepository @Inject constructor(
    private val apiService: ApiService
) : RemoteDataSource {

    override suspend fun getListUser(): Result<List<Users>> = safeApiCall {
        apiService.getListUsers()
    }

    override suspend fun getUserDetail(username: String): Result<UserDetail> = safeApiCall {
        apiService.getUserDetail(username)
    }

    override suspend fun searchUsers(query: String): Result<UserSearchResult> = safeApiCall {
        apiService.searchUsers(query)
    }
}