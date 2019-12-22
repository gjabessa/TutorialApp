package com.example.tutorialapp.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.tutorialapp.data.Course

@Dao
interface CourseDao{

    @Query("Select * from course level where level ='b' ORDER BY id")
    fun getCourses(): LiveData<List<Course>>

    @Query("Select level from course LIMIT 1")
    fun getLevel(): LiveData<List<String>>

    @Query("Select * from course where level ='i' ORDER BY id")
    fun getiCourses(): LiveData<List<Course>>

    @Query("Select * from course where level ='e' ORDER BY id")
    fun geteCourses(): LiveData<List<Course>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCourse(course: Course): Long

    @Delete
    fun deleteCourse(course: Course)

    @Query("Delete from course")
    fun deleteCourses()
}