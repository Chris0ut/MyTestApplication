package com.example.myapplication.ui.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.util.Constants.BASE_URL
import com.example.myapplication.data.model.User
import com.example.myapplication.data.remote.GithubApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UsersViewModel : ViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private var sinceId: Int = 0

    fun loadUsers() {
        _isLoading.value = true

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(GithubApi::class.java)
        val call = apiService.getAllUsers(30, sinceId)

        call.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    val users = response.body()
                    if (users != null) {
                        sinceId = users.last().id
                        _users.value = users!!
                    }
                } else {
                    _errorMessage.value = "Failed to load users: ${response.code()}"
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                _errorMessage.value = "Failed to load users"
                _isLoading.value = false
            }
        })
    }

    fun refreshUsers() {
        sinceId = 0
        loadUsers()
    }
}
