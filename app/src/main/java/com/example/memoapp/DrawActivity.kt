package com.example.memoapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.memoapp.databinding.ActivityDrawBinding
import com.example.memoapp.entity.DrawCanvas

class DrawActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDrawBinding

    private lateinit var canvas : DrawCanvas
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_draw)
        init()
    }

    private fun init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_draw)
        canvas = DrawCanvas(applicationContext)
        binding.linearCanvas.addView(canvas)
    }
}