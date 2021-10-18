package com.example.memoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.memoapp.databinding.ActivityDrawBinding
import com.example.memoapp.dialog.ThemeDialog
import com.example.memoapp.entity.DrawCanvas

class DrawActivity : AppCompatActivity() {
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
                if (canvas.currentTool == DrawCanvas.Tool.PEN) {
                    binding.ivTool.setImageResource(R.drawable.round_pencil_24)
                    canvas.changeTool(DrawCanvas.Tool.ERASER)
                } else if (canvas.currentTool == DrawCanvas.Tool.ERASER) {
                    binding.ivTool.setImageResource(R.drawable.ic_eraser_24)
                    canvas.changeTool(DrawCanvas.Tool.PEN)
                }
            }
            R.id.iv_color_and_type -> {
                val dialog = ThemeDialog(this,
                    onToolItemSelected = { itemIndex : Int ->
                        //TODO : 선택된 툴로 변경
                    },
                    onColorItemSelected = { itemIndex : Int ->
                        //TODO : 선택된 컬러로 변경
                    },
                    onSizeItemSelected = { selectedValue : Int ->
                        //TODO : 선택된 사이즈로 변경
                    })
                dialog.show()
            }
            R.id.iv_close_menu -> {

            }
        }
    }
}