package com.example.myapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.util.Constants.DB_NAME

@Entity(tableName = DB_NAME)
data class UserHistory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val avatar: String,
    val name: String,
    val email: String
)