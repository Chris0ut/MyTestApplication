package com.example.myapplication.data.remote

import com.example.myapplication.data.model.User
import com.example.myapplication.data.model.UserDetails
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {
    @GET("users")
    fun getAllUsers(@Query("per_page") perPage: Int = 30, @Query("since") since: Int = 0): Call<List<User>>

    @GET("users/{username}")
    fun getUserDetails(@Path("username") username: String): Call<UserDetails>
}
