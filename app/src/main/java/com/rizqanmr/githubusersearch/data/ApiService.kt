package com.rizqanmr.githubusersearch.data

import com.rizqanmr.githubusersearch.data.models.UserDetail
import com.rizqanmr.githubusersearch.data.models.Users
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/users")
    suspend fun getListUsers(): List<Users>

    @GET("/users/{username}")
    suspend fun getUserDetail(@Path("username") username: String) : UserDetail
}