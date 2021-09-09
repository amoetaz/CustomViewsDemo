package com.moetaz.customviewsdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         ms.modulesStatus = ArrayList<Boolean>().apply {
             add(true)
             add(false)

             add(true)
             add(false)

             add(true)
             add(false)
             add(true)
             add(false)

             add(true)
             add(false)

             add(true)
             add(false)
         }
    }
}
