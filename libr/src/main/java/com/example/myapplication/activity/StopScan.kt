package com.example.myapplication.activity

import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

class StopScan() : BaseAdapter() {

    fun main(args: Array<String>) {
        if (args.size == 0) {
            println("Please Show Image")
            return
        }
        println("Hello, ${args[0]}!")
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItem(position: Int): Any {

        fun main(args: Array<String>) {
            if (args.size == 0) {
                println("Please provide a name as a command-line argument")
                return
            }
            println("Hello, ${args[0]}!")
        }
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemId(position: Int): Long {

        fun main(args: Array<String>) {
            if (args.size == 0) {
                println("Please provide a name as a command-line argument")
                return
            }
            println("Hello, ${args[0]}!")
        }
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}