package com.example.tutorialapp.Dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tutorialapp.data.Course
import com.example.tutorialapp.data.User

@Database (entities = arrayOf(User::class), version = 1)
abstract class loggedInUserDatabase: RoomDatabase(){
    abstract fun loggedInUserDao():loggedInUserDao
    companion object{
        @Volatile
        private var INSTANCE: loggedInUserDatabase? = null

        fun getDatabase(context: Context):loggedInUserDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    loggedInUserDatabase::class.java, "loggedInuser_database"
                ).build()

                INSTANCE = instance
                return instance

            }
        }
    }
}