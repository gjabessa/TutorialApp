package com.example.tutorialapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.tutorialapp.Dao.CourseDatabase
import com.example.tutorialapp.Dao.UserDatabase
import com.example.tutorialapp.Dao.loggedInUserDatabase
import com.example.tutorialapp.data.Course
import com.example.tutorialapp.data.User
import com.example.tutorialapp.repository.CourseRepository
import com.example.tutorialapp.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserView(application: Application): AndroidViewModel(application){

    private val userRepository: UserRepository
    val user: LiveData<List<User>>
    var loggedInUser: LiveData<List<User>>
    var getLevel: LiveData<List<String>>
    var getCategory: LiveData<List<String>>
    init{
        val userDao = UserDatabase.getDatabase(application).userDao()
        val loggedInUserDao = loggedInUserDatabase.getDatabase(application).loggedInUserDao()
        userRepository = UserRepository(userDao,loggedInUserDao)
        user = userRepository.user()
        loggedInUser = userRepository.loggedInUser()
        getLevel = userRepository.getLevel()
        getCategory = userRepository.getCategory()
    }
    fun insertUser(user: User) = viewModelScope.launch(Dispatchers.IO) {
        userRepository.insertUser(user)
    }

    fun findUser(username: String): LiveData<User> = userRepository.findUser(username)


    fun loginUser(user:User) = viewModelScope.launch(Dispatchers.IO){
        userRepository.loginUser(user)
    }


    fun deleteUser(user: User) = viewModelScope.launch(Dispatchers.IO) {
        userRepository.deleteUser(user)
    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO){
        userRepository.deleteAll()
    }

    fun logoutUser(user:User) = viewModelScope.launch(Dispatchers.IO){
        userRepository.logoutUser(user)
    }
}