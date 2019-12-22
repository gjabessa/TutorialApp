package com.example.tutorialapp.repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import com.example.tutorialapp.Dao.CourseDao
import com.example.tutorialapp.data.Course

class CourseRepository(private val courseDao: CourseDao){
    fun courses(): LiveData<List<Course>> = courseDao.getCourses()

    fun icourses(): LiveData<List<Course>> = courseDao.getiCourses()

    fun ecourses(): LiveData<List<Course>> = courseDao.geteCourses()
    fun getLevel(): LiveData<List<String>> = courseDao.getLevel()

    fun insertCourse(course: Course){
        courseDao.insertCourse(course)
    }

    fun deleteCourse(course: Course){
        courseDao.deleteCourse(course)
    }

    fun deleteCourses(){
        courseDao.deleteCourses()
    }
}