package com.example.tutorialapp.repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import com.example.tutorialapp.Dao.CourseDao
import com.example.tutorialapp.Dao.UserDao
import com.example.tutorialapp.Dao.loggedInUserDao
import com.example.tutorialapp.data.Course
import com.example.tutorialapp.data.User
import com.example.tutorialapp.data.model.LoggedInUser

class UserRepository(private val userDao: UserDao, private val loggedInUserDao: loggedInUserDao){
    fun user(): LiveData<List<User>> = userDao.getUser()

    fun getLevel(): LiveData<List<String>> = loggedInUserDao.getLevel()

    fun getCategory(): LiveData<List<String>> = loggedInUserDao.getCategory()

    fun insertUser(user: User){
        userDao.insertUser(user)
    }

    fun loggedInUser(): LiveData<List<User>> = loggedInUserDao.getUser()

    fun loginUser(user:User){
        loggedInUserDao.insertUser(user)
    }

    fun deleteUser(user: User){
        userDao.deleteUser(user)
    }

    fun findUser(username: String): LiveData<User> = userDao.findUser(username)


    fun logoutUser(user: User){
        loggedInUserDao.deleteUser(user)
    }

    fun deleteAll(){
        userDao.deleteAll()
    }
}