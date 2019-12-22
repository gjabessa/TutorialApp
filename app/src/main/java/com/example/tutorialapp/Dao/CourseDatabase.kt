package com.example.tutorialapp.Dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tutorialapp.data.Course
import com.example.tutorialapp.data.User

@Database (entities = arrayOf(Course::class), version = 1)
abstract class CourseDatabase: RoomDatabase(){
    abstract fun courseDao():CourseDao
    companion object{
        @Volatile
        private var INSTANCE: CourseDatabase? = null

        fun getDatabase(context: Context):CourseDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CourseDatabase::class.java, "course_database"
                ).build()

                INSTANCE = instance
                return instance

            }
        }
    }
}