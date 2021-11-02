package com.example.memoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.memoapp.databinding.ActivityDrawBinding
import com.example.memoapp.dialog.ThemeDialog
import com.example.memoapp.entity.DrawCanvas

class DrawActivity : AppCompatActivity() {
    private val TAG : String = javaClass.simpleName

    private lateinit var binding: ActivityDrawBinding

    private lateinit var canvas: DrawCanvas
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_draw)
        init()
    }

    private fun init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_draw)
        canvas = DrawCanvas(applicationContext)
        binding.linearCanvas.addView(canvas)
        binding.ivMenu.setOnClickListener(onClickListener)
        binding.ivUndo.setOnClickListener(onClickListener)
        binding.ivRedo.setOnClickListener(onClickListener)
        binding.ivColorAndType.setOnClickListener(onClickListener)
        binding.ivTool.setOnClickListener(onClickListener)
    }

    private val onClickListener = View.OnClickListener() {
        when (it.id) {
            R.id.iv_menu -> {

            }
            R.id.iv_undo -> {
                canvas.undo()
            }
            R.id.iv_redo -> {
                canvas.redo()
            }
            R.id.iv_tool -> {
                if (canvas.currentTool == DrawCanvas.Tools.PEN) {
                    binding.ivTool.setImageResource(R.drawable.round_pencil_24)
                    canvas.changeTool(DrawCanvas.Tools.ERASER)
                } else if (canvas.currentTool == DrawCanvas.Tools.ERASER) {
                    binding.ivTool.setImageResource(R.drawable.ic_eraser_24)
                    canvas.changeTool(DrawCanvas.Tools.PEN)
                }
            }
            R.id.iv_color_and_type -> {
                val dialog = ThemeDialog(this, canvas,
                    onToolItemSelected = { selectedTool : DrawCanvas.Tools ->
                        canvas.setTool(selectedTool)
                    },
                    onColorItemSelected = { selectedColor : DrawCanvas.Colors ->
                        canvas.setColor(selectedColor)
                    },
                    onSizeItemSelected = { selectedValue : Int ->
                        canvas.setSize(selectedValue)
                    })
                dialog.show()
            }
            R.id.iv_close_menu -> {

            }
        }
    }
}