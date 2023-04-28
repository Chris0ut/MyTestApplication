package com.example.myapplication.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("login") val login: String,
    @SerializedName("id") val id: Int
)
