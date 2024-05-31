package com.rizqanmr.githubusersearch.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserDetail(
    @Json(name = "bio")
    val bio: String?,
    @Json(name = "login")
    val login: String,
    @Json(name = "blog")
    val blog: String?,
    @Json(name = "followers")
    val followers: Int,
    @Json(name = "avatar_url")
    val avatarUrl: String,
    @Json(name = "html_url")
    val htmlUrl: String,
    @Json(name = "following")
    val following: Int,
    @Json(name = "name")
    val name: String?,
    @Json(name = "company")
    val company: String?,
    @Json(name = "location")
    val location: String?,
    @Json(name = "id")
    val id: Int,
    @Json(name = "public_repos")
    val publicRepos: Int,
    @Json(name = "email")
    val email: String?
) {
    val safeBio: String get() = bio ?: ""
    val safeBlog: String get() = blog ?: ""
    val safeCompany: String get() = company ?: ""
    val safeName: String get() = name ?: ""
    val safeLocation: String get() = location ?: ""
    val safeEmail: String get() = email ?: ""
    val totalFollowers: String get() = "$followers followers"
    val totalFollowing: String get() = "$following following"
    val totalRepositories: String get() = "Repositories $publicRepos"
}