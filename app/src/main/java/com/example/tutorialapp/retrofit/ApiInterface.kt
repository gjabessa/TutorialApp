package com.example.tutorialapp.retrofit

import com.example.tutorialapp.data.Course
import com.example.tutorialapp.data.User
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {
    @GET("courses")
    fun getCourses(): Call<List<Course>>

    @GET("icourses")
    fun getIntermediateCourses(): Call<List<Course>>

    @GET("ecourses")
    fun getExpertCourses(): Call<List<Course>>

    @GET("users")
    fun getUser(): Call<List<User>>

    @POST("user")
    fun addUser(@Body request: RequestBody): Call<List<User>>
}