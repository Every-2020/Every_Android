package com.project.every.view.student

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.project.every.R
import com.project.every.view.student.fragment.bamboo.StudentBambooFragment
import com.project.every.view.student.fragment.home.StudentHomeFragment
import com.project.every.view.student.fragment.more.StudentMoreFragment
import com.project.every.view.student.fragment.schedule.StudentScheduleFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.project.every.databinding.ActivityStudentMainBinding

class StudentMainActivity : AppCompatActivity() {

    lateinit var binding : ActivityStudentMainBinding

    val studentMainFragment = StudentHomeFragment()
    val studentBambooFragment = StudentBambooFragment()
    val studentScheduleFragment = StudentScheduleFragment()
    val studentMoreFragment = StudentMoreFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this@StudentMainActivity, R.layout.activity_student_main)
        binding.lifecycleOwner = this@StudentMainActivity

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, studentMainFragment).commitAllowingStateLoss()

        selectedEvent()
    }

    private fun selectedEvent(){
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(object : BottomNavigationView.OnNavigationItemSelectedListener{
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                when(item.itemId){
                    R.id.home -> {
                        fragmentTransaction.replace(R.id.frameLayout, studentMainFragment).commitAllowingStateLoss()
                        return true
                    }
                    R.id.bamboo -> {
                        fragmentTransaction.replace(R.id.frameLayout, studentBambooFragment).commitAllowingStateLoss()
                        return true
                    }
                    R.id.schedule -> {
                        fragmentTransaction.replace(R.id.frameLayout, studentScheduleFragment).commitAllowingStateLoss()
                        return true
                    }
                    R.id.more -> {
                        fragmentTransaction.replace(R.id.frameLayout, studentMoreFragment).commitAllowingStateLoss()
                        return true
                    }
                }
                return false
            }
        })
    }

    override fun onBackPressed() = ActivityCompat.finishAffinity(this@StudentMainActivity)
}
