package com.example.tutorialapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.tutorialapp.Dao.CourseDatabase
import com.example.tutorialapp.data.Course
import com.example.tutorialapp.repository.CourseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CourseView(application: Application): AndroidViewModel(application){

    private val courseRepository: CourseRepository
    val courses: LiveData<List<Course>>
    val icourses: LiveData<List<Course>>
    val ecourses: LiveData<List<Course>>
    lateinit var getLevel: LiveData<List<String>>

    init{
        val courseDao = CourseDatabase.getDatabase(application).courseDao()
        courseRepository = CourseRepository(courseDao)
        courses = courseRepository.courses()
        icourses = courseRepository.icourses()
        ecourses = courseRepository.ecourses()
        getLevel = courseRepository.getLevel()
    }
    fun insertCourse(course: Course) = viewModelScope.launch(Dispatchers.IO) {
        courseRepository.insertCourse(course)
    }

    fun deleteCourse(course: Course) = viewModelScope.launch(Dispatchers.IO) {
        courseRepository.deleteCourse(course)
    }

    fun deleteCourses() =  viewModelScope.launch(Dispatchers.IO) {
        courseRepository.deleteCourses()
    }
}