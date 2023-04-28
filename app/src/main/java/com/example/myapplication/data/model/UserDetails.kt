package com.example.myapplication.data.model

import com.google.gson.annotations.SerializedName

data class UserDetails(
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("name") val name: String?,
    @SerializedName("type") val type: String,
    @SerializedName("email") val email: String?,
    @SerializedName("company") val company: String?,
    @SerializedName("following") val followingCount: Int,
    @SerializedName("followers") val followersCount: Int,
    @SerializedName("created_at") val createdAt: String
)
