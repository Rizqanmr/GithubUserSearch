package com.rizqanmr.githubusersearch.domain.repository

import com.rizqanmr.githubusersearch.data.Result
import com.rizqanmr.githubusersearch.data.models.UserDetail
import com.rizqanmr.githubusersearch.data.models.UserSearchResult
import com.rizqanmr.githubusersearch.data.models.Users

interface RemoteDataSource {

    suspend fun getListUser(): Result<List<Users>>

    suspend fun getUserDetail(username: String) : Result<UserDetail>

    suspend fun searchUsers(query: String) : Result<UserSearchResult>
}