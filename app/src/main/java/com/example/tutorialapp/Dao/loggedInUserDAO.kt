package com.example.tutorialapp.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.tutorialapp.data.Course
import com.example.tutorialapp.data.User

@Dao
interface loggedInUserDao{

    @Query("Select * from user ORDER BY id LIMIT 1")
    fun getUser(): LiveData<List<User>>

    @Query("Select level from user LIMIT 1")
    fun getLevel(): LiveData<List<String>>

    @Query("Select category from user LIMIT 1")
    fun getCategory(): LiveData<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User): Long

    @Delete
    fun deleteUser(user: User)

    @Query("Delete from user")
    fun deleteAll()
}