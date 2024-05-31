package com.rizqanmr.githubusersearch.data.network.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserNetwork(
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "login")
    val username: String = "",
    @Json(name = "avatar_url")
    val avatarUrl: String = ""
)