package com.rizqanmr.githubusersearch.data

import com.rizqanmr.githubusersearch.data.models.Users
import retrofit2.http.GET

interface ApiService {

    @GET("/users")
    suspend fun getListUsers(): List<Users>
}