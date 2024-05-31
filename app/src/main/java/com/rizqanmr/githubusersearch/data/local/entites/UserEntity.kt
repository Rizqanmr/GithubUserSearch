package com.rizqanmr.githubusersearch.data.local.entites

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Int,
    val username: String,
    val avatarUrl: String
)
