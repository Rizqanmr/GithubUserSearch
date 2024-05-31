package com.rizqanmr.githubusersearch.data

import com.rizqanmr.githubusersearch.data.models.UserDetail
import com.rizqanmr.githubusersearch.data.models.UserSearchResult
import com.rizqanmr.githubusersearch.data.models.Users
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/users")
    suspend fun getListUsers(): List<Users>

    @GET("/users/{username}")
    suspend fun getUserDetail(@Path("username") username: String) : UserDetail

    @GET("/search/users")
    suspend fun searchUsers(@Query("q") query: String) : UserSearchResult
}