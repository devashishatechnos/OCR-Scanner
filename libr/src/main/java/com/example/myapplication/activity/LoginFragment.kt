package com.example.atechnosattendence.Fragment

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_splash.*


class LoginFragment : Fragment() {

    internal lateinit var pDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        pDialog = ProgressDialog(activity)

        jjj.setOnClickListener {
            var et_email: String
            var et_password: String
            val userMob = toString().trim { it <= ' ' }
            val userPassword = toString().trim { it <= ' ' }
            if (userMob.length > 0 && userPassword.length > 0) {
            } else {
                if (userMob.length == 0) {

                }/* else if (userMob.length > 10 || userMob.length < 10) {
                    et_email.error = "Please Enter Valid Mobile Number"
                }*/ else if (userPassword.length == 0) {

                }
            }
        }
    }

    private fun loginUser(username: String, password: String) {

        pDialog.setMessage("Login ...")
        pDialog.setCancelable(false)
        pDialog.show()
        /*  val fragment = HomeFragment()

                      var transaction = activity!!.supportFragmentManager.beginTransaction()
                      transaction.replace(R.id.container, fragment)

                    startActivity(intent)
                    activity?.finish()

                    //     Toast.makeText(activity, response.body()?.success, Toast.LENGTH_SHORT).show();

                } else if (response.body()?.code.equals("401")) {
                    Toast.makeText(activity, response.body()?.message, Toast.LENGTH_SHORT).show()
                } else if (response.body()?.code.equals("400")) {
                    Toast.makeText(activity, response.body()?.message, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(activity, response.body()?.message, Toast.LENGTH_SHORT).show()
                }

                pDialog.dismiss()
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
                pDialog.dismiss()

            }
        })
    }


    fun Text() {
        var a = 1
        var b= 2

        a = a.let { it + 2 }.let { val i = it + b
            i}
        println(a) //5
    }

}
}
                     */
    }
}
