package com.rizqanmr.githubusersearch.domain.repository

import com.rizqanmr.githubusersearch.data.Result
import com.rizqanmr.githubusersearch.data.models.Users

interface RemoteDataSource {

    suspend fun getListUser(): Result<List<Users>>
}