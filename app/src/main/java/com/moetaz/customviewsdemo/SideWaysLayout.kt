package com.moetaz.customviewsdemo

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.widget.LinearLayout

class SideWaysLayout : LinearLayout {

    constructor(context: Context) : super(context) {

    }

    constructor(context: Context , attributeSet: AttributeSet?): super(context , attributeSet){

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        setMeasuredDimension(measuredHeight , measuredWidth)
    }

    override fun dispatchDraw(canvas: Canvas?) {
        canvas?.let {
            it.save()
            it.translate( 0f , height.toFloat())
            it.rotate(-90f)
        }
        super.dispatchDraw(canvas)


        canvas?.let {
            it.restore()
        }
    }
}