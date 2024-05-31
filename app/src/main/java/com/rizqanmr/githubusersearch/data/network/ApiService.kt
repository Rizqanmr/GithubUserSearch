package com.rizqanmr.githubusersearch.data.network

import com.rizqanmr.githubusersearch.data.network.models.UserDetailNetwork
import com.rizqanmr.githubusersearch.data.network.models.UserSearchResult
import com.rizqanmr.githubusersearch.data.network.models.UserNetwork
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/users")
    suspend fun getListUsers(): List<UserNetwork>

    @GET("/users/{username}")
    suspend fun getUserDetail(@Path("username") username: String) : UserDetailNetwork

    @GET("/search/users")
    suspend fun searchUsers(@Query("q") query: String) : UserSearchResult
}