package com.example.memoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
        binding.ivUndo.setOnClickListener(onClickListener)
        binding.ivRedo.setOnClickListener(onClickListener)
        binding.ivTool.setOnClickListener(onClickListener)
    }

    private val onClickListener = View.OnClickListener() {
        when(it.id) {
            R.id.iv_menu -> {

            }
            R.id.iv_undo -> {
                canvas.undo()
            }
            R.id.iv_redo -> {
                canvas.redo()
            }
            R.id.iv_tool -> {
                if(canvas.currentTool == DrawCanvas.Tool.PEN) {
                    binding.ivTool.setImageResource(R.drawable.round_brush_24)
                    canvas.changeTool(DrawCanvas.Tool.ERASER)
                }
                else if(canvas.currentTool == DrawCanvas.Tool.ERASER) {
                    binding.ivTool.setImageResource(R.drawable.eraser_variant)
                    canvas.changeTool(DrawCanvas.Tool.PEN)
                }
            }
            R.id.iv_color_and_type -> {

            }
            R.id.iv_close_menu -> {

            }
        }
    }
}