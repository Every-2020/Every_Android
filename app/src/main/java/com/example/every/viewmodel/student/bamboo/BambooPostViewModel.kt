package com.example.every.viewmodel.student.bamboo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.every.activity.base.student.tokenData
import com.example.every.network.Data
import com.example.every.network.Response
import com.example.every.network.request.model.student.BambooPostData
import com.example.every.viewmodel.base.student.BaseStudentFragmentViewModel
import retrofit2.Call
import retrofit2.Callback

class BambooPostViewModel : BaseStudentFragmentViewModel(){

    val content_EditText = MutableLiveData<String>()

    fun addPost(){
        if(emptyContent()){
            val bambooPostData = BambooPostData(content_EditText.value.toString())
            val res : Call<Response<Data>> = netRetrofit.bamboo.postBamboo("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZHgiOjE1LCJleHAiOjE1ODIyMTUwMTl9.SKb7MBnDQAo6ov_Le67RFoio6MnRT5bBd8YUM3BD5Jo", bambooPostData)
            res.enqueue(object : Callback<Response<Data>>{
                override fun onResponse(call: Call<Response<Data>>, response: retrofit2.Response<Response<Data>>) {
                    when(response.code()){
                        201 -> onSuccessEvent.call()
                        500 -> Log.e("test", "왜 500이냐")
                    }
                }
                override fun onFailure(call: Call<Response<Data>>, t: Throwable) {
                    Log.e("addPost[Error]", "대나무숲 게시글 추가 과정에서 서버와 통신이 되지 않습니다.")
                }
            })
        }else{
            onFailEvent.call()
        }
    }

    fun emptyContent() : Boolean{
        if(content_EditText.value.toString().isNullOrEmpty()) return false
        else return true
    }
}