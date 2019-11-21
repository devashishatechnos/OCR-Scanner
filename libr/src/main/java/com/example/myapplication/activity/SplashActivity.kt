package com.example.myapplication.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.myapplication.R
import com.example.myapplication.activity.ApniClass.i
import com.example.myapplication.databinding.ActivitySplashBinding
import com.google.android.gms.vision.text.TextBlock


class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ScanFragment()
        //   setContentView(R.layout.activity_splash)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        binding.apply {
            binding.invalidateAll()
        }

        if (savedInstanceState == null) {
            Thread(Runnable {
                Thread.sleep(2000)
                startActivity(Intent(this, HomeActivity::class.java))
                finish()

            }).start()
        }



    }

    private fun ScanFragment() {
        var et_email="jsdxkj"
        var et_password="kdkm"
        val userMob = et_email.toString().trim { it <= ' ' }
        val userPassword = et_password.toString().trim { it <= ' ' }
    }



}
