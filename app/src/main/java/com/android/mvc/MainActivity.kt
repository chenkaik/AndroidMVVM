package com.android.mvc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.lib.util.showToast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        isDarkTheme(this)
//        startActivity<MainActivity>(this)
//        if ("hello" beginsWith "he"){
//
//        }
        "t".showToast(this)
//        R.string.app_name.showToast(this)
    }

}
