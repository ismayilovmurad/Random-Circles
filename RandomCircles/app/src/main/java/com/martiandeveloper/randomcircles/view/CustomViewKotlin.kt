package com.martiandeveloper.randomcircles.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.martiandeveloper.randomcircles.hasDrawMTBNClicked
import com.martiandeveloper.randomcircles.model.Shape
import com.martiandeveloper.randomcircles.utils.Constants
import java.util.*



class CustomViewKotlin(context: Context?, attrs: AttributeSet?) :
    View(context, attrs) {
    val RADIUS = Constants.RADIUS
    private var canvas: Canvas? = null
    var historyList: List<Shape> = ArrayList()
    var colorList: MutableList<Int> = ArrayList()

    // defines paint and canvas
    private var drawPaint: Paint? = null

    // Setup paint with color and stroke styles
    private fun setupPaint() {
        drawPaint = Paint()
        //drawPaint.setColor(colorList.get(new Random().nextInt(10)));
        drawPaint!!.isAntiAlias = true
        drawPaint!!.strokeWidth = 5f
        drawPaint!!.style = Paint.Style.FILL_AND_STROKE
        drawPaint!!.strokeJoin = Paint.Join.ROUND
        drawPaint!!.strokeCap = Paint.Cap.ROUND
    }

    override fun onDraw(canvas: Canvas) {
        if(hasDrawMTBNClicked){
            super.onDraw(canvas)
            this.canvas = canvas
            Log.d("TAG", "  onDraw called")
            for (shape in historyList) {
                if (shape.isVisible) {
                    when (shape.type) {
                        Shape.Type.CIRCLE -> {
                            drawPaint!!.color = colorList[Random().nextInt(10)]
                            canvas.drawCircle(
                                shape.getxCordinate().toFloat(),
                                shape.getyCordinate().toFloat(),
                                RADIUS.toFloat(),
                                drawPaint!!
                            )
                        }
                        Shape.Type.SQUARE -> drawRectangle(shape.getxCordinate(), shape.getyCordinate())
                        Shape.Type.TRIANGLE -> drawTriangle(
                            shape.getxCordinate(), shape.getyCordinate(),
                            (2 * RADIUS)
                        )
                    }
                }
            }
        }

    }

    private var longClickActive = false
    var initialTouchX = 0f
    var initialTouchY = 0f
    private var startClickTime: Long = 0

    var squareSideHalf = 1 / Math.sqrt(2.0)

    //Consider pivot x,y as centroid.
    fun drawRectangle(x: Int, y: Int) {
        drawPaint!!.color = Color.RED
        val rectangle = Rect(
            (x - squareSideHalf * RADIUS).toInt(),
            (y - squareSideHalf * RADIUS).toInt(),
            (x + squareSideHalf * RADIUS).toInt(),
            (y + squareSideHalf * RADIUS).toInt()
        )
        canvas!!.drawRect(rectangle, drawPaint!!)
    }

    /*
    select three vertices of triangle. Draw 3 lines between them to form a traingle
     */
    fun drawTriangle(x: Int, y: Int, width: Int) {
        drawPaint!!.color = Color.GREEN
        val halfWidth = width / 2
        val path = Path()
        path.moveTo(x.toFloat(), (y - halfWidth).toFloat()) // Top
        path.lineTo((x - halfWidth).toFloat(), (y + halfWidth).toFloat()) // Bottom left
        path.lineTo((x + halfWidth).toFloat(), (y + halfWidth).toFloat()) // Bottom right
        path.lineTo(x.toFloat(), (y - halfWidth).toFloat()) // Back to Top
        path.close()
        canvas!!.drawPath(path, drawPaint!!)
    }

    init {
        isFocusable = true
        isFocusableInTouchMode = true
        Log.d("TAG", "  constructor called")
        colorList.add(Color.BLUE)
        colorList.add(Color.BLACK)
        colorList.add(Color.CYAN)
        colorList.add(Color.DKGRAY)
        colorList.add(Color.GRAY)
        colorList.add(Color.GREEN)
        colorList.add(Color.LTGRAY)
        colorList.add(Color.MAGENTA)
        colorList.add(Color.RED)
        colorList.add(Color.YELLOW)
        setupPaint()
    }
}
