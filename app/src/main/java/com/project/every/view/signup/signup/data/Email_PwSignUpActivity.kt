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
import com.project.every.databinding.ActivityEmailPwBinding
import com.project.every.network.SHA512
import com.project.every.viewmodel.signup.signup.data.Email_PwSignUpViewModel

class Email_PwSignUpActivity : BaseActivity() {

    lateinit var binding : ActivityEmailPwBinding
    lateinit var viewModel : Email_PwSignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this@Email_PwSignUpActivity, R.layout.activity_email__pw)
        viewModel = ViewModelProviders.of(this@Email_PwSignUpActivity).get(Email_PwSignUpViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this@Email_PwSignUpActivity

        pwCheck()
        emailCheck()
        toolbarInit(binding.toolbar)
    }

    private fun emailCheck(){
        binding.emailEditText.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?){}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int){}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(viewModel.EmailPwCheckType(binding.emailEditText.text.toString().trim(), 0))
                    viewModel.getOverlapEmail(binding.emailEditText.text.toString().trim())
            }
        })
    }

    private fun pwCheck(){
        binding.pwEditText.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?){}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int){}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(viewModel.EmailPwCheckType(binding.pwEditText.text.toString(), 1)){
                    if(viewModel.email_check.value == null && viewModel.pw_check.value == null){
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
            onEmailPwNextEvent.observe(this@Email_PwSignUpActivity, Observer {
                if(SignUpData.identityData == 0){
                    SignUpData.signUpDataStudent.email = viewModel.email.value.toString().trim()
                    SignUpData.signUpDataStudent.pw = SHA512.getSH512(viewModel.pw.value.toString().trim())
                } else if(SignUpData.identityData == 1) {
                    SignUpData.signUpDataWorker.email = viewModel.email.value.toString().trim()
                    SignUpData.signUpDataWorker.pw = viewModel.pw.value.toString().trim()
                }
                startActivity(Intent(this@Email_PwSignUpActivity, Name_BirthSignUpActivity::class.java))
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            })
        }
    }
}
