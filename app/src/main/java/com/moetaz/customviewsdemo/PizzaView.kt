package com.moetaz.customviewsdemo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class PizzaView : View {

    lateinit var paint: Paint
    var numWedges = 5

    constructor(context: Context) : super(context) {
        init(context , null)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        var strokeWidth = 4
        var color = Color.YELLOW
        attrs?.let {
            val array = context.obtainStyledAttributes(it , R.styleable.PizzaView)
            strokeWidth = array.getDimensionPixelSize(R.styleable.PizzaView_stroke_width , strokeWidth)
            color = array.getColor(R.styleable.PizzaView_color , color)
            numWedges = array.getInt(R.styleable.PizzaView_num_wedges , numWedges)
        }

        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = strokeWidth.toFloat()
        paint.color = color
    }

    constructor(context: Context, attrs: AttributeSet?, devStylArr : Int) : super(context, attrs , devStylArr) {
        init(context , attrs)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context , attrs)
    }

    override fun onDraw(canvas: Canvas?) {
        val widht = width - paddingLeft - paddingRight
        val hight = height - paddingTop - paddingBottom
        val cx = widht / 2 + paddingLeft
        val cy = hight /2 + paddingTop
        val diamter = Math.min(widht , hight) - paint.strokeWidth
        val radius = diamter / 2

        canvas?.let {
            it.drawCircle(cx.toFloat() , cy.toFloat() , radius.toFloat() , paint)
            drawPizzaCuts(canvas ,cx.toFloat() , cy.toFloat() , radius)
        }

    }

    private fun drawPizzaCuts(canvas: Canvas , cx :Float , cy : Float , radius  : Float){

        val degrees = 360f / 5
        canvas.save()
        for (i in 0 until numWedges){
            canvas.rotate(degrees , cx ,cy)
            canvas.drawLine(cx , cy , cx , cy - radius , paint)
        }

        canvas.restore()

    }
}