package com.rizqanmr.githubusersearch.domain.repository

import com.rizqanmr.githubusersearch.data.network.Result
import com.rizqanmr.githubusersearch.data.network.models.UserDetailNetwork
import com.rizqanmr.githubusersearch.data.network.models.UserSearchResult
import com.rizqanmr.githubusersearch.data.network.models.UserNetwork

interface RemoteDataSource {

    suspend fun getListUser(): Result<List<UserNetwork>>

    suspend fun getUserDetail(username: String) : Result<UserDetailNetwork>

    suspend fun searchUsers(query: String) : Result<UserSearchResult>
}