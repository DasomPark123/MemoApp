package com.example.memoapp.entity

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.example.memoapp.R

//그림이 그려질 Canvas
class DrawCanvas @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val TAG = javaClass.simpleName

    enum class Tools {
        PEN, BRUSH, HIGHLIGHTER, SPRAY, ERASER
    }

    enum class Colors {
        RED, ORANGE, YELLOW, GREEN, BLUE, NAVY, PURPLE, GRAY, BLACK, WHITE
    }

    private val DEF_SIZE_PEN = 3
    private val DEF_SIZE_ERASER = 30

    //private val canvas : Canvas = Canvas()
    private val drawCommandList = ArrayList<Tool>()
    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private lateinit var tool: Tool
    private var drownImage: Bitmap? = null
    var previousTool: Tools = Tools.ERASER
    var currentTool: Tools = Tools.PEN
    var currentColor: Int = Color.BLACK
    var currentSize: Int = DEF_SIZE_PEN
    var currentEraserSize: Int = DEF_SIZE_ERASER

    private var drawCommandListForRedo = ArrayList<Tool>()

    // 현재 그린 그림을 bitmap으로 반환
    fun currentCanvas(): Bitmap {
        val bitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        this.draw(canvas)
        return bitmap
    }

    // Tool 타입을 변경
    fun changeTool(tools: Tools) {
        Log.d(TAG, "changeTool ${tools}")
        previousTool = currentTool
        when (tools) {
            Tools.PEN -> {
                currentTool = Tools.PEN
            }
            Tools.BRUSH -> {
                currentTool = Tools.BRUSH
            }
            Tools.HIGHLIGHTER -> {
                currentTool = Tools.HIGHLIGHTER
            }
            Tools.SPRAY -> {
                currentTool = Tools.SPRAY
            }
            Tools.ERASER -> {
                currentTool = Tools.ERASER
            }
        }
        Log.d(TAG, "previous : $previousTool, currentTool : $currentTool")
    }

    fun setTool(tool: Tools) {
        previousTool = currentTool
        currentTool = tool

        Log.d(TAG, "previous : $previousTool, currentTool : $currentTool")

//        // 지우개를 선택한 경우 배경색과 동일하게 색상 변경해 줌
//        if(currentTool == Tools.ERASER)
//            currentColor = ContextCompat.getColor(context, R.color.white)
    }

    // Color를 변경
    fun setColor(color: Colors) {
        when (color) {
            Colors.RED -> {
                currentColor = if (currentTool != Tools.HIGHLIGHTER)
                    ContextCompat.getColor(context, R.color.red)
                else
                    ContextCompat.getColor(context, R.color.translucent_red)
            }
            Colors.ORANGE -> {
                currentColor = if (currentTool != Tools.HIGHLIGHTER)
                    ContextCompat.getColor(context, R.color.orange)
                else
                    ContextCompat.getColor(context, R.color.translucent_orange)
            }
            Colors.YELLOW -> {
                currentColor = if (currentTool != Tools.HIGHLIGHTER)
                    ContextCompat.getColor(context, R.color.yellow)
                else
                    ContextCompat.getColor(context, R.color.translucent_yellow)
            }
            Colors.GREEN -> {
                currentColor = if (currentTool != Tools.HIGHLIGHTER)
                    ContextCompat.getColor(context, R.color.green)
                else
                    ContextCompat.getColor(context, R.color.translucent_green)
            }
            Colors.BLUE -> {
                currentColor = if (currentTool != Tools.HIGHLIGHTER)
                 ContextCompat.getColor(context, R.color.blue)
                else
                    ContextCompat.getColor(context, R.color.translucent_blue)
            }
            Colors.NAVY -> {
                currentColor = if (currentTool != Tools.HIGHLIGHTER)
                    ContextCompat.getColor(context, R.color.navy)
                else
                    ContextCompat.getColor(context, R.color.translucent_navy)
            }
            Colors.PURPLE -> {
                currentColor = if (currentTool != Tools.HIGHLIGHTER)
                    ContextCompat.getColor(context, R.color.purple)
                else
                    ContextCompat.getColor(context, R.color.translucent_navy)
            }
            Colors.GRAY -> {
                currentColor = if (currentTool != Tools.HIGHLIGHTER)
                    ContextCompat.getColor(context, R.color.gray)
                else
                    ContextCompat.getColor(context, R.color.translucent_gray)
            }
            Colors.BLACK -> {
                currentColor = if (currentTool != Tools.HIGHLIGHTER)
                    ContextCompat.getColor(context, R.color.black)
                else
                    ContextCompat.getColor(context, R.color.translucent_black)
            }
            Colors.WHITE -> {
                currentColor = if (currentTool != Tools.HIGHLIGHTER)
                    ContextCompat.getColor(context, R.color.white)
                else
                    ContextCompat.getColor(context, R.color.translucent_white)
            }
        }
    }

    fun getColor(): Colors {
        when (currentColor) {
            ContextCompat.getColor(context, R.color.red),
            ContextCompat.getColor(context, R.color.translucent_red)  -> {
                return Colors.RED
            }
            ContextCompat.getColor(context, R.color.orange),
            ContextCompat.getColor(context, R.color.translucent_orange) -> {
                return Colors.ORANGE
            }
            ContextCompat.getColor(context, R.color.yellow),
            ContextCompat.getColor(context, R.color.translucent_yellow)-> {
                return Colors.YELLOW
            }
            ContextCompat.getColor(context, R.color.green),
            ContextCompat.getColor(context, R.color.translucent_green)-> {
                return Colors.GREEN
            }
            ContextCompat.getColor(context, R.color.blue),
            ContextCompat.getColor(context, R.color.translucent_blue)-> {
                return Colors.BLUE
            }
            ContextCompat.getColor(context, R.color.navy),
            ContextCompat.getColor(context, R.color.translucent_navy)-> {
                return Colors.NAVY
            }
            ContextCompat.getColor(context, R.color.purple),
            ContextCompat.getColor(context, R.color.translucent_purple)-> {
                return Colors.PURPLE
            }
            ContextCompat.getColor(context, R.color.gray),
            ContextCompat.getColor(context, R.color.translucent_gray)-> {
                return Colors.GRAY
            }
            ContextCompat.getColor(context, R.color.black),
            ContextCompat.getColor(context, R.color.translucent_black)-> {
                return Colors.BLACK
            }
            ContextCompat.getColor(context, R.color.white),
            ContextCompat.getColor(context, R.color.translucent_white)-> {
                return Colors.WHITE
            }
        }
        return Colors.BLACK
    }

    // Size를 변경
    fun setSize(size: Int) {
        if (currentTool == Tools.ERASER)
            currentEraserSize = size
        else
            currentSize = size
    }

    fun getSize(): Int {
        if (currentTool == Tools.ERASER)
            return currentEraserSize

        return currentSize
    }

    // 현재 그림을 undo
    fun undo() {
        //Log.d(TAG,"undo() size : " + drawCommandList.size)
        if (drawCommandList.size <= 0)
            return

        drawCommandList.removeAt(drawCommandList.size - 1)
        invalidate()
    }

    // undo 했던 그림을 redo
    fun redo() {
        var isFirstFalse = true
        Log.d(TAG, "redo() size : " + drawCommandList.size)
        for (i in drawCommandList.size until drawCommandListForRedo.size) {
            Log.d(TAG, "redo() index : $i")
            if (isFirstFalse) {
                drawCommandList.add(drawCommandListForRedo[i])
                isFirstFalse = false
            } else {
                break
            }
        }
        invalidate()
    }

    private fun setAttribute(tool: Tool) {
        when (currentTool) {
            Tools.PEN -> {
                paint.style = Paint.Style.STROKE
                paint.color = tool.color
                paint.strokeCap = Paint.Cap.ROUND
                paint.strokeWidth = tool.size.toFloat()
            }
            Tools.BRUSH -> {

            }
            Tools.HIGHLIGHTER -> {
                paint.style = Paint.Style.STROKE
                paint.color = tool.color
                paint.strokeCap = Paint.Cap.ROUND
                paint.strokeWidth = tool.size.toFloat()
            }
            Tools.SPRAY -> {
                paint.style = Paint.Style.STROKE
                paint.color = tool.color
                paint.strokeCap = Paint.Cap.ROUND
                paint.strokeWidth = tool.size.toFloat()
                val blur = BlurMaskFilter( 10f, BlurMaskFilter.Blur.NORMAL)
                paint.setMaskFilter(blur)
            }
            Tools.ERASER -> {
                paint.style = Paint.Style.STROKE
                paint.color = tool.color
                paint.strokeCap = Paint.Cap.ROUND
                paint.strokeWidth = tool.size.toFloat()
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.d(TAG, "onDraw()")
        for (i in 0 until drawCommandList.size) {
            val tool: Tool = drawCommandList[i]
            setAttribute(tool)
            canvas?.drawPath(tool.path, paint)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d(TAG, "onTouchEvent : " + event!!.action)
        val x = event.x
        val y = event.y

        val action: Int = event.action
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                if (currentTool == Tools.ERASER)
                    tool = Tool(ContextCompat.getColor(context, R.color.white), currentEraserSize)
                else
                    tool = Tool(currentColor, currentSize)
                drawCommandList.add(tool)
                tool.path.moveTo(x, y)
            }
            MotionEvent.ACTION_MOVE -> {
                tool.path.lineTo(x, y)
            }
            MotionEvent.ACTION_UP -> {
                // canvas.drawPath(pen.path, paint)
                // redo 를 위해 저장
                drawCommandListForRedo.clear()
                drawCommandListForRedo.addAll(drawCommandList)
            }
        }

        invalidate()
        return true
    }
}