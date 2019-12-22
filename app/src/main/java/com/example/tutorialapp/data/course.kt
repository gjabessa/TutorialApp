package com.example.tutorialapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "course")
data class Course(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name="description") val description: String,
    @ColumnInfo(name="large_image") val large_image: String,
    @ColumnInfo(name="small_image") val small_image: String,
    @ColumnInfo(name = "level") val level: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name="seen") var seen: Boolean
): Serializable