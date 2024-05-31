package com.rizqanmr.githubusersearch.di

import com.rizqanmr.githubusersearch.data.local.dao.UserDao
import com.rizqanmr.githubusersearch.data.network.ApiService
import com.rizqanmr.githubusersearch.domain.repository.UserRepository
import com.rizqanmr.githubusersearch.domain.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(apiService: ApiService, userDao: UserDao) : UserRepository = UserRepositoryImpl(apiService, userDao)
}