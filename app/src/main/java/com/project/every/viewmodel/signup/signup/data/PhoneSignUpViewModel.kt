package com.project.every.viewmodel.signup.signup.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.project.every.base.BaseViewModel
import com.project.every.network.Data
import com.project.every.network.Response
import com.project.every.widget.SingleLiveEvent
import retrofit2.Call
import retrofit2.Callback
import java.util.regex.Pattern

class PhoneSignUpViewModel : BaseViewModel(){

    /**
     * getOverlapPhone 전화번호 중복확인 API Response
     * status[200] -> 전화번호 중복 없음 : onPhoneSuccessEvent
     * status[400] -> 전화번호 검증 오류
     * status[409] -> 전화번호 중복 확인 : onPhoneFailureEvent
     */

    // MutableLiveData
    val phone = MutableLiveData<String>()
    val phone_check = MutableLiveData<String>()

    // SingleLiveEvent
    val onPhoneSuccessEvent = SingleLiveEvent<Unit>()
    val onPhoneFailureEvent = SingleLiveEvent<Unit>()
    val onPhoneNextEvent = SingleLiveEvent<Unit>()

    fun checkType(text : String) : Boolean{
        if(!Pattern.compile("^01(?:0|1|[6-9])[-]?(?:\\d{4})[-]?\\d{4}$").matcher(text).matches()){
            phone_check.value = "전화번호의 형식이 올바르지 않습니다."
            return false
        }else {
            phone_check.value = null
            return true
        }
    }

    fun getOverlapPhone(text :String){
        val res : Call<Response<Data>> = netRetrofit.signUp.getOverlapPhone(text)
        res.enqueue(object : Callback<Response<Data>> {
            override fun onResponse(call: Call<Response<Data>>, response: retrofit2.Response<Response<Data>>) {
                when(response.code()){
                    200 -> {
                        phone_check.value = null
                        onPhoneSuccessEvent.call()
                    }
                    400 -> phone_check.value = "전화번호의 형식이 올바르지 않습니다."
                    409 -> {
                        phone_check.value = "중복된 전화번호가 이미 존재합니다."
                        onPhoneFailureEvent.call()
                    }
                }
            }
            override fun onFailure(call: Call<Response<Data>>, t: Throwable) {
                Log.e("getOverlapPhone[Error]", "전화번호 중복확인 과정에서 서버와 통신이 되지 않습니다.")
            }
        })
    } fun next() = onPhoneNextEvent.call()
}