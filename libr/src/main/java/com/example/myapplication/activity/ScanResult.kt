package com.example.myapplication.activity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityResultBinding
import java.util.*

class ScanResult : AppCompatActivity() {
    var result: String? = null

    private lateinit var binding: ActivityResultBinding

    public override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_result)

        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        binding.apply {
            binding.invalidateAll()
        }

        initViews()
    }


    private fun initViews() {
        binding.textview1!!.movementMethod = ScrollingMovementMethod()
        val extras = intent.extras
        if (extras != null) {
            result = extras.getString("ResultActivity")
        }
        binding.textview1!!.text = result

        binding.share.setOnClickListener {
            if (binding.textview1!!.text.toString().equals("No Text", ignoreCase = true)) {
                Toast.makeText(this, "There is no any text", Toast.LENGTH_SHORT).show()
            } else {
                val str2 = result
                val intent2 = Intent("android.intent.action.SEND")
                intent2.type = "text/plain"
                intent2.putExtra("android.intent.extra.SUBJECT", "Text Reader")
                intent2.putExtra("android.intent.extra.TEXT", str2)
                this@ScanResult.startActivity(Intent.createChooser(intent2, "Share"))
            }
        }

        binding.icBack.setOnClickListener {
            finish()
        }

        binding.copy.setOnClickListener {

            if (Random().nextInt(5) + 0 != 0) {
                (this@ScanResult.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).setPrimaryClip(
                    ClipData.newPlainText("text", binding.textview1!!.text.toString())
                )
                if (binding.textview1!!.text.toString().equals("No Text", ignoreCase = true)) {
                    Toast.makeText(this, "There is no any text", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Text Copied", Toast.LENGTH_SHORT).show()
                }
            } else {
                (this@ScanResult.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).setPrimaryClip(
                    ClipData.newPlainText("text", binding.textview1!!.text.toString())
                )
                if (binding.textview1!!.text.toString().equals("No Text", ignoreCase = true)
                ) {
                    Toast.makeText(this, "There is no any text", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Text Copied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}