package com.rizqanmr.githubusersearch.di

import com.rizqanmr.githubusersearch.data.network.ApiService
import com.rizqanmr.githubusersearch.domain.repository.RemoteDataSource
import com.rizqanmr.githubusersearch.domain.repository.RemoteRepository
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
    fun provideRepository(apiService: ApiService) : RemoteDataSource = RemoteRepository(apiService)
}