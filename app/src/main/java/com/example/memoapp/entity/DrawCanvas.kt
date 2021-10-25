package com.example.memoapp.entity

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
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

    private val penSize = 3
    private val eraserSize = 30

    private var drawCommandList = ArrayList<Pen>()
    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var drownImage: Bitmap? = null
    private var currentColor: Int = Color.BLACK
    private var size: Int = penSize

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
                //this.color = Color.BLACK
                size = penSize
            }
            Tools.ERASER -> {
                currentTool = Tools.ERASER
                currentColor = Color.WHITE
                size = eraserSize
            }
        }
    }

    // Color를 변경
    fun changeColor(color : Colors) {
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

    // Size를 변경
    fun changeSize(size : Int) {
        this.size = size
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
        canvas?.drawColor(Color.WHITE)

        // 그려진 이미지가 없으면 0,0 부터 그림
        if (drownImage != null) {
            canvas?.drawBitmap(drownImage!!, 0.0f, 0.0f, null)
        }

        for (i in 0 until drawCommandList.size) {
            val pen : Pen = drawCommandList.get(i)
            paint.color = pen.color
            paint.strokeWidth = pen.size.toFloat()

            if(pen.move()) {
                val prevPen : Pen = drawCommandList.get(i-1)
                canvas?.drawLine(prevPen.x, prevPen.y, pen.x, pen.y, paint)
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d(TAG,"onTouchEvent : " + event!!.action)
        val action : Int = event!!.action
        var state : Pen.State = Pen.State.MOVE
        if(action == MotionEvent.ACTION_DOWN)
            state = Pen.State.START
        else if(action == MotionEvent.ACTION_UP)
            state = Pen.State.END

        if(state == Pen.State.START || state == Pen.State.MOVE) {
            drawCommandList.add(Pen(event.x, event.y, state, currentColor, size))
            invalidate()
        }
        else if(state == Pen.State.END) {
            drawCommandListForRedo.clear()
            drawCommandListForRedo.addAll(drawCommandList)
        }
        return true
    }
}