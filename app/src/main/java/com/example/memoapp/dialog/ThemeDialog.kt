package com.example.memoapp.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.memoapp.R
import com.example.memoapp.databinding.DialogThemeBinding
import com.example.memoapp.entity.DrawCanvas

class ThemeDialog(
    context: Context,
    canvas: DrawCanvas,
    onToolItemSelected: (DrawCanvas.Tools) -> Unit,
    onColorItemSelected: (DrawCanvas.Colors) -> Unit,
    onSizeItemSelected: (Int) -> Unit
) : Dialog(context) {

    private val TAG: String = javaClass.simpleName

    private lateinit var binding: DialogThemeBinding

    private lateinit var sizePreview: Drawable
    private lateinit var gradientDrawable: GradientDrawable

    private val canvas: DrawCanvas = canvas
    private val toolCallback: (DrawCanvas.Tools) -> Unit = onToolItemSelected
    private val colorCallback: (DrawCanvas.Colors) -> Unit = onColorItemSelected
    private val sizeCallback: (Int) -> Unit = onSizeItemSelected

    private val MAX_SIZE = 36
    private val MIN_SIZE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_theme,
            null,
            false
        )
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
            seekbarSize.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, level: Int, p2: Boolean) {
                    Log.d(TAG, "onProgressChanged : $level")
                    sizeCallback(level)
                    changeSizeOfPreview(level.toFloat())
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {

                }

                override fun onStopTrackingTouch(p0: SeekBar?) {

                }

            })
        }

        sizePreview = AppCompatResources.getDrawable(context, R.drawable.round_black_line)!!
        if( sizePreview is GradientDrawable)
            gradientDrawable = sizePreview as GradientDrawable

        initView()
    }

    fun initView() {

        // Initialize tool
        changeToToolSelectedImage(canvas.currentTool)

        // Initialize color
        if(canvas.currentTool == DrawCanvas.Tools.ERASER) {
            changeVisibilityForColor(false)
            changeStrokeColorOfSizePreview(ContextCompat.getColor(context, R.color.black))
        } else {
            changeVisibilityForColor(true)
            changeToColorSelectedImage(canvas.getColor())
        }

        // Initialize size seekbar
        binding.seekbarSize.progress = canvas.getSize()
    }

    // Size preview 의 컬러를 변경
    private fun changeStrokeColorOfSizePreview(color: Int) {
        if (sizePreview is GradientDrawable)
            gradientDrawable.setStroke(5, color)
        binding.ivSizePreview.setImageResource(R.drawable.round_black_line)
    }

    // Size preview 의 크기를 변경
    private fun changeSizeOfPreview(level : Float) {
        var size = MAX_SIZE * (level / 100) * 4
        if(size < 10)
            size = 10.0f
        Log.d(TAG,"size : ${size.toInt()}")
       if (sizePreview is GradientDrawable) {
           Log.d(TAG,"gradientDrawable")
           gradientDrawable.setSize(size.toInt(), size.toInt())
           binding.ivSizePreview.setImageResource(R.drawable.round_black_line)
       }
    }

    // Color 의 visibility 를 변경
    private fun changeVisibilityForColor(isEnable : Boolean) {
        with(binding) {
            tvColor.visibility = if (isEnable) View.VISIBLE else View.GONE
            linearColor.visibility =if (isEnable) View.VISIBLE else View.GONE
        }
    }

    private fun changeToToolSelectedImage(tool : DrawCanvas.Tools) {
        toolCallback(tool)

        with(binding) {
            ivPencil.setBackgroundResource(0)
            ivBrush.setBackgroundResource(0)
            ivHighlighter.setBackgroundResource(0)
            ivSpray.setBackgroundResource(0)
            ivEraser.setBackgroundResource(0)
        }

        when (tool) {
            DrawCanvas.Tools.PEN -> {
                binding.ivPencil.background =
                    ContextCompat.getDrawable(context, R.drawable.bg_rectangle_white_blue_line)
                changeVisibilityForColor(true)
                changeStrokeColorOfSizePreview(canvas.currentColor)
            }
            DrawCanvas.Tools.BRUSH -> {
                binding.ivBrush.background =
                    ContextCompat.getDrawable(context, R.drawable.bg_rectangle_white_blue_line)
                changeVisibilityForColor(true)
                changeStrokeColorOfSizePreview(canvas.currentColor)
            }
            DrawCanvas.Tools.HIGHLIGHTER -> {
                binding.ivHighlighter.background =
                    ContextCompat.getDrawable(context, R.drawable.bg_rectangle_white_blue_line)
                changeVisibilityForColor(true)
                changeStrokeColorOfSizePreview(canvas.currentColor)
            }
            DrawCanvas.Tools.SPRAY -> {
                binding.ivSpray.background =
                    ContextCompat.getDrawable(context, R.drawable.bg_rectangle_white_blue_line)
                changeVisibilityForColor(true)
                changeStrokeColorOfSizePreview(canvas.currentColor)
            }
            DrawCanvas.Tools.ERASER -> {
                binding.ivEraser.background =
                    ContextCompat.getDrawable(context, R.drawable.bg_rectangle_white_blue_line)
                changeVisibilityForColor(false)
                changeStrokeColorOfSizePreview(ContextCompat.getColor(context, R.color.black))
            }
        }
    }

    // Color 가 선택되면 선택된 이미지로 변경
    private fun changeToColorSelectedImage(colors: DrawCanvas.Colors) {

        colorCallback(colors)

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

        when (colors) {
            DrawCanvas.Colors.RED -> {
                binding.ivRed.setImageResource(R.drawable.round_red_clicked)
                changeStrokeColorOfSizePreview(ContextCompat.getColor(context, R.color.red))
            }
            DrawCanvas.Colors.ORANGE -> {
                binding.ivOrange.setImageResource(R.drawable.round_orange_clicked)
                changeStrokeColorOfSizePreview(ContextCompat.getColor(context, R.color.orange))
            }
            DrawCanvas.Colors.YELLOW -> {
                binding.ivYellow.setImageResource(R.drawable.round_yellow_clicked)
                changeStrokeColorOfSizePreview(ContextCompat.getColor(context, R.color.yellow))
            }
            DrawCanvas.Colors.GREEN -> {
                binding.ivGreen.setImageResource(R.drawable.round_green_clicked)
                changeStrokeColorOfSizePreview(ContextCompat.getColor(context, R.color.green))
            }
            DrawCanvas.Colors.BLUE -> {
                binding.ivBlue.setImageResource(R.drawable.round_blue_clicked)
                changeStrokeColorOfSizePreview(ContextCompat.getColor(context, R.color.blue))
            }
            DrawCanvas.Colors.NAVY -> {
                binding.ivNavy.setImageResource(R.drawable.round_navy_clicked)
                changeStrokeColorOfSizePreview(ContextCompat.getColor(context, R.color.navy))
            }
            DrawCanvas.Colors.PURPLE -> {
                binding.ivPurple.setImageResource(R.drawable.round_purple_clicked)
                changeStrokeColorOfSizePreview(ContextCompat.getColor(context, R.color.purple))
            }
            DrawCanvas.Colors.GRAY -> {
                binding.ivGray.setImageResource(R.drawable.round_gray_clicked)
                changeStrokeColorOfSizePreview(ContextCompat.getColor(context, R.color.gray))
            }
            DrawCanvas.Colors.BLACK -> {
                binding.ivBlack.setImageResource(R.drawable.round_black_clicked)
                changeStrokeColorOfSizePreview(ContextCompat.getColor(context, R.color.black))
            }
            DrawCanvas.Colors.WHITE -> {
                binding.ivWhite.setImageResource(R.drawable.round_white_clicked)
                changeStrokeColorOfSizePreview(ContextCompat.getColor(context, R.color.white))
            }
        }
    }

    private val onToolClickListener = View.OnClickListener {
        Log.d(TAG, "clicked " + it.id)

        when (it.id) {
            R.id.iv_pencil -> {
               changeToToolSelectedImage(DrawCanvas.Tools.PEN)
            }
            R.id.iv_brush -> {
               changeToToolSelectedImage(DrawCanvas.Tools.BRUSH)
            }
            R.id.iv_highlighter -> {
                changeToToolSelectedImage(DrawCanvas.Tools.HIGHLIGHTER)
            }
            R.id.iv_spray -> {
                changeToToolSelectedImage(DrawCanvas.Tools.SPRAY)
            }
            R.id.iv_eraser -> {
                changeToToolSelectedImage(DrawCanvas.Tools.ERASER)
            }
        }
    }

    private val onColorClickListener = View.OnClickListener {
        when (it.id) {
            R.id.iv_red -> {
                changeToColorSelectedImage(DrawCanvas.Colors.RED)
            }
            R.id.iv_orange -> {
                changeToColorSelectedImage(DrawCanvas.Colors.ORANGE)
            }
            R.id.iv_yellow -> {
                changeToColorSelectedImage(DrawCanvas.Colors.YELLOW)
            }
            R.id.iv_green -> {
                changeToColorSelectedImage(DrawCanvas.Colors.GREEN)
            }
            R.id.iv_blue -> {
                changeToColorSelectedImage(DrawCanvas.Colors.BLUE)
            }
            R.id.iv_navy -> {
                changeToColorSelectedImage(DrawCanvas.Colors.NAVY)
            }
            R.id.iv_purple -> {
                changeToColorSelectedImage(DrawCanvas.Colors.PURPLE)
            }
            R.id.iv_gray -> {
                changeToColorSelectedImage(DrawCanvas.Colors.GRAY)
            }
            R.id.iv_black -> {
                changeToColorSelectedImage(DrawCanvas.Colors.BLACK)
            }
            R.id.iv_white -> {
                changeToColorSelectedImage(DrawCanvas.Colors.WHITE)
            }
        }
    }
}
