package com.example.myapplication.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.data.model.UserHistory

@Database(entities = [UserHistory::class], version = 1)
abstract class UserHistoryDB : RoomDatabase() {
    abstract fun userDao(): UserHistoryDao
}
