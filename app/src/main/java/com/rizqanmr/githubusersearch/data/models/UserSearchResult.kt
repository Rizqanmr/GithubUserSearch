package com.rizqanmr.githubusersearch.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserSearchResult(
    @Json(name = "total_count")
    val totalCount: Int = 0,
    @Json(name = "incomplete_results")
    val incompleteResults: Boolean = false,
    @Json(name = "items")
    val items: List<Users> = emptyList()
)