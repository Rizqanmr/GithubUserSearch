package com.rizqanmr.githubusersearch.data.local.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rizqanmr.githubusersearch.data.local.entites.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}