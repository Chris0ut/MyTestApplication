package com.example.myapplication.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.util.Constants.BASE_URL
import com.example.myapplication.data.model.UserDetails
import com.example.myapplication.data.remote.GithubApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailsViewModel : ViewModel() {

    private val _userDetails = MutableLiveData<UserDetails>()
    val userDetails: LiveData<UserDetails> = _userDetails

    fun getUserDetails(username: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(GithubApi::class.java)
        apiService.getUserDetails(username).enqueue(object : Callback<UserDetails> {
            override fun onResponse(
                call: Call<UserDetails>,
                response: Response<UserDetails>
            ) {
                if (response.isSuccessful) {
                    val user = response.body()
                    user?.let { _userDetails.value = it }
                }
            }

            override fun onFailure(call: Call<UserDetails>, t: Throwable) {}
        })
    }
}
