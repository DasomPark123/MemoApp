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
    private val TAG: String = javaClass.simpleName

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
        Log.d(TAG, "init() currentTool : ${canvas.currentTool}")
    }

    private fun changeTool() {
        changeToolIcon(canvas.currentTool)
        if (canvas.currentTool != DrawCanvas.Tools.ERASER)
            canvas.setTool(DrawCanvas.Tools.ERASER)
        else
            canvas.setTool(canvas.previousTool)
    }

    private fun changeToolIcon(tool: DrawCanvas.Tools) {
        when (tool) {
            DrawCanvas.Tools.PEN -> binding.ivTool.setImageResource(R.drawable.ic_pencil_24)
            DrawCanvas.Tools.BRUSH -> binding.ivTool.setImageResource(R.drawable.ic_brush_24)
            DrawCanvas.Tools.HIGHLIGHTER -> binding.ivTool.setImageResource(R.drawable.ic_highlighter_24)
            DrawCanvas.Tools.SPRAY -> binding.ivTool.setImageResource(R.drawable.ic_spray_24)
            DrawCanvas.Tools.ERASER -> binding.ivTool.setImageResource(R.drawable.ic_eraser_24)
        }
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
                changeTool()
            }
            R.id.iv_color_and_type -> {
                val dialog = ThemeDialog(this, canvas,
                    onToolItemSelected = { selectedTool: DrawCanvas.Tools ->
                        canvas.setTool(selectedTool)

                        // 지우개가 선택된 경우 상단의 지우개 아이콘을 이전에 선택된 그리기 툴 아이콘으로 변경
                        if (selectedTool == DrawCanvas.Tools.ERASER)
                            changeToolIcon(canvas.previousTool)
                    },
                    onColorItemSelected = { selectedColor: DrawCanvas.Colors ->
                        canvas.setColor(selectedColor)
                    },
                    onSizeItemSelected = { selectedValue: Int ->
                        canvas.setSize(selectedValue)
                    })
                dialog.show()
            }
            R.id.iv_close_menu -> {

            }
        }
    }
}