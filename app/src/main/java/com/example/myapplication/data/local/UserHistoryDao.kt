package com.example.myapplication.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.data.model.UserHistory

@Dao
interface UserHistoryDao {
    @Insert
    suspend fun insertUser(user: UserHistory)

    @Query("SELECT * FROM users ORDER BY id DESC LIMIT :limit OFFSET :offset")
    suspend fun getUserHistory(limit: Int, offset: Int): List<UserHistory>

    @Query("SELECT * FROM users WHERE name = :name LIMIT 1")
    suspend fun getUserByName(name: String): UserHistory?
}
