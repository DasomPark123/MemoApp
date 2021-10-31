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
        PEN, ERASER
    }

    enum class Colors {
        RED, ORANGE, YELLOW, GREEN, BLUE, NAVY, PURPLE, GRAY, BLACK, WHITE
    }

    var currentTool : Tools = Tools.PEN

    private val defSize = 3
    private val eraserSize = 30

    //private val canvas : Canvas = Canvas()
    private val drawCommandList = ArrayList<Pen>()
    private val paint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private lateinit var pen : Pen
    private var drownImage : Bitmap? = null
    private var currentColor : Int = Color.BLACK
    private var currentSize : Int = defSize

    private var drawCommandListForRedo = ArrayList<Pen>()

    // 현재 그린 그림을 bitmap으로 반환
    fun currentCanvas(): Bitmap {
        val bitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        this.draw(canvas)
        return bitmap
    }

    // Tool 타입을 변경
    fun changeTool(tools: Tools) {
        when (tools) {
            Tools.PEN -> {
                currentTool = Tools.PEN
                currentSize = defSize
            }
            Tools.ERASER -> {
                currentTool = Tools.ERASER
                currentColor = Color.WHITE
                currentSize = eraserSize
            }
        }
    }

    // Color를 변경
    fun setColor(color : Colors) {
        when (color) {
            Colors.RED -> {
                currentColor = ContextCompat.getColor(context, R.color.red)
            }
            Colors.ORANGE -> {
                currentColor = ContextCompat.getColor(context, R.color.orange)
            }
            Colors.YELLOW -> {
                currentColor = ContextCompat.getColor(context, R.color.yellow)
            }
            Colors.GREEN -> {
                currentColor = ContextCompat.getColor(context, R.color.green)
            }
            Colors.BLUE -> {
                currentColor = ContextCompat.getColor(context, R.color.blue)
            }
            Colors.NAVY -> {
                currentColor = ContextCompat.getColor(context, R.color.navy)
            }
            Colors.PURPLE -> {
                currentColor = ContextCompat.getColor(context, R.color.purple)
            }
            Colors.GRAY -> {
                currentColor = ContextCompat.getColor(context, R.color.gray)
            }
            Colors.BLACK -> {
                currentColor = ContextCompat.getColor(context, R.color.black)
            }
            Colors.WHITE -> {
                currentColor = ContextCompat.getColor(context, R.color.white)
            }
        }
    }

    fun getColor() : Colors {
        when(currentColor) {
            ContextCompat.getColor(context, R.color.red) -> {
                return Colors.RED
            }
            ContextCompat.getColor(context, R.color.orange) -> {
                return Colors.ORANGE
            }
            ContextCompat.getColor(context, R.color.yellow) -> {
                return Colors.YELLOW
            }
            ContextCompat.getColor(context, R.color.green) -> {
                return Colors.GREEN
            }
            ContextCompat.getColor(context, R.color.blue) -> {
                return Colors.BLUE
            }
            ContextCompat.getColor(context, R.color.navy) -> {
                return Colors.NAVY
            }
            ContextCompat.getColor(context, R.color.purple) -> {
                return Colors.PURPLE
            }
            ContextCompat.getColor(context, R.color.gray) -> {
                return Colors.GRAY
            }
            ContextCompat.getColor(context, R.color.black) -> {
                return Colors.BLACK
            }
            ContextCompat.getColor(context, R.color.white) -> {
                return Colors.WHITE
            }
        }
        return Colors.BLACK
    }

    // Size를 변경
    fun setSize(size : Int) {
        currentSize = size
    }

    fun getSize() : Int {
        return currentSize
    }

    // 현재 그림을 undo
    fun undo() {
        //Log.d(TAG,"undo() size : " + drawCommandList.size)
        if(drawCommandList.size <= 0)
            return

        for(i in drawCommandList.size - 1 downTo 0) {
            val pen : Pen = drawCommandList[i]
                drawCommandList.removeAt(i)

            if(!pen.move())
                break
        }
        invalidate()
    }

    // undo 했던 그림을 redo
    fun redo() {
        var isFirstFalse = true
        Log.d(TAG,"redo() size : " + drawCommandList.size)
        for(i in drawCommandList.size until drawCommandListForRedo.size) {
            Log.d(TAG, "redo() index : $i")
            if(drawCommandListForRedo[i].move()) {
                drawCommandList.add(drawCommandListForRedo[i])
            }
            else {
                if(isFirstFalse) {
                    drawCommandList.add(drawCommandListForRedo[i])
                    isFirstFalse = false;
                }
                else {
                    break
                }
            }
        }
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.d(TAG,"onDraw()")
        for(i in 0 until drawCommandList.size) {
            val pen : Pen = drawCommandList[i]
            paint.style = Paint.Style.STROKE
            paint.color = pen.color
            paint.strokeWidth = pen.size.toFloat()
            canvas?.drawPath(pen.path, paint)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d(TAG,"onTouchEvent : " + event!!.action)
        val x = event.x
        val y = event.y

        val action : Int = event.action
        when(action) {
            MotionEvent.ACTION_DOWN -> {
                pen = Pen(currentColor, currentSize)
                drawCommandList.add(pen)
                pen.path.moveTo(x,y)
            }
            MotionEvent.ACTION_MOVE -> {
                pen.path.lineTo(x,y)
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