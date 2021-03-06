package com.project.every.network.retrofit.interfaces.student

import com.project.every.network.Data
import com.project.every.network.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface Home {

    @GET("school/meal")
    fun getMeal(@Header("token") token : String) : Call<Response<Data>>
}