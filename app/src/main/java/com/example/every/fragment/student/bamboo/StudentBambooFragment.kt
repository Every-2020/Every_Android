package com.example.every.fragment.student.bamboo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.every.R
import com.example.every.base.view.student.tokenData
import com.example.every.activity.signin.SignInActivity
import com.example.every.activity.student.bamboo.BambooPostActivity
import com.example.every.base.view.student.BaseStudentFragment
import com.example.every.databinding.FragmentStudentBambooBinding
import com.example.every.fragment.student.bamboo.adapter.BambooPostAdapter
import com.example.every.viewmodel.student.bamboo.StudentBambooFragmentViewModel

class StudentBambooFragment : BaseStudentFragment() {

    lateinit var binding : FragmentStudentBambooBinding
    lateinit var viewModel : StudentBambooFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_student_bamboo, container, false)
        viewModel = ViewModelProviders.of(this@StudentBambooFragment).get(StudentBambooFragmentViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this@StudentBambooFragment

        observerViewModel()
        refreshLayout()
        return binding.root
    }
    override fun onResume() {
        super.onResume()
        viewModel.getBambooPost(tokenData.token.value.toString())
    }
    fun observerViewModel(){
        with(viewModel){
            onSuccessEvent.observe(this@StudentBambooFragment, Observer {
                val adapter = BambooPostAdapter(context!!.applicationContext, viewModel.bambooPostDataList)
                binding.recyclerView.adapter = adapter
            })
            onFailEvent.observe(this@StudentBambooFragment, Observer {
            })
            onTokenEvent.observe(this@StudentBambooFragment, Observer {
                val loginData = context!!.applicationContext.getSharedPreferences("checkLogin", Context.MODE_PRIVATE)
                val loginData_editor = loginData.edit()

                loginData_editor.putBoolean("loginData", false)
                loginData_editor.commit()

                Toast.makeText(context!!.applicationContext, "토큰이 만료되었습니다. 로그인 화면으로 이동합니다.", Toast.LENGTH_SHORT).show()
                startActivity(Intent(context!!.applicationContext, SignInActivity::class.java))
                activity!!.finish()
            })
            onPostEvent.observe(this@StudentBambooFragment, Observer {
               startActivity(Intent(binding.root.context, BambooPostActivity::class.java))
            })
        }
    }
    fun refreshLayout(){
        binding.swipeRefreshLayout.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener{
            override fun onRefresh() {
                viewModel.getBambooPost(tokenData.token.value.toString())
                Handler().postDelayed({ binding.swipeRefreshLayout.isRefreshing = false }, 500)
            }
        })
    }
}