package com.example.myapplication.ui.history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.myapplication.util.Constants.DB_NAME
import com.example.myapplication.data.local.UserHistoryDB
import com.example.myapplication.data.local.UserHistoryDao
import com.example.myapplication.data.model.UserHistory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    private val userDao: UserHistoryDao =
        Room.databaseBuilder(application, UserHistoryDB::class.java, DB_NAME).build().userDao()

    private val _usersList: MutableLiveData<List<UserHistory>> = MutableLiveData(emptyList())
    val users: LiveData<List<UserHistory>> = _usersList

    private var offset = 0
    private val limit = 30

    fun loadUsers() {
        viewModelScope.launch {
            val newUsers = withContext(Dispatchers.IO) {
                userDao.getUserHistory(limit, offset)
            }
            offset += limit
            _usersList.value = _usersList.value?.plus(newUsers)
        }
    }
}
