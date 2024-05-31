package com.rizqanmr.githubusersearch.data

import com.rizqanmr.githubusersearch.data.local.entites.UserEntity
import com.rizqanmr.githubusersearch.data.model.User
import com.rizqanmr.githubusersearch.data.network.models.UserNetwork

object UserMapper {
    fun fromEntity(entity: UserEntity) : User {
        return User(
            id = entity.id,
            username = entity.username,
            avatarUrl = entity.avatarUrl
        )
    }

    fun toEntity(user: User) : UserEntity {
        return UserEntity(
            id = user.id,
            username = user.username,
            avatarUrl = user.avatarUrl
        )
    }

    fun fromNetwork(network: UserNetwork) : User {
        return User(
            id = network.id,
            username = network.username,
            avatarUrl = network.avatarUrl
        )
    }

    fun toNetwork(user: User) : UserEntity {
        return UserEntity(
            id = user.id,
            username = user.username,
            avatarUrl = user.avatarUrl
        )
    }
}