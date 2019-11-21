package com.example.atechnosattendence.Fragment

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.os.Bundle

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myapplication.R

import java.text.SimpleDateFormat
import java.util.*
import javax.security.auth.callback.Callback

class Apply_For_leave_Fragment : Fragment() {
    internal lateinit var pDialog: ProgressDialog
    var cal = Calendar.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.activity_camera, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        pDialog = ProgressDialog(activity)


        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(
                view: DatePicker, year: Int, monthOfYear: Int,
                dayOfMonth: Int
            ) {

                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            }
        }

        val toDateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(
                view: DatePicker, year: Int, monthOfYear: Int,
                dayOfMonth: Int
            ) {

                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            }
        }

        fun updateDateInView(Btn: Button) {
            val myFormat = "yyyy/MM/dd" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            Btn!!.text = sdf.format(cal.time)
        }

        fun ApplyLeave(emp_id: String, from_date: String, to_date: String, reason_: String) {

            pDialog.setMessage("Submitting ...")
            pDialog.setCancelable(false)
            pDialog.show()


        }
    }
}




