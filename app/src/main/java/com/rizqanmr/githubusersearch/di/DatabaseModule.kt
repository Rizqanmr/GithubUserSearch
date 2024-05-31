package com.rizqanmr.githubusersearch.di

import android.content.Context
import androidx.room.Room
import com.rizqanmr.githubusersearch.data.local.dao.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Volatile
    private var userDatabase: UserDatabase? = null

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): UserDatabase {
        return userDatabase ?: synchronized(this) {
            userDatabase ?: Room.databaseBuilder(
                context.applicationContext,
                UserDatabase::class.java, "github_user_db"
            ).fallbackToDestructiveMigration().build().also { userDatabase = it }
        }
    }

    @Singleton
    @Provides
    fun provideDao(userDatabase: UserDatabase) = userDatabase.userDao()
}