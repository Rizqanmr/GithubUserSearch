package com.rizqanmr.githubusersearch.domain.repository

import com.rizqanmr.githubusersearch.data.UserMapper
import com.rizqanmr.githubusersearch.data.local.dao.UserDao
import com.rizqanmr.githubusersearch.data.model.User
import com.rizqanmr.githubusersearch.data.network.ApiService
import com.rizqanmr.githubusersearch.data.network.Result
import com.rizqanmr.githubusersearch.data.network.models.UserDetailNetwork
import com.rizqanmr.githubusersearch.utils.safeApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDao
) : UserRepository {

    override suspend fun getListUser(): Result<List<User>> {
        return try {
            val networkResult = safeApiCall { apiService.getListUsers() }
            if (networkResult is Result.Success) {
                val usersFromNetwork = networkResult.data.map { UserMapper.fromNetwork(it) }
                withContext(Dispatchers.IO) {
                    userDao.insertUsers(usersFromNetwork.map { UserMapper.toEntity(it) })
                }
                Result.Success(usersFromNetwork)
            } else {
                val localUsers = withContext(Dispatchers.IO) {
                    userDao.getAllUsers()
                }
                Result.Success(localUsers.map { UserMapper.fromEntity(it) })
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getUserDetail(username: String): Result<UserDetailNetwork> = safeApiCall {
        apiService.getUserDetail(username)
    }

    override suspend fun searchUsers(query: String): Result<List<User>> {
        return try {
            val networkResult = safeApiCall { apiService.searchUsers(query) }
            if (networkResult is Result.Success) {
                val usersFromNetwork = networkResult.data.items.map { UserMapper.fromNetwork(it) }
                withContext(Dispatchers.IO) {
                    userDao.insertUsers(usersFromNetwork.map { UserMapper.toEntity(it) })
                }
                Result.Success(usersFromNetwork)
            } else {
                val localUsers = withContext(Dispatchers.IO) {
                    userDao.searchUser(query)
                }
                Result.Success(localUsers.map { UserMapper.fromEntity(it) })
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}