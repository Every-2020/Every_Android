package com.project.every.network

import com.project.every.network.retrofit.interfaces.signin.SignIn
import com.project.every.network.retrofit.interfaces.signup.SignUp
import com.project.every.network.retrofit.interfaces.student.Bamboo
import com.project.every.network.retrofit.interfaces.student.Home
import com.project.every.network.retrofit.interfaces.student.More
import com.project.every.network.retrofit.interfaces.student.Schedule
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetRetrofit{
    val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl("http://49.50.160.97:8080/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val signIn : SignIn = retrofit.create(SignIn::class.java)
    val signUp : SignUp = retrofit.create(SignUp::class.java)
    val bamboo : Bamboo = retrofit.create(Bamboo::class.java)
    val home : Home = retrofit.create(Home::class.java)
    val schedule : Schedule = retrofit.create(Schedule::class.java)
    val more : More = retrofit.create(More::class.java)

    companion object{
        val instance = NetRetrofit()
    }
}