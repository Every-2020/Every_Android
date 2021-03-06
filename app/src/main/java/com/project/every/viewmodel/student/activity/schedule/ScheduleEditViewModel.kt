package com.project.every.viewmodel.student.activity.schedule

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.project.every.base.BaseViewModel
import com.project.every.base.StudentData
import com.project.every.network.Data
import com.project.every.network.Response
import com.project.every.network.request.model.student.schedule.SchedulePostData
import com.project.every.widget.SingleLiveEvent
import retrofit2.Call
import retrofit2.Callback

class ScheduleEditViewModel : BaseViewModel(){

    /**
     * getIdxSchedule 게시글 일부 조회 API Response
     * status[200] 게시글 일부 조회 성공
     */

    // MutableLiveData
    val idx = MutableLiveData<Int>()
    val title =  MutableLiveData<String>()
    val content = MutableLiveData<String>()
    val start_date = MutableLiveData<String>()
    val end_date = MutableLiveData<String>()

    fun getIdxSchedule(){
        val res : Call<Response<Data>> = netRetrofit.schedule.getIdxSchedule(StudentData.token.value.toString(), idx.value!!)
        res.enqueue(object : Callback<Response<Data>>{
            override fun onResponse(call: Call<Response<Data>>, response: retrofit2.Response<Response<Data>>) {
                if(response.code() == 200){
                    val scheduleData = response.body()!!.data!!.schedule

                    title.value = scheduleData!!.title
                    content.value = scheduleData!!.content
                    start_date.value = scheduleData!!.start_date
                    end_date.value = scheduleData!!.end_date
                }
            }
            override fun onFailure(call: Call<Response<Data>>, t: Throwable) {
                Log.e("getIdxSchedule[Error]", "스케줄 일부 조회 과정에서 서버와 통신되지 않습니다.")
            }
        })
    }

    /**
     * editSchedule 일정 변경 API Response
     * status[200] 일정 변경 성공 : onSelectedEditSuccessEvent
     */

    // MutableLiveData
    val check_date = MutableLiveData<Int>()

    // SingleLiveEvent
    val onSelectedEvent = SingleLiveEvent<Unit>()
    val onScheduleEditSuccessEvent = SingleLiveEvent<Unit>()
    val onScheduleEditFailureEvent = SingleLiveEvent<Unit>()

    fun editSchedule(){
        if(checkData()){
            val schedulePostData = SchedulePostData(title.value.toString(), content.value.toString(), start_date.value.toString(), end_date.value.toString())
            val res : Call<Response<Data>> = netRetrofit.schedule.editSchedule(StudentData.token.value.toString(), schedulePostData, idx.value!!)
            res.enqueue(object : Callback<Response<Data>>{
                override fun onResponse(call: Call<Response<Data>>, response: retrofit2.Response<Response<Data>>) {
                    if(response.code() == 200) onScheduleEditSuccessEvent.call()
                }
                override fun onFailure(call: Call<Response<Data>>, t: Throwable) {
                    Log.e("editSchedule[Error]", "일정 수정 과정에서 서버와 통신되지 않습니다.")
                }
            })
        }else onScheduleEditFailureEvent.call()
    }

    fun chooseDate(check : Int){
        check_date.value = check
        onSelectedEvent.call()
    }

    fun checkData() : Boolean{
        return if(!title.value.isNullOrBlank()){
            if(!content.value.isNullOrBlank()){
                if(start_date.value!!.substring(0, 4) <= end_date.value!!.substring(0, 4) &&
                    start_date.value!!.substring(5, 7) <= end_date.value!!.substring(5, 7) &&
                    start_date.value!!.substring(8, 10) <= end_date.value!!.substring(8, 10)){
                    return true
                }else false
            }else false
        }else false
    }
}