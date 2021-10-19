package com.example.memoapp.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.memoapp.R
import com.example.memoapp.databinding.DialogThemeBinding

class ThemeDialog(
    context: Context,
    onToolItemSelected: (Int) -> Unit,
    onColorItemSelected: (Int) -> Unit,
    onSizeItemSelected: (Int) -> Unit
) : Dialog(context) {

    private val TAG : String = javaClass.simpleName

    private lateinit var binding: DialogThemeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_theme, null, false)
        setContentView(binding.root)
        val params = window!!.attributes
        params.width = WindowManager.LayoutParams.WRAP_CONTENT
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        params.dimAmount = 0.0f
        window!!.attributes = params
        with(binding) {
            ivPencil.setOnClickListener(onToolClickListener)
            ivBrush.setOnClickListener(onToolClickListener)
            ivHighlighter.setOnClickListener(onToolClickListener)
            ivSpray.setOnClickListener(onToolClickListener)
            ivEraser.setOnClickListener(onToolClickListener)
        }
    }

    private val onToolClickListener : View.OnClickListener = View.OnClickListener() {
        Log.d(TAG, "clicked " + it.id)

        with(binding) {
            ivPencil.setBackgroundResource(0)
            ivBrush.setBackgroundResource(0)
            ivHighlighter.setBackgroundResource(0)
            ivSpray.setBackgroundResource(0)
            ivEraser.setBackgroundResource(0)
        }

        when (it.id) {
            R.id.iv_pencil -> {
                binding.ivPencil.background = ContextCompat.getDrawable(context, R.drawable.bg_rectangle_white_blue_line)
            }

            R.id.iv_brush -> {
                binding.ivBrush.background = ContextCompat.getDrawable(context, R.drawable.bg_rectangle_white_blue_line)
            }
            R.id.iv_highlighter -> {
                binding.ivHighlighter.background = ContextCompat.getDrawable(context, R.drawable.bg_rectangle_white_blue_line)
            }
            R.id.iv_spray -> {
                binding.ivSpray.background = ContextCompat.getDrawable(context, R.drawable.bg_rectangle_white_blue_line)
            }
            R.id.iv_eraser -> {
                binding.ivEraser.background = ContextCompat.getDrawable(context, R.drawable.bg_rectangle_white_blue_line)
            }
        }
    }
}
