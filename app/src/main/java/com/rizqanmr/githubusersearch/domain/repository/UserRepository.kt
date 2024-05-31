package com.rizqanmr.githubusersearch.domain.repository

import com.rizqanmr.githubusersearch.data.model.User
import com.rizqanmr.githubusersearch.data.network.Result
import com.rizqanmr.githubusersearch.data.network.models.UserDetailNetwork

interface UserRepository {

    suspend fun getListUser(): Result<List<User>>

    suspend fun getUserDetail(username: String) : Result<UserDetailNetwork>

    suspend fun searchUsers(query: String) : Result<List<User>>
}