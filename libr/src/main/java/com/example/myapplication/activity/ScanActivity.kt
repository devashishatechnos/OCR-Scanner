package com.example.myapplication.activity

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore.Images
import android.provider.Settings
import android.util.DisplayMetrics
import android.util.Log
import android.util.SparseArray
import android.view.SurfaceHolder
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myapplication.BoxDetector
import com.example.myapplication.R
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.Detector.Detections
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.text.TextBlock
import com.google.android.gms.vision.text.TextRecognizer
import com.google.android.gms.vision.text.TextRecognizer.Builder
import kotlinx.android.synthetic.main.activity_camera.*
import java.io.IOException
import java.lang.StringBuilder
import java.util.regex.Pattern


class ScanActivity() : AppCompatActivity() {

    lateinit var cameraSource: CameraSource

    var scanText = "No Text"
    var count = 0
    var counter = 0

    var f12a: String? = null
    var MY_CAMERA_REQUEST_CODE = 100
    private val sharedPrefFile = "ocrscanner"
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        sharedPreferences =
            this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        counter = sharedPreferences.getInt("counter", 0)
        Log.e("Scan", "" + sharedPreferences.getInt("counter", 0))

        if (counter > 150) {
            finish()
            Toast.makeText(this, "Limit exhausted", Toast.LENGTH_SHORT).show()
        } else {
            initViews()
            counter++
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putInt("counter", counter)
            editor.apply()
            editor.commit()
        }
    }


    private fun initViews() {
        var textRecognizer: TextRecognizer = TextRecognizer.Builder(applicationContext).build()

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        val width = displayMetrics.widthPixels
        val height = displayMetrics.heightPixels


        var boxDetector = BoxDetector(textRecognizer, width - 120, 80, width, height)

        if (boxDetector.isOperational) {


            //  surfaceview.holder.setFixedSize(width, width / 3)


            cameraSource = CameraSource.Builder(applicationContext, boxDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(width, height)
                .setRequestedFps(ApniClass.j)

                .setAutoFocusEnabled(true)
                .build()



            surfaceview.holder.addCallback(object : SurfaceHolder.Callback {
                override fun surfaceChanged(
                    holder: SurfaceHolder?,
                    format: Int,
                    width: Int,
                    height: Int
                ) {
                    /* cameraSource.apply {
                         surfaceview.holder.setFixedSize(width, height)
                         Log.e("ScanActivity1", "Width $width Height $height")

                     }*/
                }


                override fun surfaceDestroyed(holder: SurfaceHolder?) {
                    cameraSource.stop()
                }


                override fun surfaceCreated(holder: SurfaceHolder?) {

                    try {

                        if (ContextCompat.checkSelfPermission(
                                this@ScanActivity, Manifest.permission.CAMERA
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {

                            ActivityCompat.requestPermissions(
                                this@ScanActivity,
                                arrayOf("android.permission.CAMERA"),
                                MY_CAMERA_REQUEST_CODE
                            )


                        } else if (ContextCompat.checkSelfPermission(
                                this@ScanActivity, Manifest.permission.CAMERA
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            cameraSource.start(this@ScanActivity.surfaceview.holder)

                        }


                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }


            })

            boxDetector.setProcessor(object : Detector.Processor<TextBlock> {
                override fun release() {
                }

                override fun receiveDetections(detections: Detections<TextBlock>?) {
                    val detectedItems: SparseArray<*> = detections!!.detectedItems
                    if (detectedItems.size() != 0) {
                        scantxt.post(Runnable {
                            val sb = StringBuilder()
                            for (i in 0 until detectedItems.size()) {
                                if ((detectedItems.valueAt(i) as TextBlock).value.contains("-")) {
                                    if (count == 0) {
                                        sb.append((detectedItems.valueAt(i) as TextBlock).value)
                                        sb.append("\n")
                                        Log.e("Test0", "" + i + sb)
                                        // String regex = "^[A-Z0-9#-]+$"
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
                                            // count++
                                            break
                                        }
                                        sb.setLength(0)
                                    }
                                }
                            }
                            scantxt.setText(sb.toString())
                            scanText = scantxt.text.toString()
                        })

                    }
                }

            })

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults
        )

        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                try {
                    cameraSource.start(surfaceview.holder)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            } else {
                if (this == null) {

                    return
                }
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.CAMERA
                    )
                ) {
                    Toast.makeText(this, "Please Allow Camera Permission", Toast.LENGTH_SHORT)
                        .show()
                    finish()
                } else {
                    Toast.makeText(this, "Please Allow Camera Permission", Toast.LENGTH_SHORT)
                        .show()
                    finish()

                    var intent = Intent()
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    var uri = Uri.fromParts("package", applicationContext.getPackageName(), null)
                    intent.setData(uri)
                    startActivity(intent)

                }

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