package com.example.customviewbutton

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.customviewbutton.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        binding.myCustomButton.setListener {
            if (it == BittomAction.POSITIVE) {
                binding.myCustomButton.setPositiveText("oy yee baby")
            } else if (it == BittomAction.NEGATIVE) {
                binding.myCustomButton.setNegativeText("oy noy baby")
            }
        }
    }

}