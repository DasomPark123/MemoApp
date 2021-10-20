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
            ivRed.setOnClickListener(onColorClickListener)
            ivOrange.setOnClickListener(onColorClickListener)
            ivYellow.setOnClickListener(onColorClickListener)
            ivGreen.setOnClickListener(onColorClickListener)
            ivBlue.setOnClickListener(onColorClickListener)
            ivNavy.setOnClickListener(onColorClickListener)
            ivPurple.setOnClickListener(onColorClickListener)
            ivGray.setOnClickListener(onColorClickListener)
            ivBlack.setOnClickListener(onColorClickListener)
            ivWhite.setOnClickListener(onColorClickListener)
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

    private val onColorClickListener = View.OnClickListener {

        with(binding) {
            ivRed.setImageResource(R.drawable.round_red_unclicked)
            ivOrange.setImageResource(R.drawable.round_orange_unclicked)
            ivYellow.setImageResource(R.drawable.round_yellow_unclicked)
            ivGreen.setImageResource(R.drawable.round_green_unclicked)
            ivBlue.setImageResource(R.drawable.round_blue_unclicked)
            ivNavy.setImageResource(R.drawable.round_navy_unclicked)
            ivPurple.setImageResource(R.drawable.round_purple_unclicked)
            ivGray.setImageResource(R.drawable.round_gray_unclicked)
            ivBlack.setImageResource(R.drawable.round_black_unclicked)
            ivWhite.setImageResource(R.drawable.round_whilte_unclicked)
        }
        when(it.id) {
            R.id.iv_red -> {
                binding.ivRed.setImageResource(R.drawable.round_red_clicked)
            }
            R.id.iv_orange -> {
                binding.ivOrange.setImageResource(R.drawable.round_orange_clicked)
            }
            R.id.iv_yellow -> {
                binding.ivYellow.setImageResource(R.drawable.round_yellow_clicked)
            }
            R.id.iv_green -> {
                binding.ivGreen.setImageResource(R.drawable.round_green_clicked)
            }
            R.id.iv_blue -> {
                binding.ivBlue.setImageResource(R.drawable.round_blue_clicked)
            }
            R.id.iv_navy -> {
                binding.ivNavy.setImageResource(R.drawable.round_navy_clicked)
            }
            R.id.iv_purple -> {
                binding.ivPurple.setImageResource(R.drawable.round_purple_clicked)
            }
            R.id.iv_gray -> {
                binding.ivGray.setImageResource(R.drawable.round_gray_clicked)
            }
            R.id.iv_black -> {
                binding.ivBlack.setImageResource(R.drawable.round_black_clicked)
            }
            R.id.iv_white -> {
                binding.ivWhite.setImageResource(R.drawable.round_white_clicked)
            }
        }
    }
}
