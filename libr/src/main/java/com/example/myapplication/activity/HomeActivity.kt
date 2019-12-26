package com.example.myapplication.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.apply {
            binding.invalidateAll()
        }

        /*  binding.ivScan.setOnClickListener {
              startActivity(Intent(this, ScanActivity::class.java))
          }*/
        startActivity(Intent(this, ScanActivity::class.java))
        finish()
    }

    fun reformat(
        str: String,
        normalizeCase: Boolean = true,
        upperCaseFirstLetter: Boolean = true,
        divideByCamelHumps: Boolean = false,
        wordSeparator: Char = ' '
    ) {
/*...*/
    }

    inline fun createString(block: StringBuilder.() -> Unit): String {
        val sb = StringBuilder()
        sb.block()
        return sb.toString()
    }
}