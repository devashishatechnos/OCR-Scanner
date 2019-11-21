package com.example.myapplication.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore.Images
import android.util.DisplayMetrics
import android.util.Log
import android.util.SparseArray
import android.view.SurfaceHolder
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.PointerIconCompat
import androidx.databinding.DataBindingUtil
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityCameraBinding
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.Detector.Detections
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.text.TextBlock
import com.google.android.gms.vision.text.TextRecognizer
import com.google.android.gms.vision.text.TextRecognizer.Builder
import kotlinx.android.synthetic.main.activity_camera.*
import java.io.IOException
import java.util.regex.Pattern


class ScanActivity() : AppCompatActivity() {

    private lateinit var binding: ActivityCameraBinding
    lateinit var cameraSource: CameraSource

    var scanText = "No Text"
    var count = 0
    var f12a: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_camera)
        binding.apply {
            binding.invalidateAll()
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        initViews()

    }

    fun addNumbers(n1: Double, n2: Double): Int {
        val sum = n1 + n2
        val sumInteger = sum.toInt()
        return sumInteger
    }

    fun main(args: Array<String>) {
        val number1 = 12.2
        val number2 = 3.4
        val result: Int
        result = addNumbers(number1, number2)
        println("result = $result")
    }

    private fun initViews() {
        var build: TextRecognizer = Builder(applicationContext).build()
        if (build.isOperational) {

            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)

            val width = displayMetrics.widthPixels
            val height = displayMetrics.heightPixels

            cameraSource = CameraSource.Builder(applicationContext, build)
                .setRequestedPreviewSize(width, height)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedFps(ApniClass.j)
                .setAutoFocusEnabled(true)
                .build()

            binding.surfaceview.holder.addCallback(object : SurfaceHolder.Callback {
                override fun surfaceChanged(
                    holder: SurfaceHolder?,
                    format: Int,
                    width: Int,
                    height: Int
                ) {
                }


                override fun surfaceDestroyed(holder: SurfaceHolder?) {
                    cameraSource.stop()
                }


                override fun surfaceCreated(holder: SurfaceHolder?) {

                    try {
                        if (ContextCompat.checkSelfPermission(
                                this@ScanActivity.applicationContext,
                                "android.permission.CAMERA"
                            ) != 0
                        ) {
                            ActivityCompat.requestPermissions(
                                this@ScanActivity,
                                arrayOf("android.permission.CAMERA"),
                                PointerIconCompat.TYPE_CONTEXT_MENU
                            )
                            return
                        }
                        cameraSource.start(this@ScanActivity.surfaceview.holder)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }


            })

            fun reformat(
                str: String,
                normalizeCase: Boolean = true,
                upperCaseFirstLetter: Boolean = true,
                divideByCamelHumps: Boolean = false,
                wordSeparator: Char = ' '
            ) {
/*...*/
            }

            fun foo(bar: Int = 0, baz: Int = 1, qux: () -> Unit) { /*...*/
            }

            foo(1) { println("hello") }     // Uses the default value baz = 1
            foo(qux = { println("hello") }) // Uses both default values bar = 0 and baz = 1
            foo { println("hello") }

            binding.scanbtn.setOnClickListener {

                cameraSource.stop()


                var intent = Intent()
                intent.putExtra("scanText", scanText);
                setResult(Activity.RESULT_OK, intent);
                finish();
                /* val intent = Intent(this, ResultActivity::class.java)
                 intent.putExtra("ResultActivity", scanText)
                 startActivity(intent)
                 finish()*/
            }



            fun main(args: Array<String>) {
                val demo = Outer()
                print(demo)
            }

            class Outer {
                private val welcomeMessage: String = "Welcome Scanner"

                inner class Nested {
                    fun foo() = welcomeMessage
                }
            }


            build.setProcessor(object : Detector.Processor<TextBlock> {
                override fun release() {
                }

                override fun receiveDetections(detections: Detections<TextBlock>?) {
                    val detectedItems: SparseArray<*> = detections!!.detectedItems
                    if (detectedItems.size() != 0) {
                        binding.scantxt.post(Runnable {
                            val sb = StringBuilder()
                            for (i in 0 until detectedItems.size()) {
                                if ((detectedItems.valueAt(i) as TextBlock).value.contains("-")) {
                                    if (count == 0) {
                                        sb.append((detectedItems.valueAt(i) as TextBlock).value)
                                        sb.append("\n")
                                        Log.e("Test0", "" + i + sb)
                                        // String regex = "^[A-Z0-9#-]+$";
                                        val regex = "^[A-Z0-9]{4}(-[A-Z0-9]{4}){3}"
                                        val pat = Pattern.compile(regex)
                                        Log.e("Test", "" + sb.toString().length)
                                        if (pat.matcher(sb.toString().trim { it <= ' ' }.toUpperCase()).matches()
                                            && sb.toString().trim { it <= ' ' }.length == ApniClass.i
                                        ) {
                                            cameraSource.stop()
                                            /*val intent = Intent(
                                                this@ScanActivity,
                                                ResultActivity::class.java
                                            )
                                            intent.putExtra(
                                                "ResultActivity",
                                                sb.toString().trim { it <= ' ' }.toUpperCase()
                                            )
                                            startActivity(intent)
                                            finish()*/

                                            var intent = Intent()
                                            intent.putExtra(
                                                "scanText",
                                                sb.toString().trim { it <= ' ' }.toUpperCase()
                                            )
                                            setResult(Activity.RESULT_OK, intent)
                                            finish()
                                            count++
                                            break
                                        }
                                        sb.setLength(0)
                                    }
                                }
                            }
                            binding.scantxt.setText(sb.toString())
                            scanText = binding.scantxt.text.toString()
                        })

                    }
                }

            })

        }
    }

    override fun onRequestPermissionsResult(
        i: Int, strArr: Array<String?>, iArr: IntArray
    ) {
        if (i == 1001 && iArr[0] == 0 && ContextCompat.checkSelfPermission(
                this,
                "android.permission.CAMERA"
            ) == 0
        ) {
            try {
                cameraSource.start(binding.surfaceview.holder)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun onActivityResult(i: Int, i2: Int, intent: Intent?) {
        val bitmap: Bitmap?
        super.onActivityResult(i, i2, intent)
        if (i == 1 && i2 == -1 && intent != null && intent.data != null) {
            bitmap = try {
                Images.Media.getBitmap(contentResolver, intent.data)
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }
            val build = Builder(applicationContext).build()
            if (build.isOperational) {
                val detect: SparseArray<*> =
                    build.detect(Frame.Builder().setBitmap(bitmap).build())
                val sb = java.lang.StringBuilder()
                for (i3 in 0 until detect.size()) {
                    sb.append((detect.valueAt(i3) as TextBlock).value)
                    sb.append("\n")
                }
                this.f12a = sb.toString()
              /*  val intent2 = Intent(this, ResultActivity::class.java)
                intent2.putExtra("ResultActivity", this.f12a)
                startActivity(intent2)
                finish()*/


            }
        }
    }
}