package com.example.memoapp.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.memoapp.R
import com.example.memoapp.databinding.ActivityDrawBinding
import com.example.memoapp.databinding.DialogThemeBinding

class ThemeDialog(
    context: Activity,
    onToolItemSelected: (Int) -> Unit,
    onColorItemSelected: (Int) -> Unit,
    onSizeItemSelected: (Int) -> Unit
) : Dialog(context) {

    private var binding: DialogThemeBinding

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding = DataBindingUtil.setContentView(context, R.layout.dialog_theme)
        setContentView(R.layout.dialog_theme)
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

    private val onToolClickListener = View.OnClickListener() {
        with(binding) {
            ivPencil.setImageResource(0)
            ivBrush.setImageResource(0)
            ivHighlighter.setImageResource(0)
            ivSpray.setImageResource(0)
            ivEraser.setImageResource(0)
        }
        when (it.id) {
            R.id.iv_pencil -> {
                binding.ivPencil.setImageResource(R.drawable.bg_rectangle_white_blue_line)
            }

            R.id.iv_brush -> {
                binding.ivBrush.setImageResource(R.drawable.bg_rectangle_white_blue_line)
            }
            R.id.iv_highlighter -> {
                binding.ivHighlighter.setImageResource(R.drawable.bg_rectangle_white_blue_line)
            }
            R.id.iv_spray -> {
                binding.ivSpray.setImageResource(R.drawable.bg_rectangle_white_blue_line)
            }
            R.id.iv_eraser -> {
                binding.ivEraser.setImageResource(R.drawable.bg_rectangle_white_blue_line)
            }
        }
    }
}
