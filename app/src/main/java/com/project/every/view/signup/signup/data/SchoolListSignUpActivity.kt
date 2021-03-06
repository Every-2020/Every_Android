package com.project.every.view.signup.signup.data

import android.os.Bundle
import android.view.Window
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.project.every.R
import com.project.every.base.BaseActivity
import com.project.every.databinding.ActivitySchoolListBinding
import com.project.every.viewmodel.signup.signup.data.SchoolListSignUpViewModel

class SchoolListSignUpActivity : BaseActivity() {

    lateinit var binding : ActivitySchoolListBinding
    lateinit var viewModel : SchoolListSignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)

        binding = DataBindingUtil.setContentView(this@SchoolListSignUpActivity, R.layout.activity_school_list)
        viewModel = ViewModelProviders.of(this@SchoolListSignUpActivity).get(SchoolListSignUpViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this@SchoolListSignUpActivity

        editTextListener()
    }

    private fun editTextListener(){
        binding.schoolName.imeOptions = EditorInfo.IME_ACTION_SEARCH
        binding.schoolName.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            when(actionId){
                EditorInfo.IME_ACTION_SEARCH -> viewModel.getSchool()
            }
            true
        })
    }

    override fun observerViewModel(){
        with(viewModel){
            onGetSchoolSuccessEvent.observe(this@SchoolListSignUpActivity, Observer {
                setVisible(binding.questionLayout, binding.recyclerView, 1)
                val adapter = SchoolAdapter(this@SchoolListSignUpActivity, viewModel.schoolDataList)
                binding.recyclerView.adapter = adapter
            })
            onGetSchoolFailureEvent.observe(this@SchoolListSignUpActivity, Observer {
                setVisible(binding.questionLayout, binding.recyclerView, 0)
                binding.answerTextView.text = "입력하신 학교는 존재하지 않습니다."
            })
        }
    }
}
