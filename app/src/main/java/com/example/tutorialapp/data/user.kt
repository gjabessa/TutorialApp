package com.example.tutorialapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "user")
data class User(
    @ColumnInfo(name="username") val username: String,
    @PrimaryKey @ColumnInfo(name="id") val phone_number: String,
    @ColumnInfo(name="password") val password: String,
    @ColumnInfo(name = "level") var level: String,
    @ColumnInfo(name = "step") val step: String,
    @ColumnInfo(name = "category") var category: String
): Serializable