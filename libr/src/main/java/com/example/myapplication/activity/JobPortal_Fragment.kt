package com.example.atechnosattendence.Fragment

import android.app.Activity
import android.app.ProgressDialog
import android.os.Bundle

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myapplication.R


class JobPortal_Fragment : Fragment() {
    internal lateinit var pDialog: ProgressDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_result, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        /*news_back.setOnClickListener(View.OnClickListener {
            var fragment: Fragment = HomeFragment()
            val fragmentManager = activity!!.supportFragmentManager
            fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack("HomeFragment").commit()

        })*/
        pDialog = ProgressDialog(activity)

        GetJobPosts()

    }


    fun GetJobPosts() {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        pDialog.setMessage("Loading ...")
        pDialog.setCancelable(false)
        pDialog.show()


    }
}

