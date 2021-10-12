package com.example.memoapp

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.example.memoapp.databinding.ActivityDrawBinding
import com.example.memoapp.dialog.ThemeDialog
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
        binding.ivMenu.setOnClickListener(onClickListener)
        binding.ivUndo.setOnClickListener(onClickListener)
        binding.ivRedo.setOnClickListener(onClickListener)
        binding.ivColorAndType.setOnClickListener(onClickListener)
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
                    binding.ivTool.setImageResource(R.drawable.round_pencil_24)
                    canvas.changeTool(DrawCanvas.Tool.ERASER)
                }
                else if(canvas.currentTool == DrawCanvas.Tool.ERASER) {
                    binding.ivTool.setImageResource(R.drawable.ic_eraser_24)
                    canvas.changeTool(DrawCanvas.Tool.PEN)
                }
            }
            R.id.iv_color_and_type -> {
//                var intent = Intent(this, ThemeDialog::class.java)
//                startActivity(intent)
//                val dialog = LayoutInflater.from(this).inflate(R.layout.dialog_theme, null)
//                val builder = AlertDialog.Builder(this)
//                    .setView(dialog)
//                val alertDialog = builder.show()
                val dialog = Dialog(this)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setContentView(R.layout.dialog_theme)
                val params = dialog.window!!.attributes
                params.width = WindowManager.LayoutParams.WRAP_CONTENT
                params.height = WindowManager.LayoutParams.WRAP_CONTENT
                dialog.window!!.attributes = params
                dialog.show()
            }
            R.id.iv_close_menu -> {

            }
        }
    }
}