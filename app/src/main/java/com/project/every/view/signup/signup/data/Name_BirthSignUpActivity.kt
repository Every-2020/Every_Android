package com.project.every.view.signup.signup.data

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.project.every.R
import com.project.every.base.BaseActivity
import com.project.every.base.SignUpData
import com.project.every.databinding.ActivityNameBirthBinding
import com.project.every.viewmodel.signup.signup.data.Name_BirthSignUpViewModel

class Name_BirthSignUpActivity : BaseActivity() {

    lateinit var binding : ActivityNameBirthBinding
    lateinit var viewModel : Name_BirthSignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this@Name_BirthSignUpActivity, R.layout.activity_name__birth)
        viewModel = ViewModelProviders.of(this@Name_BirthSignUpActivity).get(Name_BirthSignUpViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this@Name_BirthSignUpActivity

        birthCheck()
        toolbarInit(binding.toolbar)
    }

    private fun birthCheck(){
        binding.birthEditText.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(viewModel.NameBirthCheckType(binding.birthEditText.text.toString())){
                    if(viewModel.birth_check.value == null){
                        binding.nextButton.setBackgroundResource(R.drawable.gradient1)
                        binding.nextButton.isEnabled = true
                    } else{
                        binding.nextButton.setBackgroundResource(R.color.gray)
                        binding.nextButton.isEnabled = false
                    }
                }else{
                    binding.nextButton.setBackgroundResource(R.color.gray)
                    binding.nextButton.isEnabled = false
                }
            }
        })
    }

    override fun observerViewModel(){
        with(viewModel){
            onNameBirthNextEvent.observe(this@Name_BirthSignUpActivity, Observer {
                if(SignUpData.identityData == 0){
                    SignUpData.signUpDataStudent.name = binding.nameEditText.text.toString()
                    SignUpData.signUpDataStudent.birth_year = Integer.parseInt(viewModel.birth.value.toString())
                } else if(SignUpData.identityData == 1) {
                    SignUpData.signUpDataWorker.name = binding.nameEditText.text.toString()
                    SignUpData.signUpDataWorker.birth_year = Integer.parseInt(viewModel.birth.value.toString())
                }
                startActivity(Intent(this@Name_BirthSignUpActivity, PhoneSignUpActivity::class.java))
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            })
        }
    }
}
